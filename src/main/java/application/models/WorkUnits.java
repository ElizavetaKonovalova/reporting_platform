package application.models;

import javax.persistence.*;

/**
 * Created by ekonovalova on 12/5/2016.
 */

@Entity
@Table(indexes = {@Index(name = "workunitsIndex", unique = true, columnList = "workunitid")})
public class WorkUnits {

    @Id
    @Column(name ="id", columnDefinition = "serial", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long ID;
    private String NAME;
    @Column(unique = true, nullable = false)
    private Integer WORKUNITID;
    private Integer WORKUNITCODE;
    private String MATRIX;
    private String LEVEL;
    private String COHORT;
    private Integer DENOMINATOR;

    public WorkUnits() {}

    public WorkUnits( String name, Integer id, Integer code){
        this.NAME = name;
        this.WORKUNITID = id;
        this.WORKUNITCODE = code;
    }

    /*Getters*/
    public String getNAME() { return this.NAME; }
    public String getMATRIX() { return this.MATRIX; }
    public Integer getWORKUNITID() { return this.WORKUNITID; }
    public Integer getWORKUNITCODE() { return this.WORKUNITCODE; }
    public String getLEVEL() { return this.LEVEL; }
    public String getCOHORT() { return this.COHORT; }
    public Integer getDENOMINATOR() { return this.DENOMINATOR; }
    public Long getID() { return this.ID; }

    /*Setters*/
    public void setNAME( String name ) { this.NAME = name; }
    public void setMATRIX( String matrix) { this.MATRIX = matrix; }
    public void setWORKUNITID( Integer workunitid ) { this.WORKUNITID = workunitid; }
    public void setWORKUNITCODE( Integer workunitcode ) {this.WORKUNITCODE = workunitcode; }
    public void setLEVEL( String level) { this.LEVEL = level; }
    public void setDENOMINATOR(Integer denominator) { this.DENOMINATOR = denominator; }
    public void setCOHORT(String cohort) { this.COHORT = cohort; }
    public void setID(Long id) { this.ID = id; }
}
