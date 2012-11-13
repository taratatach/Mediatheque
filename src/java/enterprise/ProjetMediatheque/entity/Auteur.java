package enterprise.ProjetMediatheque.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author sbai
 */
@Entity
@Table(name = "AUTHOR")
public class Auteur implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "AUTHOR_ID")
    private Long id;

    @Column(name = "LASTNAME")
    private String nom;
  
    @Column(name = "FIRSTNAME")
    private String prenom;
    
   @ManyToMany(cascade=CascadeType.ALL)
   @JoinTable(name = "WORK_AUTHOR",
        joinColumns = {
            @JoinColumn(name="AUTHOR_ID") 
        },
        inverseJoinColumns = {
            @JoinColumn(name="WORK_ID")
        }
   )
   private List<Ouvrage> ouvrages;
   
    /**
     * Creates a new instance of Ouvrage
     */
    public Auteur() {
    }

    public Auteur(String nom, String prenom) {
        this.nom = nom;
        this.prenom=prenom;
       
    }

    public Long getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }
    
    public List<Ouvrage> getOuvrages() {
        return ouvrages;
   }
}
