package application.models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ekonovalova on 08-Dec-16.
 */

@Entity
@Table(name = "Participants")
public class Participants {

    @Id
    @Column(name ="participant_id", columnDefinition = "SERIAL", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PARTICIPANT_ID;
    @ManyToOne @JoinColumn(name="job_id", insertable = false, updatable = false)
    private Jobs JOB;
    @ManyToMany(mappedBy = "PARTICIPANT", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<WorkUnits> WU_CODE;
    @OneToMany(mappedBy = "PARTICIPANT_ID", targetEntity = TextData.class)
    private List<TextData> TEXT_RESPONSES;
    @OneToMany(mappedBy = "PARTICIPANT_ID", targetEntity = NumberData.class)
    private List<NumberData> NUMBER_RESPONSES;

    /* Simple getters */
    public Long getPARTICIPANT_ID() { return this.PARTICIPANT_ID; }
    public Jobs getJOB() { return this.JOB; }
    public List<TextData> getTEXT_RESPONSES() { return this.TEXT_RESPONSES; }
    public List<NumberData> getNUMBER_RESPONSES() { return this.NUMBER_RESPONSES; }

    /* Simple setters */
    public void setPARTICIPANT_ID( Long participant_id ) { this.PARTICIPANT_ID = participant_id; }
    public void setJOB( Jobs job ) { this.JOB = job; }
    public void setTEXT_RESPONSES( TextData text_responses ) { this.TEXT_RESPONSES.add(text_responses);}
    public void setNUMBER_RESPONSES( NumberData number_responses ) { this.NUMBER_RESPONSES.add(number_responses);}
}
