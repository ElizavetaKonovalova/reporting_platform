package application.repositories;

import application.models.JobStructuralMaps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JobStructuralMapRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public JobStructuralMapRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* GETTERS */

    /* Select a work unit by its ID in the database */
    public List<JobStructuralMaps> getWorkUnitByID(Long id) {
        return this.jdbcTemplate.query( "SELECT * FROM job_structural_maps WHERE db_id = ?", wuMapper, id);
    }

    /* Select a work unit by its Name */
    public List<JobStructuralMaps> getJobStructuralMapsByName(String name) {
        return this.jdbcTemplate.query( "SELECT * FROM job_structural_maps WHERE wu_name = ?", wuMapper, name);
    }

    /* Select a work unit by its Work Unit Code */
    public List<JobStructuralMaps> getWorkUnitByWUCode(String wu_code) {
        return this.jdbcTemplate.query( "SELECT * FROM job_structural_maps WHERE wu_code = ?",
                    wuMapper, Integer.parseInt(wu_code));
    }

    /* Select a work unit by its Job ID */
    public List<JobStructuralMaps> getWUByJobID(String job_id) {
        return this.jdbcTemplate.query( "SELECT * FROM job_structural_maps WHERE job_id = ?",
                    wuMapper, Long.parseLong(job_id));
    }

    /* Select a work unit by its Work Unit ID */
    public List<JobStructuralMaps> getWorkUnitByWUID(String wuid) {
        return this.jdbcTemplate.query( "SELECT * FROM job_structural_maps WHERE wu_id = ?",
                    wuMapper, Integer.parseInt(wuid));
    }

    /* Select work units by level */
    public List<JobStructuralMaps> getJobStructuralMapsByLevel(String level) {
        return this.jdbcTemplate.query( "SELECT * FROM job_structural_maps WHERE ? IN (wu_level_one, wu_level_zero)",
                wuMapper, level);
    }

    /* Select work units by their Cohort */
    public List<JobStructuralMaps> getJobStructuralMapsByCohort(String wucohort) {
        return this.jdbcTemplate.query("SELECT * FROM job_structural_maps WHERE cohort = ?", wuMapper, wucohort);
    }

    /* Select work units by their Denominator */
    public List<JobStructuralMaps> getJobByDenominator(String denominator) {
        return this.jdbcTemplate.query("SELECT * FROM job_structural_maps WHERE denominator = ?",
                wuMapper, Integer.parseInt(denominator));
    }


    /* REMOVALS */



    /* Remove a Work Unit by its Work Unit Code */
    public void removeByWUCode(String wu_code) {
        this.jdbcTemplate.update("DELETE FROM job_structural_maps WHERE wu_code = ?",
                Long.parseLong(wu_code));
    }

    /* Remove a Work Unit by its Work Unit ID */
    public void removeByWUID(String wu_id) {
        this.jdbcTemplate.update("DELETE FROM job_structural_maps WHERE wu_id = ?",
                Integer.parseInt(wu_id));
    }

    /* Remove a Work Unit by its database ID */
    public void removeByDBID(String db_id) {
        this.jdbcTemplate.update("DELETE FROM job_structural_maps WHERE db_id = ?", Long.parseLong(db_id));
    }

    /* Remove a Work Unit by its cohort */
    public void removeByCohort(String cohort) {
        String query = "DELETE FROM job_structural_maps WHERE cohort = ?";
        this.jdbcTemplate.update(query, cohort);
    }

    /* Remove Work Units by their Job ID */
    public void removeByJobID(String job_id) {
        this.jdbcTemplate.update("DELETE FROM job_structural_maps WHERE job_id = ?", Long.parseLong(job_id));
    }

    /* Remove Work Units by level one */
    public void removeByLevelOne(String wu_lvl_one) {
        this.jdbcTemplate.update("DELETE FROM job_structural_maps WHERE wu_level_one = ?", wu_lvl_one);
    }

    /* Remove Work Units by level zero */
    public void removeByLevelZero(String wu_lvl_zero) {
        this.jdbcTemplate.update("DELETE FROM job_structural_maps WHERE wu_level_zero = ?", wu_lvl_zero);
    }

    /* Remove Work Units by their Denominator */
    public void removeByDenominator(String denominator) {
        this.jdbcTemplate.update("DELETE FROM job_structural_maps WHERE denominator = ?", Integer.parseInt(denominator));
    }


    /* NULLERS */


    /* UPDATERS */

    /* Set a Work Unit Name to null by its Work Unit Code */
    public void updateWUNameByWUCode(String wu_code, String wu_name) {
        this.jdbcTemplate.update("UPDATE job_structural_maps SET wu_name = ? WHERE wu_code = ?", wu_name, Long.parseLong(wu_code));
    }



    /* CREATORS */

    /* Create a work unit */
    public void create(String cohort, String denominator, String job_id, String level_zero, String level_one,
                       String name, String workunitcode, String workunitid) throws Exception {

        String query = "INSERT INTO job_structural_maps (cohort, denominator, job_id, wu_code, wu_level_one, " +
                "wu_level_zero, wu_name, wu_id) VALUES (?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(query, cohort, Integer.parseInt(denominator), Long.parseLong(job_id),
                Integer.parseInt(workunitcode), level_one, level_zero, name, Integer.parseInt(workunitid));
    }


    /* HELPERS */


    /* Check if a work unit code exists in a parsed job. */
    public Long isWUExists(Long job_id, String wu_code) {
        try {
            JobStructuralMaps jobs = this.jdbcTemplate.queryForObject(
                    "SELECT * FROM job_structural_maps WHERE job_id = ? AND wu_code = ?",
                    wuMapper, job_id, Long.parseLong(wu_code));
            return jobs.getWU_CODE();
        } catch (Exception e) { return 0L; }
    }

    /* Map data from the database to the JobStructuralMaps model */
    private static final RowMapper<JobStructuralMaps> wuMapper = new RowMapper<JobStructuralMaps>() {
        public JobStructuralMaps mapRow(ResultSet rs, int rowNum) throws SQLException {
            JobStructuralMaps jobStructuralMaps = new JobStructuralMaps();
            jobStructuralMaps.setDB_ID(rs.getLong("db_id"));
            jobStructuralMaps.setCOHORT(rs.getString("cohort"));
            jobStructuralMaps.setDENOMINATOR(rs.getInt("denominator"));
            jobStructuralMaps.setWU_LEVEL_ONE(rs.getString("wu_level_one"));
            jobStructuralMaps.setWU_LEVEL_ZERO(rs.getString("wu_level_zero"));
            jobStructuralMaps.setNAME(rs.getString("wu_name"));
            jobStructuralMaps.setWU_CODE(rs.getLong("wu_code"));
            jobStructuralMaps.setJOB_ID(rs.getLong("job_id"));
            jobStructuralMaps.setDATE_MODIFIED(rs.getDate("date_modified"));
            return jobStructuralMaps;
        }
    };
}
