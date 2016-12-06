package application.models;

/**
 * Created by ekonovalova on 12/5/2016.
 */
import javax.persistence.*;

@Entity
@Table(indexes = {@Index(name = "organisationIndex", columnList = "orgname", unique = true)})
public class Organisations {

    @Id
    @Column(name ="id", columnDefinition = "serial", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long ID;
    @Column(unique = true, nullable = false)
    private String ORGNAME;
    private String LOCATION;

    public Organisations() {}

    /*Getters*/
    public String getORGNAME() { return this.ORGNAME; }
    public String getLOCATION() { return this.LOCATION; }

    /*Setters*/
    public void setORGNAME( String org_name ) { this.ORGNAME = org_name; }
    public void setLOCATION( String location ) { this.LOCATION = location; }

}
