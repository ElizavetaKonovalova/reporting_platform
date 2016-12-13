package application.models;

import org.hibernate.annotations.Type;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "text_data", indexes = {@Index(columnList = " text_field_id, redflag_status, shadow_status", name = "textdataIndex")})
public class TextData {

    @Id
    @Column(name ="db_id", columnDefinition = "SERIAL", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DB_ID;

    @Column(name ="text_field_id", nullable = false, insertable = false,
            updatable = false)
    private UUID TEXT_FIELD_ID;

    @ManyToOne @JoinColumn(name = "participant_id", referencedColumnName = "participant_id")
    private Participants PARTICIPANTS;

    @Column(insertable = false, updatable = false)
    private Long PARTICIPANT_ID;

    @Column(length = 40) @Size(max = 40)
    private String REDFLAG_STATUS;

    @Column(length = 600) @Size(max = 600)
    private String RESPONSE_VALUE;

    private Date DATE_MODIFIED;
    private String SHADOW_STATUS;

    @ManyToOne(targetEntity = FieldRegistry.class,
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "text_field_id", referencedColumnName = "field_id")
    private FieldRegistry REGISRTY;

    /* Simple getters */
    public UUID getFIELD_ID() { return this.TEXT_FIELD_ID; }
    public String getREDFLAG_STATUS() { return this.REDFLAG_STATUS; }
    public String getRESPONSE_VALUE() { return  this.RESPONSE_VALUE; }
    public String getSHADOW_STATUS() { return this.SHADOW_STATUS; }
    public Date getDATE_MODIFIED() { return this.DATE_MODIFIED; }
    public Participants getPARTICIPANTS() { return this.PARTICIPANTS; }
    public Long getPARTICIPANT_ID() { return this.PARTICIPANT_ID; }
    public Long getDB_ID() { return this.DB_ID; }

    /* Simple setters */
    public void setFIELD_ID( UUID field_id ) { this.TEXT_FIELD_ID = field_id; }
    public void setDB_ID(Long db_id) { this.DB_ID = db_id; }
    public void setPARTICIPANT_ID(Long participant_id) { this.PARTICIPANT_ID = participant_id;}
    public void setPARTICIPANTS( Participants participants ) { this.PARTICIPANTS = participants; }
    public void setREDFLAG_STATUS( String redflag_status ) { this.REDFLAG_STATUS = redflag_status; }
    public void setRESPONSE_VALUE( String response_value ) { this.RESPONSE_VALUE = response_value; }
    public void setSHADOW_STATUS( String shadow_status ) {this.SHADOW_STATUS = shadow_status; }
    public void setDATE_MODIFIED( Date date_modified ) { this.DATE_MODIFIED = date_modified; }
}
