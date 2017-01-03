package application.models;

import java.sql.Date;

public class JobStructuralMaps {

    private Long DB_ID;
    private Date DATE_MODIFIED;
    private Long WU_CODE;
    private String WU_NAME;
    private Long WU_ID;
    private String WU_LEVEL_ZERO;
    private String WU_LEVEL_ONE;
    private String COHORT;
    private Integer DENOMINATOR;
    private Long PARTICIPANT_ID;

    public String getNAME() { return this.WU_NAME; }
    public Long getWU_ID() { return this.WU_ID; }
    public Long getWU_CODE() { return this.WU_CODE; }
    public Date getDATE_MODIFIED() { return this.DATE_MODIFIED; }
    public String getWU_LEVEL_ZERO() { return this.WU_LEVEL_ZERO; }
    public String getWU_LEVEL_ONE() { return this.WU_LEVEL_ONE; }
    public String getCOHORT() { return this.COHORT; }
    public Integer getDENOMINATOR() { return this.DENOMINATOR; }
    public Long getDB_ID() { return this.DB_ID; }

    public void setNAME( String name ) { this.WU_NAME = name; }
    public void setWU_ID(Long workunitid ) { this.WU_ID = workunitid; }
    public void setWU_CODE(Long workunitcode ) {this.WU_CODE = workunitcode; }
    public void setWU_LEVEL_ZERO( String level) { this.WU_LEVEL_ZERO = level; }
    public void setWU_LEVEL_ONE( String level) { this.WU_LEVEL_ONE = level; }
    public void setDENOMINATOR(Integer denominator) { this.DENOMINATOR = denominator; }
    public void setCOHORT(String cohort) { this.COHORT = cohort; }
    public void setDB_ID(Long id) { this.DB_ID = id; }
    public void setDATE_MODIFIED(Date date_modified) { this.DATE_MODIFIED = date_modified; }
    public void setPARTICIPANT_ID(Long participant_id) { this.PARTICIPANT_ID = participant_id; }

}
