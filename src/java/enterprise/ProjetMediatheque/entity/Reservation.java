/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.ProjetMediatheque.entity;

import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Persistence;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;

/**
 *
 * @author guyader
 */
@Entity
@SequenceGenerator(
        name = "BOOKING_SEQUENCE",
        sequenceName = "BOOKING_SEQUENCE",
        initialValue = 0,
        allocationSize = 1)
@NamedQueries({
    // consulter les items de cet ouvrage et leur statut -> on compte le nombre de réservés et empruntés en java
    @NamedQuery(name = "Reservation.countAvailable", query = "SELECT count(i) "
        + "FROM Item i JOIN i.ouvrage o "
        + "WHERE o = :work "
        + "AND i.statut = enterprise.ProjetMediatheque.entity.Statut.DISPONIBLE"),
    @NamedQuery(name = "Reservation.countReservations", query = "SELECT count(r) "
        + "FROM Reservation r JOIN r.ouvrage o "
        + "WHERE o = :work "
        + "AND r.dateDExpiration IS NOT NULL "
        + "AND r.dateDExpiration < :date")
})
@Table(name="BOOKINGS")
public class Reservation {
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=SEQUENCE, generator="BOOKING_SEQUENCE")
    private Long id;
    
    @Column(name = "BOOKING_DATE")
    @Temporal(DATE)
    private Date dateDeReservation;
    
    @Column(name = "EXPIRY_DATE")
    @Temporal(DATE)
    private Date dateDExpiration; // renommee depuis DateFinDeValidite
    
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Adherent adherent;
    
    @ManyToOne
    @JoinColumn(name = "WORK_ID")
    private Ouvrage ouvrage;
    
    public Reservation() {}
    
    public Reservation(
            Adherent _adherent,
            Ouvrage _ouvrage) {
        this.adherent = _adherent;
        this.ouvrage = _ouvrage;
        this.dateDeReservation = new Date();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("libraryPU");
        EntityManager em = emf.createEntityManager();
        
        TypedQuery<Long> countAvailable = em.createNamedQuery("Reservation.countAvailable", Long.class);
        countAvailable.setParameter("work", this.ouvrage);
        Long available = countAvailable.getSingleResult();
        
        TypedQuery<Long> countReservations = em.createNamedQuery("Reservation.countReservations", Long.class);
        countReservations.setParameter("work", this.ouvrage);
        countReservations.setParameter("date", this.dateDeReservation);
        Long reserved = countReservations.getSingleResult();
        
        if (available > reserved) { // verifier s'il y a encore des items de l'ouvrage disponibles
            // Si oui alors on a jusqu'au soir
            this.dateDExpiration = this.dateDeReservation;
        } else {
            // sinon on changera la valeur dès qu'un item de cet ouvrage rentre
            this.dateDExpiration = null;
        }
    }
}
