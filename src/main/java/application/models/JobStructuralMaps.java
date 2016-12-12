package application.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "job_structural_maps", indexes = {@Index(name = "jobstrmapsIndex", unique = true, columnList = "wu_id, wu_code")},
        uniqueConstraints = {@UniqueConstraint(columnNames = "wu_code")})
public class JobStructuralMaps {

    @Id @Column(name ="db_id", columnDefinition = "SERIAL", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DB_ID;

    private Long WU_CODE;

    @Column(nullable = false, length = 100) @Size(max = 100)
    private String WU_NAME;

    @ManyToOne(optional = false, fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, targetEntity = ClientsStructuralMaps.class)
    @JoinColumn(name = "WU_ID", referencedColumnName = "WU_ID", nullable = false)
    private ClientsStructuralMaps WU_ID;

    @OneToMany(targetEntity = Participants.class,
            fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "WU_FOR_PARTICIPANT")
    private List<Participants> PARTICIPANTS_PER_WU;

    @Column(length = 20) @Size(max = 20)
    private String MATRIX_ONE;

    @Column(length = 20) @Size(max = 20)
    private String MATRIX_TWO;

    @Column(length = 20) @Size(max = 20)
    private String MATRIX_THREE;

    @Column(length = 20) @Size(max = 20)
    private String MATRIX_FOUR;

    @Column(length = 20) @Size(max = 20)
    private String MATRIX_FIVE;

    private Short WU_LEVEL;

    @Column(length = 100) @Size(max = 100)
    private String COHORT;

    private Integer DENOMINATOR;

    private Long JOB_ID;

    public JobStructuralMaps() {}

    public String getNAME() { return this.WU_NAME; }
    public String getMATRIX_ONE() { return this.MATRIX_ONE; }
    public String getMATRIX_TWO() { return this.MATRIX_TWO; }
    public String getMATRIX_THREE() { return this.MATRIX_THREE; }
    public String getMATRIX_FOUR() { return this.MATRIX_FOUR; }
    public String getMATRIX_FIVE() { return this.MATRIX_FIVE; }
    public ClientsStructuralMaps getWU_ID() { return this.WU_ID; }
    public Long getWU_CODE() { return this.WU_CODE; }
    public Short getLEVEL() { return this.WU_LEVEL; }
    public String getCOHORT() { return this.COHORT; }
    public Integer getDENOMINATOR() { return this.DENOMINATOR; }
    public Long getDB_ID() { return this.DB_ID; }
//    public Collection<Participants> getPARTICIPANT() { return this.PARTICIPANT; }
    public Long getJOB_ID() { return this.JOB_ID; }

    public void setNAME( String name ) { this.WU_NAME = name; }
    public void setMATRIX_ONE(String matrix) { this.MATRIX_ONE = matrix; }
    public void setMATRIX_TWO(String matrix) { this.MATRIX_TWO = matrix; }
    public void setMATRIX_THREE(String matrix) { this.MATRIX_THREE = matrix; }
    public void setMATRIX_FOUR(String matrix) { this.MATRIX_FOUR = matrix; }
    public void setMATRIX_FIVE(String matrix) { this.MATRIX_FIVE = matrix; }
    public void setWU_ID(ClientsStructuralMaps workunitid ) { this.WU_ID = workunitid; }
    public void setWU_CODE(Long workunitcode ) {this.WU_CODE = workunitcode; }
    public void setLEVEL( Short level) { this.WU_LEVEL = level; }
    public void setDENOMINATOR(Integer denominator) { this.DENOMINATOR = denominator; }
    public void setCOHORT(String cohort) { this.COHORT = cohort; }
    public void setDB_ID(Long id) { this.DB_ID = id; }
    public void setJOB_ID(Long id) { this.JOB_ID = id; }
//    public void setPARTICIPANT( Participants participant ) {this.PARTICIPANT.add(participant); }

}
