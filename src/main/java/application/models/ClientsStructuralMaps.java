package application.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "client_structural_maps", indexes = {@Index(name = "clienttrmapsIndex", unique = true, columnList = "wu_id")})
public class ClientsStructuralMaps {

    @Id
    @Column(name ="db_id", columnDefinition = "SERIAL", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DB_ID;
    @Column(nullable = false, length = 100)
    @Size(max = 100)
    private String WU_NAME;
    @Column(unique = true, nullable = false)
    private Integer WU_ID;
    @Column(length = 20)
    @Size(max = 20)
    private String MATRIX_ONE;
    @Column(length = 20)
    @Size(max = 20)
    private String MATRIX_TWO;
    @Column(length = 20)
    @Size(max = 20)
    private String MATRIX_THREE;
    @Column(length = 20)
    @Size(max = 20)
    private String MATRIX_FOUR;
    @Column(length = 20)
    @Size(max = 20)
    private String MATRIX_FIVE;

    @Column(length = 50) @Size(max = 50)
    private String LOCATION;

    @Column(length = 50) @Size(max = 50)
    private String SECTOR;

    @Column(length = 100) @Size(max = 100)
    private String NICHE;

    private Short WU_LEVEL_ZERO;
    private Short WU_LEVEL_ONE;
    private Short WU_LEVEL_TWO;
    private Short WU_LEVEL_THREE;
    private Short WU_LEVEL_FOUR;
    private Short WU_LEVEL_FIVE;

    @Column(length = 100) @Size(max = 100)
    private String COHORT;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CLIENT_ID")
    private Organisations CLIENT;

    @Column(unique = true, nullable = false, name = "wu_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "WU_ID")
    private Set<JobStructuralMaps> JOBS_STRUCTURAL_MAPS;

    public ClientsStructuralMaps() {}

    public String getNAME() { return this.WU_NAME; }
    public String getMATRIX_ONE() { return this.MATRIX_ONE; }
    public String getMATRIX_TWO() { return this.MATRIX_TWO; }
    public String getMATRIX_THREE() { return this.MATRIX_THREE; }
    public String getMATRIX_FOUR() { return this.MATRIX_FOUR; }
    public String getMATRIX_FIVE() { return this.MATRIX_FIVE; }
    public Integer getWU_ID() { return this.WU_ID; }
    public Short getWU_LEVEL_ZERO() { return this.WU_LEVEL_ZERO; }
    public Short getWU_LEVEL_ONE() { return this.WU_LEVEL_ONE; }
    public Short getWU_LEVEL_TWO() { return this.WU_LEVEL_TWO; }
    public Short getWU_LEVEL_THREE() { return this.WU_LEVEL_THREE; }
    public Short getWU_LEVEL_FOUR() { return this.WU_LEVEL_FOUR; }
    public Short getWU_LEVEL_FIVE() { return this.WU_LEVEL_FIVE; }
    public String getCOHORT() { return this.COHORT; }
    public Long getDB_ID() { return this.DB_ID; }
    public String getLOCATION() { return this.LOCATION; }
    public String getSECTOR() { return this.SECTOR; }
    public String getNICHE() { return this.NICHE; }
    public Organisations getCLIENT() { return this.CLIENT; }
    public Set<JobStructuralMaps> getJOBS_STRUCTURAL_MAPS() { return this.JOBS_STRUCTURAL_MAPS; }

    public void setNAME( String name ) { this.WU_NAME = name; }
    public void setMATRIX_ONE(String matrix) { this.MATRIX_ONE = matrix; }
    public void setMATRIX_TWO(String matrix) { this.MATRIX_TWO = matrix; }
    public void setMATRIX_THREE(String matrix) { this.MATRIX_THREE = matrix; }
    public void setMATRIX_FOUR(String matrix) { this.MATRIX_FOUR = matrix; }
    public void setMATRIX_FIVE(String matrix) { this.MATRIX_FIVE = matrix; }
    public void setWU_ID(Integer workunitid ) { this.WU_ID = workunitid; }
    public void setWU_LEVEL_ZERO( Short level) { this.WU_LEVEL_ZERO = level; }
    public void setWU_LEVEL_ONE( Short level) { this.WU_LEVEL_ONE = level; }
    public void setWU_LEVEL_TWO( Short level) { this.WU_LEVEL_TWO = level; }
    public void setWU_LEVEL_THREE( Short level) { this.WU_LEVEL_THREE = level; }
    public void setWU_LEVEL_FOUR( Short level) { this.WU_LEVEL_FOUR = level; }
    public void setWU_LEVEL_FIVE( Short level) { this.WU_LEVEL_FIVE = level; }
    public void setCOHORT(String cohort) { this.COHORT = cohort; }
    public void setDB_ID(Long id) { this.DB_ID = id; }
    public void setLOCATION(String location) { this.LOCATION = location; }
    public void setSECTOR(String sector) { this.SECTOR = sector; }
    public void setNICHE(String niche) { this.NICHE = niche; }
    public void setCLIENT(Organisations client) { this.CLIENT = client; }
    public void setJOBS_STRUCTURAL_MAPS(JobStructuralMaps jobs_structural_maps) { this.JOBS_STRUCTURAL_MAPS.add(jobs_structural_maps); }
}
