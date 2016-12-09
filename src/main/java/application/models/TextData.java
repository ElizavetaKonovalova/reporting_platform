package application.models;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by ekonovalova on 08-Dec-16.
 */
@Entity
@Table(name = "text_data", indexes = {@Index(unique = true, columnList = "field_name", name = "textdataIndex")})
public class TextData {

    @Id @Column(name ="field_id", columnDefinition = "SERIAL", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long FIELD_ID;
    @Column(length = 100)
    @Size(max = 100)
    private String FIELD_NAME;
    @ManyToOne @JoinColumn(name = "participant_id")
    private Participants PARTICIPANT_ID;
    @Column(length = 40)
    @Size(max = 40)
    private String REDFLAG_RESULT;
    @Column(length = 600)
    @Size(max = 600)
    private String RESPONSE_VALUE;
}
