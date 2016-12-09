package application.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "demo_data")
public class DemoData {

    @Id
    @Column(name ="field_id", columnDefinition = "SERIAL", nullable = false)
    @Type(type = "pg-uuid")
    private Long FIELD_ID;

    @ManyToOne @JoinColumn(name = "participant_id")
    private Participants PARTICIPANT_ID;
    private Short RESPONSE_VALUE;
    private Date DATE_MODIFIED;
    private Integer COHORT_ID;

    /* Simple getters */
    public Long getFIELD_ID() { return this.FIELD_ID; }
    public Short getRESPONSE_VALUE() { return  this.RESPONSE_VALUE; }
    public Integer getSHADOW_STATUS() { return this.COHORT_ID; }
    public Date getDATE_MODIFIED() { return this.DATE_MODIFIED; }
    public Participants getPARTICIPANT_ID() { return this.PARTICIPANT_ID; }

    /* Simple setters */
    public void setFIELD_ID( Long field_id ) { this.FIELD_ID = field_id; }
    public void setPARTICIPANT_ID( Participants participant_id ) { this.PARTICIPANT_ID = participant_id; }
    public void setRESPONSE_VALUE( Short response_value ) { this.RESPONSE_VALUE = response_value; }
    public void setSHADOW_STATUS( Integer cohort_id ) {this.COHORT_ID = cohort_id; }
    public void setDATE_MODIFIED( Date date_modified ) { this.DATE_MODIFIED = date_modified; }
}
