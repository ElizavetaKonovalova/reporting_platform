package application.repositories;

import application.models.JobStructuralMaps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JobStructuralMapRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /* GETTERS */

    /* Select a work unit by its ID in the database */
    public JobStructuralMaps getWorkUnitByID(Long id) {
        try {
            String query = "SELECT * FROM job_structural_maps WHERE db_id = ?";
            return this.jdbcTemplate.queryForObject( query, wuMapper, id);
        } catch (Exception e) { return new JobStructuralMaps(); }
    }

    /* Select a work unit by its Name */
    public List<JobStructuralMaps> getJobStructuralMapsByName(String name) {
        String query = "SELECT * FROM job_structural_maps WHERE wu_name = ?";
        return this.jdbcTemplate.query( query, wuMapper, name);
    }

    /* Select a work unit by its Work Unit Code */
    public JobStructuralMaps getWorkUnitByWUCode(Integer wucode) {
        try {
            String query = "SELECT * FROM job_structural_maps WHERE wu_code = ?";
            return this.jdbcTemplate.queryForObject( query, wuMapper, wucode);
        } catch (Exception e) { return new JobStructuralMaps(); }
    }

    /* Select a work unit by its Job ID */
    public List<JobStructuralMaps> getWUByJobID(String job_id) {
        try {
            Long job_id_int = Long.parseLong(job_id);
            String query = "SELECT * FROM job_structural_maps WHERE job_id = ?";
            return this.jdbcTemplate.query( query, wuMapper, job_id_int);
        } catch (Exception e) { return new ArrayList<>(); }
    }

    /* Select a work unit by its Work Unit ID */
    public JobStructuralMaps getWorkUnitByWUID(String wuid) {
        try {
            Integer workunitid = Integer.parseInt(wuid);
            String query = "SELECT * FROM job_structural_maps WHERE wu_id = ?";
            return this.jdbcTemplate.queryForObject( query, wuMapper, workunitid);
        } catch (Exception e) { return new JobStructuralMaps(); }
    }

    /* Select work units by level */
    public List<JobStructuralMaps> getJobStructuralMapsByLevel(String level) {
        String query = "SELECT * FROM job_structural_maps WHERE ? IN (wu_level_one, wu_level_zero)";
        return this.jdbcTemplate.query( query, wuMapper, level);
    }

    /* Select work units by their Cohort */
    public List<JobStructuralMaps> getJobStructuralMapsByCohort(String wucohort) {
        String query = "SELECT * FROM job_structural_maps WHERE cohort = ?";
        return this.jdbcTemplate.query( query, wuMapper, wucohort);
    }

    /* Select work units by their Denominator */
    public List<JobStructuralMaps> getJobByDenominator(String denominator) {
        Integer denominator_int = Integer.parseInt(denominator);
        String query = "SELECT * FROM job_structural_maps WHERE denominator = ?";
        return this.jdbcTemplate.query( query, wuMapper, denominator_int);
    }


    /* REMOVALS */



    /* Remove a Work Unit by its Work Unit Code */
    public void removeByWUCode(String wu_code) {
        Long wu_code_long = Long.parseLong(wu_code);
        String query = "DELETE FROM job_structural_maps WHERE wu_code = ?";
        this.jdbcTemplate.update(query, wu_code_long);
    }

    /* Remove a Work Unit by its Work Unit Name */
    public void removeByWUName(String wu_name) {
        String query = "DELETE FROM job_structural_maps WHERE wu_name = ?";
        this.jdbcTemplate.update(query, wu_name);
    }

    /* Remove a Work Unit by its Work Unit ID */
    public void removeByWUID(String wu_id) {
        Integer wu_id_int = Integer.parseInt(wu_id);
        String query = "DELETE FROM job_structural_maps WHERE wu_id = ?";
        this.jdbcTemplate.update(query, wu_id_int);
    }

    /* Remove a Work Unit by its database ID */
    public void removeByDBID(String db_id) {
        Long db_id_long = Long.parseLong(db_id);
        String query = "DELETE FROM job_structural_maps WHERE db_id = ?";
        this.jdbcTemplate.update(query, db_id_long);
    }

    /* Remove a Work Unit by its cohort */
    public void removeByCohort(String cohort) {
        String query = "DELETE FROM job_structural_maps WHERE cohort = ?";
        this.jdbcTemplate.update(query, cohort);
    }

    /* Remove Work Units by their Job ID */
    public void removeByJobID(String job_id) {
        Long job_id_long = Long.parseLong(job_id);
        String query = "DELETE FROM job_structural_maps WHERE job_id = ?";
        this.jdbcTemplate.update(query, job_id_long);
    }

    /* Remove Work Units by level one */
    public void removeByLevelOne(String wu_lvl_one) {
        String query = "DELETE FROM job_structural_maps WHERE wu_level_one = ?";
        this.jdbcTemplate.update(query, wu_lvl_one);
    }

    /* Remove Work Units by level zero */
    public void removeByLevelZero(String wu_lvl_zero) {
        String query = "DELETE FROM job_structural_maps WHERE wu_level_zero = ?";
        this.jdbcTemplate.update(query, wu_lvl_zero);
    }

    /* Remove Work Units by their Denominator */
    public void removeByDenominator(String denominator) {
        Integer denominator_int = Integer.parseInt(denominator);
        String query = "DELETE FROM job_structural_maps WHERE denominator = ?";
        this.jdbcTemplate.update(query, denominator_int);
    }


    /* NULLERS */




    /* CREATORS */

    /* Create a work unit */
    public void create(String cohort, String denominator, String job_id, String level_zero, String level_one,
                       String name, String workunitcode, String workunitid) throws Exception {

        Integer denominatorint = Integer.parseInt(denominator);
        Integer wucodeint = Integer.parseInt(workunitcode);
        Integer workunitidint = Integer.parseInt(workunitid);
        Long job_id_long = Long.parseLong(job_id);


        String query = "INSERT INTO job_structural_maps (cohort, denominator, job_id, wu_code, wu_level_one, " +
                "wu_level_zero, wu_name, wu_id) VALUES (?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(query, cohort, denominatorint, job_id_long, wucodeint, level_one, level_zero, name, workunitidint);
    }


    /* HELPERS */

    /* Check if a work unit code exists in a parsed job. */
    public Long isWUExists(Long job_id, String wu_code) {
        try {
            Long wu_code_long = Long.parseLong(wu_code);
            String query = "SELECT wu_code FROM job_structural_maps WHERE job_id = ? AND wu_code = ?";
            JobStructuralMaps jobs = this.jdbcTemplate.queryForObject(query, wuMapper, job_id, wu_code_long);
            return jobs.getWU_CODE();
        } catch (Exception e) { return 0L; }
    }

    /* Map data from the database to the JobStructuralMaps model */
    private static final RowMapper<JobStructuralMaps> wuMapper = new RowMapper<JobStructuralMaps>() {
        public JobStructuralMaps mapRow(ResultSet rs, int rowNum) throws SQLException {
            JobStructuralMaps JobStructuralMaps = new JobStructuralMaps();
            JobStructuralMaps.setDB_ID(rs.getLong("db_id"));
            JobStructuralMaps.setCOHORT(rs.getString("cohort"));
            JobStructuralMaps.setDENOMINATOR(rs.getInt("denominator"));
            JobStructuralMaps.setWU_LEVEL_ONE(rs.getString("wu_level_one"));
            JobStructuralMaps.setWU_LEVEL_ZERO(rs.getString("wu_level_zero"));
            JobStructuralMaps.setNAME(rs.getString("wu_name"));
            JobStructuralMaps.setWU_CODE(rs.getLong("wu_code"));
            JobStructuralMaps.setJOB_ID(rs.getLong("job_id"));
            return JobStructuralMaps;
        }
    };
}
