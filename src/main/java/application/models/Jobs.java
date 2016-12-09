package application.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by ekonovalova on 12/5/2016.
 */

@Entity
@Table(name = "jobs", indexes = {@Index(name = "jobsIndex", unique = true, columnList = "job_code")})
public class Jobs {

    @Id
    @Column(name ="job_id", columnDefinition = "SERIAL", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long JOB_ID;
    @ManyToOne @JoinColumn(name="client_id")
    private Organisations CLIENT_ID;
    @Column(unique = true, nullable = false)
    private String JOB_CODE;
    @Column(nullable = false)
    private String JOB_NAME;
    private Date CENSUS_START;
    private Date CENSUS_END;
    private Date DELIVERY_DATE;
    private Date PRESENTATION_DATE;
    private Boolean STATUS;
    private Integer SAMPLE_SIZE;
    private Integer SURVEY_SUBTYPEID;
    private String DELIVERY_TYPE;
    private Integer LOGGED_IN;
    private Short RESPONSE_RATE;
    @OneToMany(mappedBy = "JOB", targetEntity = Participants.class)
    private List<Participants> PARTICIPANTS;

    public Jobs(){}

    /*Getters*/
    public Integer getSAMPLE_SIZE() { return this.SAMPLE_SIZE; }
    public Date getCENSUS_START() { return this.CENSUS_START; }
    public Date getCENSUS_END() { return this.CENSUS_END; }
    public Date getDELIVERY_DATE() { return this.DELIVERY_DATE; }
    public Date getPRESENTATION_DATE() { return this.PRESENTATION_DATE; }
    public Boolean getSTATUS() { return this.STATUS; }
    public String getJOB_NAME() { return this.JOB_NAME; }
    public Integer getLOGGED_IN() { return this.LOGGED_IN; }
    public String getJOB_CODE() { return this.JOB_CODE; }
    public String getDELIVERY_TYPE() { return this.DELIVERY_TYPE; }
    public Organisations getCLIENT_ID() { return this.CLIENT_ID; }
    public Short getRESPONSE_RATE() { return this.RESPONSE_RATE; }
    public Long getJOB_ID() { return this.JOB_ID; }
    public Integer getSURVEY_SUBTYPEID() { return this.SURVEY_SUBTYPEID; }
    public List<Participants> getPARTICIPANTS() { return this.PARTICIPANTS; }

    /*Setters*/
    public void setSAMPLE_SIZE(Integer samplesize) { this.SAMPLE_SIZE = samplesize; }
    public void setCENSUS_START(Date censusstart) { this.CENSUS_START = censusstart; }
    public void setCENSUS_END(Date censusend) { this.CENSUS_END = censusend; }
    public void setDELIVERY_DATE(Date deliverydate) { this.DELIVERY_DATE = deliverydate; }
    public void setPRESENTATION_DATE(Date presentationdate) { this.PRESENTATION_DATE = presentationdate; }
    public void setSTATUS(Boolean status) { this.STATUS = status; }
    public void setJOB_NAME(String surveyname) { this.JOB_NAME = surveyname; }
    public void setLOGGED_IN(Integer loggedin) { this.LOGGED_IN = loggedin; }
    public void setJOB_CODE(String jobcode) { this.JOB_CODE = jobcode; }
    public void setDELIVERY_TYPE(String deliverytype) { this.DELIVERY_TYPE = deliverytype; }
    public void setCLIENT_ID(Organisations client_id) { this.CLIENT_ID = client_id; }
    public void setRESPONSE_RATE(Short responserate ) { this.RESPONSE_RATE = responserate; }
    public void setJOB_ID(Long id) { this.JOB_ID = id; }
    public void setSURVEY_SUBTYPEID(Integer id) { this.SURVEY_SUBTYPEID = id; }
}
