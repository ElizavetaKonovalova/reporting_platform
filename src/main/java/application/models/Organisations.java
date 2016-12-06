package application.models;

/**
 * Created by ekonovalova on 12/5/2016.
 */
import javax.persistence.*;

@Entity
public class Organisations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    private String ORGNAME;
    private String LOCATION;

    public Organisations() {}

    public Organisations( String org_name, String location) {
        this.ORGNAME = org_name;
        this.LOCATION = location;
    }

    /*Getters*/
    public String getORGNAME() { return this.ORGNAME; }
    public String getLOCATION() { return this.LOCATION; }

    /*Setters*/
    public void setORGNAME( String org_name ) { this.ORGNAME = org_name; }
    public void setLOCATION( String location ) { this.LOCATION = location; }

}
