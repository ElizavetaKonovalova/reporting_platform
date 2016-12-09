package application.models;

import org.hibernate.annotations.Type;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
@Table(name = "text_data", indexes = {@Index(unique = true, columnList = "redflag_status, shadow_status", name = "textdataIndex")})
public class TextData {

    @Id @Column(name ="field_id", columnDefinition = "SERIAL", nullable = false)
    @Type(type = "pg-uuid")
    private Long FIELD_ID;

    @ManyToOne @JoinColumn(name = "participant_id")
    private Participants PARTICIPANT_ID;

    @Column(length = 40) @Size(max = 40)
    private String REDFLAG_STATUS;

    @Column(length = 600) @Size(max = 600)
    private String RESPONSE_VALUE;

    private Date DATE_MODIFIED;
    private String SHADOW_STATUS;

    /* Simple getters */
    public Long getFIELD_ID() { return this.FIELD_ID; }
    public String getREDFLAG_STATUS() { return this.REDFLAG_STATUS; }
    public String getRESPONSE_VALUE() { return  this.RESPONSE_VALUE; }
    public String getSHADOW_STATUS() { return this.SHADOW_STATUS; }
    public Date getDATE_MODIFIED() { return this.DATE_MODIFIED; }
    public Participants getPARTICIPANT_ID() { return this.PARTICIPANT_ID; }

    /* Simple setters */
    public void setFIELD_ID( Long field_id ) { this.FIELD_ID = field_id; }
    public void setPARTICIPANT_ID( Participants participant_id ) { this.PARTICIPANT_ID = participant_id; }
    public void setREDFLAG_STATUS( String redflag_status ) { this.REDFLAG_STATUS = redflag_status; }
    public void setRESPONSE_VALUE( String response_value ) { this.RESPONSE_VALUE = response_value; }
    public void setSHADOW_STATUS( String shadow_status ) {this.SHADOW_STATUS = shadow_status; }
    public void setDATE_MODIFIED( Date date_modified ) { this.DATE_MODIFIED = date_modified; }
}
