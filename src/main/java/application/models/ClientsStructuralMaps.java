package application.models;

import java.sql.Date;

public class ClientsStructuralMaps {

    private Long DB_ID;
    private String WU_NAME;
    private Integer WU_ID;
    private String MATRIX_ONE;
    private String MATRIX_TWO;
    private String MATRIX_THREE;
    private String MATRIX_FOUR;
    private String MATRIX_FIVE;
    private String LOCATION;
    private String SECTOR;
    private String NICHE;
    private String WU_LEVEL_ZERO;
    private String WU_LEVEL_ONE;
    private String WU_LEVEL_TWO;
    private String WU_LEVEL_THREE;
    private String WU_LEVEL_FOUR;
    private String WU_LEVEL_FIVE;
    private String COHORT;
    private Date DATE_MODIFIED;
    private Long CLIENT_ID;

    public ClientsStructuralMaps() {}

    public ClientsStructuralMaps(Long db_id, String wu_name, Integer wu_id ) {
        this.DB_ID = db_id;
        this.WU_NAME = wu_name;
        this.WU_ID = wu_id;
    }

    public String getWU_NAME() { return this.WU_NAME; }
    public String getMATRIX_ONE() { return this.MATRIX_ONE; }
    public String getMATRIX_TWO() { return this.MATRIX_TWO; }
    public String getMATRIX_THREE() { return this.MATRIX_THREE; }
    public String getMATRIX_FOUR() { return this.MATRIX_FOUR; }
    public String getMATRIX_FIVE() { return this.MATRIX_FIVE; }
    public Integer getWU_ID() { return this.WU_ID; }
    public String getWU_LEVEL_ZERO() { return this.WU_LEVEL_ZERO; }
    public String getWU_LEVEL_ONE() { return this.WU_LEVEL_ONE; }
    public String getWU_LEVEL_TWO() { return this.WU_LEVEL_TWO; }
    public String getWU_LEVEL_THREE() { return this.WU_LEVEL_THREE; }
    public String getWU_LEVEL_FOUR() { return this.WU_LEVEL_FOUR; }
    public String getWU_LEVEL_FIVE() { return this.WU_LEVEL_FIVE; }
    public String getCOHORT() { return this.COHORT; }
    public Long getDB_ID() { return this.DB_ID; }
    public String getLOCATION() { return this.LOCATION; }
    public String getSECTOR() { return this.SECTOR; }
    public String getNICHE() { return this.NICHE; }
    public Long getCLIENT_ID() { return this.CLIENT_ID; }
    private Date getDATE_MODIFIED() { return this.DATE_MODIFIED; }

    public void setWU_NAME( String name ) { this.WU_NAME = name; }
    public void setMATRIX_ONE(String matrix) { this.MATRIX_ONE = matrix; }
    public void setMATRIX_TWO(String matrix) { this.MATRIX_TWO = matrix; }
    public void setMATRIX_THREE(String matrix) { this.MATRIX_THREE = matrix; }
    public void setMATRIX_FOUR(String matrix) { this.MATRIX_FOUR = matrix; }
    public void setMATRIX_FIVE(String matrix) { this.MATRIX_FIVE = matrix; }
    public void setWU_ID(Integer workunitid ) { this.WU_ID = workunitid; }
    public void setWU_LEVEL_ZERO(String level) { this.WU_LEVEL_ZERO = level; }
    public void setWU_LEVEL_ONE(String level) { this.WU_LEVEL_ONE = level; }
    public void setWU_LEVEL_TWO(String level) { this.WU_LEVEL_TWO = level; }
    public void setWU_LEVEL_THREE(String level) { this.WU_LEVEL_THREE = level; }
    public void setWU_LEVEL_FOUR(String level) { this.WU_LEVEL_FOUR = level; }
    public void setWU_LEVEL_FIVE(String level) { this.WU_LEVEL_FIVE = level; }
    public void setCOHORT(String cohort) { this.COHORT = cohort; }
    public void setDB_ID(Long id) { this.DB_ID = id; }
    public void setLOCATION(String location) { this.LOCATION = location; }
    public void setSECTOR(String sector) { this.SECTOR = sector; }
    public void setNICHE(String niche) { this.NICHE = niche; }
    public void setCLIENT(Long client) { this.CLIENT_ID = client; }
    public void setDATE_MODIFIED(Date date_modified) { this.DATE_MODIFIED = date_modified; }
}
