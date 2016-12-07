package application.models;

/**
 * Created by ekonovalova on 12/5/2016.
 */
import javax.persistence.*;

@Entity
@Table(indexes = {@Index(name = "organisationIndex", columnList = "orgname", unique = true)})
public class Organisations {

    @Id
    @Column(name ="clientid", columnDefinition = "serial", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long CLIENTID;
    @Column(unique = true, nullable = false)
    private String CLIENTNAME;
    private String ORGNAME;

    public Organisations() {}

    /*Getters*/
    public String getORGNAME() { return this.ORGNAME; }
    public String getCLIENTNAME() { return this.CLIENTNAME; }
    public Long getCLIENTID() { return this.CLIENTID; }

    /*Setters*/
    public void setORGNAME( String orgname ) { this.ORGNAME = orgname; }
    public void setCLIENTNAME( String clientname ) { this.CLIENTNAME = clientname; }
    public void setCLIENTID( Long id ) { this.CLIENTID = id; }
}
