package enterprise.ProjetMediatheque.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author sbai
 */

@Entity
@Table(name = "TYPES")
public class Type implements Serializable {

    @Id
    @Column(name = "NAME")
    @Enumerated(EnumType.STRING)
    private TypeName nom;

    @Column(name = "MAX_BORROWING_DURATION")
    private String dureeEmpruntMax;
  
    @Column(name = "MAX_BORROWING_NUMBER")
    private int nbEmpruntMax;
    
    /**
     * Creates a new instance of Type
     */
    public Type() {
    }

    public Type(String nom, String dureeEmpruntMax, int nbEmpruntMax) {
        this.nom= TypeName.valueOf(nom);
        this.dureeEmpruntMax= dureeEmpruntMax;
        this.nbEmpruntMax=nbEmpruntMax;
    }

    public String getNom() {
        return this.nom.toString();
    }

    public String getDureeEmpruntMax() {
        return this.dureeEmpruntMax;
    }

    public int getNbEmpruntMax(){
        return this.nbEmpruntMax;
    }
    
}
