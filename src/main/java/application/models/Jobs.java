package application.models;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by ekonovalova on 12/5/2016.
 */

@Entity
public class Jobs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    private Long CLIENTID;
    private String JOBCODE;
    private String JOBNAME;
    private Date CENSUSSTART;
    private Date CENSUSEND;
    private Date DELIVERYDATE;
    private Date PRESENTATIONDATE;
    private Boolean STATUS;
    private Short SAMPLESIZE;
    private Integer SURVEYSUBTYPEID;
    private String DELIVERYTYPE;
    private Integer LOGGEDIN;
    private Short RESPONSERATE;

    public Jobs(){}

    public Jobs(Long clientid, String jobcode, String jobname) {
        this.JOBCODE = jobcode;
        this.CLIENTID = clientid;
        this.JOBNAME = jobname;
    }

    /*Getters*/
    public Short getSAMPLESIZE() { return this.SAMPLESIZE; }
    public Date getCENSUSSTART() { return this.CENSUSSTART; }
    public Date getCENSUSEND() { return this.CENSUSEND; }
    public Date getDELIVERYDATE() { return this.DELIVERYDATE; }
    public Date getPRESENTATIONDATE() { return this.PRESENTATIONDATE; }
    public Boolean getSTATUS() { return this.STATUS; }
    public String getSURVEYNAME() { return this.JOBNAME; }
    public Integer getSURVEYTYPE() { return this.LOGGEDIN; }
    public String getJOBCODE() { return this.JOBCODE; }
    public String getDELIVERYTYPE() { return this.DELIVERYTYPE; }
    public Long getCLIENTID() { return this.CLIENTID; }
    public Short getLOCATION() { return this.RESPONSERATE; }
    public Long getID() { return this.ID; }

    /*Setters*/
    public void setSAMPLESIZE(Short samplesize) { this.SAMPLESIZE = samplesize; }
    public void setCENSUSSTART(Date censusstart) { this.CENSUSSTART = censusstart; }
    public void setCENSUSEND(Date censusend) { this.CENSUSEND = censusend; }
    public void setDELIVERYDATE(Date deliverydate) { this.DELIVERYDATE = deliverydate; }
    public void setPRESENTATIONDATE(Date presentationdate) { this.PRESENTATIONDATE = presentationdate; }
    public void setSTATUS(Boolean status) { this.STATUS = status; }
    public void setSURVEYNAME(String surveyname) { this.JOBNAME = surveyname; }
    public void setSURVEYTYPE(Integer loggedin) { this.LOGGEDIN = loggedin; }
    public void setJOBCODE(String jobcode) { this.JOBCODE = jobcode; }
    public void setDELIVERYTYPE(String deliverytype) { this.DELIVERYTYPE = deliverytype; }
    public void setCLIENTNAME( Long client_name ) { this.CLIENTID = client_name; }
    public void setLOCATION( Short responserate ) { this.RESPONSERATE = responserate; }
    public void setID(Long id) { this.ID = id; }
}
