package application.models;

import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "number_data", indexes = {@Index(unique = true, columnList = "positivity_result",
        name = "numbdataIndex")})
public class NumberData {

    @Id
    @Column(name ="field_id", nullable = false)
    @Type(type = "pg-uuid")
    private UUID NUMBER_FIELD_ID;
    private char POSITIVITY_RESULT;
    @ManyToOne @JoinColumn(name = "participant_id")
    private Participants PARTICIPANT_ID;
    private Date DATE_MODIFIED;
    private Short RESPONSE_VALUE;

    /* Simple getters */
    public UUID getNUMBER_FIELD_ID() { return this.NUMBER_FIELD_ID; }
    public char getPOSITIVITY_RESULT() { return this.POSITIVITY_RESULT; }
    public Participants getPARTICIPANT_ID() { return this.PARTICIPANT_ID; }
    public Date getDATE_MODIFIED() { return this.DATE_MODIFIED; }
    public Short getRESPONSE_VALUE() { return this.RESPONSE_VALUE; }

    /* Simple setters*/
    public void setNUMBER_FIELD_ID(UUID field_id ) { this.NUMBER_FIELD_ID = field_id; }
    public void setPOSITIVITY_RESULT( char positivity_result ) { this.POSITIVITY_RESULT = positivity_result; }
    public void setPARTICIPANT_ID(Participants participants ) { this.PARTICIPANT_ID = participants; }
    public void setDATE_MODIFIED( Date date_modified ) { this.DATE_MODIFIED = date_modified; }
    public void setRESPONSE_VALUE( Short response_value ) {this.RESPONSE_VALUE = response_value;}

}
