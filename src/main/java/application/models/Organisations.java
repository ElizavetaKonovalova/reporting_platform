package application.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clients", indexes = {@Index(name = "clientIndex", columnList = "org_name", unique = true)})
public class Organisations {

    @Id
    @Column(columnDefinition = "SERIAL", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CLIENT_ID;
    @Column(nullable = false)
    private String CLIENT_NAME;
    @Column(nullable = false)
    private String ORG_NAME;
    @OneToMany(mappedBy = "CLIENT_ID", targetEntity = Jobs.class)
    private List<Jobs> JOBS;

    public Organisations() {}

    /*Getters*/
    public String getORG_NAME() { return this.ORG_NAME; }
    public String getCLIENT_NAME() { return this.CLIENT_NAME; }
    public Long getCLIENT_ID() { return this.CLIENT_ID; }

    /*Setters*/
    public void setORG_NAME(String orgname ) { this.ORG_NAME = orgname; }
    public void setCLIENT_NAME(String clientname ) { this.CLIENT_NAME = clientname; }
    public void setCLIENT_ID(Long id ) { this.CLIENT_ID = id; }
}
