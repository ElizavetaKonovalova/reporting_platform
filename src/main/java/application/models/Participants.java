package application.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Participants")
public class Participants {

    @Id
    @Column(name ="participant_id", columnDefinition = "SERIAL", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PARTICIPANT_ID;
    @Column(length = 100)
    @Size(max = 100)
    private String PARTICIPANT_NAME;
    @Column(length = 100)
    @Size(max = 100)
    private String PARTICIPANT_EMAIL;
    @Column(length = 40)
    @Size(max = 40)
    private String PASSWORD;
    private char STATUS;
    private Date DATE_MODIFIED;
    @ManyToOne @JoinColumn(name="job_id", insertable = false, updatable = false)
    private Jobs JOB;
    @ManyToMany(mappedBy = "PARTICIPANT", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<JobStructuralMaps> WU_CODE;
    @OneToMany(mappedBy = "PARTICIPANT_ID", targetEntity = TextData.class)
    private List<TextData> TEXT_RESPONSES;
    @OneToMany(mappedBy = "PARTICIPANT_ID", targetEntity = NumberData.class)
    private List<NumberData> NUMBER_RESPONSES;

    /* Simple getters */
    public Long getPARTICIPANT_ID() { return this.PARTICIPANT_ID; }
    public Jobs getJOB() { return this.JOB; }
    public List<TextData> getTEXT_RESPONSES() { return this.TEXT_RESPONSES; }
    public List<NumberData> getNUMBER_RESPONSES() { return this.NUMBER_RESPONSES; }
    public String getPARTICIPANT_NAME() { return this.PARTICIPANT_NAME; }
    public String getPARTICIPANT_EMAIL() { return this.PARTICIPANT_EMAIL; }
    public String getPASSWORD() { return this.PASSWORD; }
    public char getSTATUS() { return this.STATUS; }
    public Date getDATE_MODIFIED() { return this.DATE_MODIFIED; }

    /* Simple setters */
    public void setPARTICIPANT_ID( Long participant_id ) { this.PARTICIPANT_ID = participant_id; }
    public void setJOB( Jobs job ) { this.JOB = job; }
    public void setTEXT_RESPONSES( TextData text_responses ) { this.TEXT_RESPONSES.add(text_responses);}
    public void setNUMBER_RESPONSES( NumberData number_responses ) { this.NUMBER_RESPONSES.add(number_responses);}
    public void setPARTICIPANT_NAME( String participant_name ) { this.PARTICIPANT_NAME = participant_name;}
    public void setPARTICIPANT_EMAIL( String participant_email ) { this.PARTICIPANT_EMAIL = participant_email;}
    public void setPASSWORD( String password ) { this.PASSWORD = password;}
    public void setSTATUS( char status ) { this.STATUS = status;}
    public void setDATE_MODIFIED( Date date_modified ) { this.DATE_MODIFIED = date_modified;}
}
