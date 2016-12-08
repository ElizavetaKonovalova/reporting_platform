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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long PARTICIPANT_ID;
    private Long JOB_ID;
    @ManyToOne @JoinColumn(name="job_id", insertable = false, updatable = false)
    private Jobs JOB;
    @OneToMany(mappedBy = "PARTICIPANT_ID", targetEntity = TextData.class)
    private List<TextData> TEXT_RESPONSES;
}
