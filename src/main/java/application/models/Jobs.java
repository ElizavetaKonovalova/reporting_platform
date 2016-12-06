package application.models;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by ekonovalova on 12/5/2016.
 */

@Entity
@Table(indexes = {@Index(name = "jobIndex", unique = true, columnList = "jobcode")})
public class Jobs {

    @Id
    @Column(name ="id", columnDefinition = "serial", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long ID;
    private Long CLIENTID;

    @Column(unique = true, nullable = false)
    private String JOBCODE;
    @Column(nullable = false)
    private String JOBNAME;
    private Date CENSUSSTART;
    private Date CENSUSEND;
    private Date DELIVERYDATE;
    private Date PRESENTATIONDATE;
    private Boolean STATUS;
    private Integer SAMPLESIZE;
    private Integer SURVEYSUBTYPEID;
    private String DELIVERYTYPE;
    private Integer LOGGEDIN;
    private Short RESPONSERATE;

    public Jobs(){}

    /*Getters*/
    public Integer getSAMPLESIZE() { return this.SAMPLESIZE; }
    public Date getCENSUSSTART() { return this.CENSUSSTART; }
    public Date getCENSUSEND() { return this.CENSUSEND; }
    public Date getDELIVERYDATE() { return this.DELIVERYDATE; }
    public Date getPRESENTATIONDATE() { return this.PRESENTATIONDATE; }
    public Boolean getSTATUS() { return this.STATUS; }
    public String getJOBNAME() { return this.JOBNAME; }
    public Integer getLOGGEDIN() { return this.LOGGEDIN; }
    public String getJOBCODE() { return this.JOBCODE; }
    public String getDELIVERYTYPE() { return this.DELIVERYTYPE; }
    public Long getCLIENTID() { return this.CLIENTID; }
    public Short getRESPONSERATE() { return this.RESPONSERATE; }
    public Long getID() { return this.ID; }
    public Integer getSURVEYSUBTYPEID() { return this.SURVEYSUBTYPEID; }

    /*Setters*/
    public void setSAMPLESIZE(Integer samplesize) { this.SAMPLESIZE = samplesize; }
    public void setCENSUSSTART(Date censusstart) { this.CENSUSSTART = censusstart; }
    public void setCENSUSEND(Date censusend) { this.CENSUSEND = censusend; }
    public void setDELIVERYDATE(Date deliverydate) { this.DELIVERYDATE = deliverydate; }
    public void setPRESENTATIONDATE(Date presentationdate) { this.PRESENTATIONDATE = presentationdate; }
    public void setSTATUS(Boolean status) { this.STATUS = status; }
    public void setJOBNAME(String surveyname) { this.JOBNAME = surveyname; }
    public void setLOGGEDIN(Integer loggedin) { this.LOGGEDIN = loggedin; }
    public void setJOBCODE(String jobcode) { this.JOBCODE = jobcode; }
    public void setDELIVERYTYPE(String deliverytype) { this.DELIVERYTYPE = deliverytype; }
    public void setCLIENTID( Long client_name ) { this.CLIENTID = client_name; }
    public void setRESPONSERATE( Short responserate ) { this.RESPONSERATE = responserate; }
    public void setID(Long id) { this.ID = id; }
    public void setSURVEYSUBTYPEID(Integer id) { this.SURVEYSUBTYPEID = id; }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
