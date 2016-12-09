package application.models;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "client_structural_maps", indexes = {@Index(name = "clienttrmapsIndex", unique = true, columnList = "wu_id")},
        uniqueConstraints = {@UniqueConstraint(columnNames = "wu_code")})
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
    @Column(nullable = false, unique = true)
    private Integer WU_CODE;
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
    private Short WU_LEVEL;
    @Column(length = 100)
    @Size(max = 100)
    private String COHORT;
    private Integer DENOMINATOR;

    public ClientsStructuralMaps() {}

    public String getNAME() { return this.WU_NAME; }
    public String getMATRIX_ONE() { return this.MATRIX_ONE; }
    public String getMATRIX_TWO() { return this.MATRIX_TWO; }
    public String getMATRIX_THREE() { return this.MATRIX_THREE; }
    public String getMATRIX_FOUR() { return this.MATRIX_FOUR; }
    public String getMATRIX_FIVE() { return this.MATRIX_FIVE; }
    public Integer getWU_ID() { return this.WU_ID; }
    public Integer getWU_CODE() { return this.WU_CODE; }
    public Short getLEVEL() { return this.WU_LEVEL; }
    public String getCOHORT() { return this.COHORT; }
    public Integer getDENOMINATOR() { return this.DENOMINATOR; }
    public Long getDB_ID() { return this.DB_ID; }

    public void setNAME( String name ) { this.WU_NAME = name; }
    public void setMATRIX_ONE(String matrix) { this.MATRIX_ONE = matrix; }
    public void setMATRIX_TWO(String matrix) { this.MATRIX_TWO = matrix; }
    public void setMATRIX_THREE(String matrix) { this.MATRIX_THREE = matrix; }
    public void setMATRIX_FOUR(String matrix) { this.MATRIX_FOUR = matrix; }
    public void setMATRIX_FIVE(String matrix) { this.MATRIX_FIVE = matrix; }
    public void setWU_ID(Integer workunitid ) { this.WU_ID = workunitid; }
    public void setWU_CODE(Integer workunitcode ) {this.WU_CODE = workunitcode; }
    public void setLEVEL( Short level) { this.WU_LEVEL = level; }
    public void setDENOMINATOR(Integer denominator) { this.DENOMINATOR = denominator; }
    public void setCOHORT(String cohort) { this.COHORT = cohort; }
    public void setDB_ID(Long id) { this.DB_ID = id; }
}
