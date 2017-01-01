package application.models;

import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "number_data", indexes = {@Index(columnList = "number_field_id, positivity_result",
        name = "numbdataIndex")})
public class NumberData {

    @Id
    @Column(name ="db_id", columnDefinition = "SERIAL", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DB_ID;

    @Column(name ="number_field_id", nullable = false, insertable = false,
            updatable = false)
    private UUID NUMBER_FIELD_ID;

    @Column(nullable = false, insertable = false, updatable = false)
    private Long PARTICIPANT_ID;

    @ManyToOne(targetEntity = FieldRegistry.class,
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "number_field_id", referencedColumnName = "field_id")
    private FieldRegistry REGISRTY;

    private char POSITIVITY_RESULT;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "participant_id", referencedColumnName = "participant_id")
    private Participants PARTICIPANTS;

    @Column(nullable = false)
    private Date DATE_MODIFIED;

    private Short RESPONSE_VALUE;

    /* Simple getters */
    public UUID getNUMBER_FIELD_ID() { return this.NUMBER_FIELD_ID; }
    public char getPOSITIVITY_RESULT() { return this.POSITIVITY_RESULT; }
    public Participants getPARTICIPANTS() { return this.PARTICIPANTS; }
    public Date getDATE_MODIFIED() { return this.DATE_MODIFIED; }
    public Short getRESPONSE_VALUE() { return this.RESPONSE_VALUE; }
    public Long getDB_ID() { return this.DB_ID; }
    public Long getPARTICIPANT_ID() { return this.PARTICIPANT_ID; }

    /* Simple setters*/
    public void setNUMBER_FIELD_ID(UUID field_id) { this.NUMBER_FIELD_ID = field_id; }
    public void setPOSITIVITY_RESULT(char positivity_result) { this.POSITIVITY_RESULT = positivity_result; }
    public void setPARTICIPANTS(Participants participants) { this.PARTICIPANTS = participants; }
    public void setDATE_MODIFIED(Date date_modified) { this.DATE_MODIFIED = date_modified; }
    public void setRESPONSE_VALUE(Short response_value) {this.RESPONSE_VALUE = response_value;}
    public void setDB_ID(Long db_id) { this.DB_ID = db_id; }
    public void setPARTICIPANT_ID(Long participant_id) { this.PARTICIPANT_ID = participant_id; }
}
