package application.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "demo_data", indexes = {@Index(name = "demoIndex", columnList = "demo_field_id")})
public class DemoData {

    @Id @Type(type = "pg-uuid")
    private Long DB_ID;

    @Column(name ="demo_field_id", nullable = false, insertable = false,
            updatable = false)
    private UUID DEMO_FIELD_ID;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "participant_id")
    private Participants PARTICIPANT_ID;

    private Short RESPONSE_VALUE;

    @Column(name = "demo_cohort_id", nullable = false,
            updatable = false, insertable = false)
    private Long DEMO_COHORT_ID;

    private Date DATE_MODIFIED;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Cohorts.class,
            cascade = CascadeType.ALL, mappedBy = "DEMO_RESPONSES")
    private List<Cohorts> COHORTS;

    @ManyToOne(targetEntity = FieldRegistry.class,
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "demo_field_id", referencedColumnName = "field_id")
    private FieldRegistry REGISRTY;


    /* Simple getters */
    public UUID getFIELD_ID() { return this.DEMO_FIELD_ID; }
    public Short getRESPONSE_VALUE() { return  this.RESPONSE_VALUE; }
    public Date getDATE_MODIFIED() { return this.DATE_MODIFIED; }
    public Participants getPARTICIPANT_ID() { return this.PARTICIPANT_ID; }

    /* Simple setters */
    public void setFIELD_ID( UUID field_id ) { this.DEMO_FIELD_ID = field_id; }
    public void setPARTICIPANT_ID( Participants participant_id ) { this.PARTICIPANT_ID = participant_id; }
    public void setRESPONSE_VALUE( Short response_value ) { this.RESPONSE_VALUE = response_value; }
    public void setDATE_MODIFIED( Date date_modified ) { this.DATE_MODIFIED = date_modified; }
}
