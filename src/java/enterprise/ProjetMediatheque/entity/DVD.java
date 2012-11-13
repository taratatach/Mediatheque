package enterprise.ProjetMediatheque.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 *
 * @author sbai
 */
@Entity
@DiscriminatorValue("DVD")
@Table(name = "DVD")
public class DVD extends Ouvrage{

    @Column(name = "LANGUAGE")
    private String langue;
  
    @Column(name = "FORMAT")
    private String format;
  
    
    /**
     * Creates a new instance of DVD
     */
    public DVD() {
    }

    public DVD(String langue, String format) {
        this.langue = langue;
        this.format=format;
               
    }

    public String getLangue() {
        return this.langue;
    }
      
    public String getFormat(){
        return this.format;
    }
}


