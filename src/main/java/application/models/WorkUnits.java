package application.models;

import javax.persistence.*;

/**
 * Created by ekonovalova on 12/5/2016.
 */

@Entity
@Table(name = "StructuralMaps", indexes = {@Index(name = "workunitsIndex", unique = true, columnList = "wu_id")})
public class WorkUnits {

    @Id
    @Column(name ="id", columnDefinition = "SERIAL", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long ID;
    @Column(nullable = false)
    private String WU_NAME;
    @Column(unique = true, nullable = false)
    private Integer WU_ID;
    private Integer WU_CODE;
    private String MATRIX;
    private Short WU_LEVEL;
    private String COHORT;
    private Integer DENOMINATOR;

    public WorkUnits() {}

    public WorkUnits( String name, Integer id, Integer code){
        this.WU_NAME = name;
        this.WU_ID = id;
        this.WU_CODE = code;
    }

    /*Getters*/
    public String getNAME() { return this.WU_NAME; }
    public String getMATRIX() { return this.MATRIX; }
    public Integer getWU_ID() { return this.WU_ID; }
    public Integer getWU_CODE() { return this.WU_CODE; }
    public Short getLEVEL() { return this.WU_LEVEL; }
    public String getCOHORT() { return this.COHORT; }
    public Integer getDENOMINATOR() { return this.DENOMINATOR; }
    public Long getID() { return this.ID; }

    /*Setters*/
    public void setNAME( String name ) { this.WU_NAME = name; }
    public void setMATRIX( String matrix) { this.MATRIX = matrix; }
    public void setWU_ID(Integer workunitid ) { this.WU_ID = workunitid; }
    public void setWU_CODE(Integer workunitcode ) {this.WU_CODE = workunitcode; }
    public void setLEVEL( Short level) { this.WU_LEVEL = level; }
    public void setDENOMINATOR(Integer denominator) { this.DENOMINATOR = denominator; }
    public void setCOHORT(String cohort) { this.COHORT = cohort; }
    public void setID(Long id) { this.ID = id; }
}
