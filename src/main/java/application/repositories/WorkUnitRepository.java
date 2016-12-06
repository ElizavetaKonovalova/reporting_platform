package application.repositories;

import application.models.Jobs;
import application.models.WorkUnits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekonovalova on 12/5/2016.
 */

@Repository
public class WorkUnitRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /* Select a work unit by its ID in the database */
    public WorkUnits getWorkUnitByID(Long id) {
        String query = "SELECT * FROM work_units WHERE id = ?";
        try { return this.jdbcTemplate.queryForObject( query, wuMapper, id); }
        catch (Exception e) { return new WorkUnits(); }
    }

    /* Select a work unit by its Name */
    public List<WorkUnits> getWorkUnitsByName(String name) {
        String query = "SELECT * FROM work_units WHERE wuname = ?";
        try { return this.jdbcTemplate.query( query, wuMapper, name); }
        catch (Exception e) { return new ArrayList<>(); }
    }

    /* Select a work unit by its Work Unit Code in the database */
    public WorkUnits getWorkUnitByWUCode(Integer wucode) {
        String query = "SELECT * FROM work_units WHERE workunitcode = ?";
        try { return this.jdbcTemplate.queryForObject( query, wuMapper, wucode); }
        catch (Exception e) { return new WorkUnits(); }
    }

    /* Select a work unit by its Work Unit ID */
    public WorkUnits getWorkUnitByWUID(String wuid) {
        Integer workunitid = Integer.parseInt(wuid);
        String query = "SELECT * FROM work_units WHERE workunitid = ?";
        try { return this.jdbcTemplate.queryForObject( query, wuMapper, workunitid); }
        catch (Exception e) { return new WorkUnits(); }
    }

    /* Select work units by level */
    public List<WorkUnits> getWorkUnitsByLevel(Short level) {
        String query = "SELECT * FROM work_units WHERE wulevel = ?";
        try { return this.jdbcTemplate.query( query, wuMapper, level); }
        catch (Exception e) { return new ArrayList<>(); }
    }

    /* Select work units by thier Cohort */
    public List<WorkUnits> getWorkUnitsByCohort(String wucohort) {
        String query = "SELECT * FROM work_units WHERE cohort = ?";
        try { return this.jdbcTemplate.query( query, wuMapper, wucohort); }
        catch (Exception e) { return new ArrayList<>(); }
    }

    /* Select work units by thier Cohort */
    public List<WorkUnits> getWorkUnitsByMatrix(String matrix) {
        String query = "SELECT * FROM work_units WHERE matrix = ?";
        try { return this.jdbcTemplate.query( query, wuMapper, matrix); }
        catch (Exception e) { return new ArrayList<>(); }
    }

    /* Create a work unit */
    public void create(String cohort, String denominator, String level, String matrix, String name, String workunitcode,
                       String workunitid) {

        Integer denominatorint = Integer.parseInt(denominator);
        Integer wucodeint = Integer.parseInt(workunitcode);
        Short levelshort = Short.parseShort(level);
        Integer workunitidint = Integer.parseInt(workunitid);

        String query = "INSERT INTO work_units (cohort, denominator, wulevel, matrix, wuname, workunitcode, workunitid) " +
                "VALUES (?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(query, cohort, denominatorint, levelshort, matrix, name, wucodeint, workunitidint);
    }

    /* Map data from the database to the WorkUnits model */
    private static final RowMapper<WorkUnits> wuMapper = new RowMapper<WorkUnits>() {
        public WorkUnits mapRow(ResultSet rs, int rowNum) throws SQLException {
            WorkUnits workUnits = new WorkUnits();
            workUnits.setID(rs.getLong("id"));
            workUnits.setCOHORT(rs.getString("cohort"));
            workUnits.setDENOMINATOR(rs.getInt("denominator"));
            workUnits.setLEVEL(rs.getShort("wulevel"));
            workUnits.setMATRIX(rs.getString("matrix"));
            workUnits.setNAME(rs.getString("wuname"));
            workUnits.setWORKUNITCODE(rs.getInt("workunitcode"));
            workUnits.setWORKUNITID(rs.getInt("workunitid"));
            return workUnits;
        }
    };
}
