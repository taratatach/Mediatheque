/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.ProjetMediatheque.servlet;

import enterprise.ProjetMediatheque.entity.Auteur;
import java.io.IOException;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 *
 * @author guyader
 */
@WebServlet(name = "CreateAuthorServlet", urlPatterns = {"/CreateAuthor"})
public class CreateAuthorServlet extends HttpServlet {

    @PersistenceUnit
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
    
    private String req_prenom = "";
    private String req_nom = "";
    
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        assert emf != null;  //Make sure injection went through correctly.
        EntityManager em = null;
        try {

            //Get the data from user's form
            req_nom = (String) request.getParameter("nom");
            req_prenom = (String) request.getParameter("prenom");
            
            if (req_prenom.isEmpty()) {
                returnMessageError(request, response, "Veuillez entrer un pr&eacutenom !");
            } else if (req_nom.isEmpty()) {
                returnMessageError(request, response, "Veuillez entrer un nom !");
            }
            
            Auteur auteur = new Auteur(req_nom, req_prenom);

            //begin a transaction
            utx.begin();
            //create an em. 
            //Since the em is created inside a transaction, it is associsated with 
            //the transaction
            em = emf.createEntityManager();
            //persist the person entity
            em.persist(auteur);
            //commit transaction which will trigger the em to 
            //commit newly created entity into database
            utx.commit();

            //Forward to ListPerson servlet to list persons along with the newly
            //created person above
            response.sendRedirect("ListAuthors");
        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            //close the em to release any resources held up by the persistebce provider
            if (em != null) {
                em.close();
            }
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    
    /**
     * Retourne une message d'erreur a la page createAuthor.jsp
     * @param request
     * @param response
     * @param msg
     */
    private void returnMessageError(HttpServletRequest request, HttpServletResponse response, String msg) throws ServletException, IOException {
        request.setAttribute("alert",           "<span class=\"alert\">" + msg + "</span>");
        request.setAttribute("prenom",          req_prenom);
        request.setAttribute("nom",             req_nom);
        request.getRequestDispatcher("createAuthor.jsp").forward(request, response);
    }
}
