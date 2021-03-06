package enterprise.ProjetMediatheque.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Gilles
 */
@Entity
@Table (name="ADDRESSES")
@NamedQuery(name = "Adresse.get", query = "SELECT a "
        + "FROM Adresse a "
        + "WHERE a.rue = :rue "
        + "AND a.ville = :ville "
        + "HAVING COUNT(a) > 0")
public class Adresse implements Serializable {
    @Id
    @GeneratedValue
    @Column (name="ID") // innutile car : nom de variable = nom de colomne
    private Long id;
    
    @Column(name="STREET")
    private String rue;
    
    @ManyToOne(cascade= CascadeType.PERSIST)
    @JoinColumns ({
        @JoinColumn (name="CITY_NAME", referencedColumnName="CITY_NAME"),
        @JoinColumn (name="POSTAL_CODE", referencedColumnName="POSTAL_CODE")
    })
    private Ville ville;
    
    // <editor-fold defaultstate="collapsed" desc="Constructeurs">
    /**
     * Constructeur vide de la classe Adresse.
     */
    public Adresse(){}

    /**
     * Constructeur de la classe Adresse.
     * @param rue La rue associee a l'adresse.
     * @param ville La ville associee a l'adresse.
     * @param codePostale Le code postal associe a cette Adresse.
     */
    public Adresse(String rue, Ville ville) {
        this.rue = rue;
        this.ville = ville;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Accesseurs">
    // TODO : Ecrire un descriptif pour la JavaDoc
    /**
     * 
     * @return L'id de l'objet Adresse.
     */
    public Long getId() {
        return id;
    }
    
    /**
     * 
     * @return La rue associee a l'Adresse.
     */
    public String getRue(){
        return rue;
    }
    
    /**
     * 
     * @return La ville associee a l'Adresse.
     */
    public Ville getVille(){
        return ville;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Modificateurs">
    /**
     * 
     * @param id Le nouvel id d'Adresse.
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * 
     * @param rue La nouvelle rue associee a Adresse.
     */
    public void setRue(String rue){
        this.rue = rue;
    }
    
    /**
     * 
     * @param ville La nouvelle ville associee a Adresse.
     */
    public void setVille(Ville ville){
        this.ville = ville;
    }
    // </editor-fold>
}
