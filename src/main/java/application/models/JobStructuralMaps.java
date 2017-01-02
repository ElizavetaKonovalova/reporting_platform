package application.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "job_structural_maps", indexes = {@Index(name = "jobstrmapsIndex", unique = true, columnList = "wu_id, wu_code")},
        uniqueConstraints = {@UniqueConstraint(columnNames = "wu_code")})
public class JobStructuralMaps {

    @Id @Column(name ="db_id", columnDefinition = "SERIAL", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DB_ID;

    @Column(nullable = false)
    private Date DATE_MODIFIED;
    private Long WU_CODE;

    @Column(nullable = false, length = 100) @Size(max = 100)
    private String WU_NAME;

    @Column(nullable = false, updatable = false, insertable = false)
    private Long WU_ID;

    @OneToMany(targetEntity = Participants.class,
            fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "WU_FOR_PARTICIPANT")
    private List<Participants> PARTICIPANTS_PER_WU;

    private String WU_LEVEL_ZERO;
    private String WU_LEVEL_ONE;

    @Column(length = 100) @Size(max = 100)
    private String COHORT;

    private Integer DENOMINATOR;

    private Long JOB_ID;

    public JobStructuralMaps() {}

    public String getNAME() { return this.WU_NAME; }
    public Long getWU_ID() { return this.WU_ID; }
    public Long getWU_CODE() { return this.WU_CODE; }
    public Date getDATE_MODIFIED() { return this.DATE_MODIFIED; }
    public String getWU_LEVEL_ZERO() { return this.WU_LEVEL_ZERO; }
    public String getWU_LEVEL_ONE() { return this.WU_LEVEL_ONE; }
    public String getCOHORT() { return this.COHORT; }
    public Integer getDENOMINATOR() { return this.DENOMINATOR; }
    public Long getDB_ID() { return this.DB_ID; }
    public Collection<Participants> getPARTICIPANT() { return this.PARTICIPANTS_PER_WU; }
    public Long getJOB_ID() { return this.JOB_ID; }

    public void setNAME( String name ) { this.WU_NAME = name; }
    public void setWU_ID(Long workunitid ) { this.WU_ID = workunitid; }
    public void setWU_CODE(Long workunitcode ) {this.WU_CODE = workunitcode; }
    public void setWU_LEVEL_ZERO( String level) { this.WU_LEVEL_ZERO = level; }
    public void setWU_LEVEL_ONE( String level) { this.WU_LEVEL_ONE = level; }
    public void setDENOMINATOR(Integer denominator) { this.DENOMINATOR = denominator; }
    public void setCOHORT(String cohort) { this.COHORT = cohort; }
    public void setDB_ID(Long id) { this.DB_ID = id; }
    public void setJOB_ID(Long id) { this.JOB_ID = id; }
    public void setDATE_MODIFIED(Date date_modified) { this.DATE_MODIFIED = date_modified; }
    public void setPARTICIPANT( Participants participant ) {this.PARTICIPANTS_PER_WU.add(participant); }

}
