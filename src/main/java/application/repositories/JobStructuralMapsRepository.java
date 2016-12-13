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
public class JobStructuralMapsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /* Select a work unit by its ID in the database */
    public JobStructuralMaps getWorkUnitByID(Long id) {
        String query = "SELECT * FROM job_structural_maps WHERE db_id = ?";
        return this.jdbcTemplate.queryForObject( query, wuMapper, id);
    }

    /* Select a work unit by its Name */
    public List<JobStructuralMaps> getJobStructuralMapsByName(String name) {
        String query = "SELECT * FROM job_structural_maps WHERE wu_name = ?";
        return this.jdbcTemplate.query( query, wuMapper, name);
    }

    /* Select a work unit by its Work Unit Code in the database */
    public JobStructuralMaps getWorkUnitByWUCode(Integer wucode) {
        String query = "SELECT * FROM job_structural_maps WHERE wu_code = ?";
        return this.jdbcTemplate.queryForObject( query, wuMapper, wucode);
    }

    /* Select a work unit by its Work Unit ID */
    public JobStructuralMaps getWorkUnitByWUID(String wuid) {
        Integer workunitid = Integer.parseInt(wuid);
        String query = "SELECT * FROM job_structural_maps WHERE wu_id = ?";
        return this.jdbcTemplate.queryForObject( query, wuMapper, workunitid);
    }

    /* Select work units by level */
    public List<JobStructuralMaps> getJobStructuralMapsByLevel(Short level) {
        String query = "SELECT * FROM job_structural_maps WHERE wu_level = ?";
        return this.jdbcTemplate.query( query, wuMapper, level);
    }

    /* Select work units by thier Cohort */
    public List<JobStructuralMaps> getJobStructuralMapsByCohort(String wucohort) {
        String query = "SELECT * FROM job_structural_maps WHERE cohort = ?";
        return this.jdbcTemplate.query( query, wuMapper, wucohort);
    }

    /* Select work units by thier Cohort */
    public List<JobStructuralMaps> getJobStructuralMapsByMatrix(String matrix) {
        String query = "SELECT * FROM job_structural_maps WHERE ? IN (matrix_one, matrix_two, matrix_three, matrix_four, matrix_five)";
        return this.jdbcTemplate.query( query, wuMapper, matrix);
    }

    /* Create a work unit */
    public void create(String cohort, String denominator, String level, String matrixone, String matrixtwo,
                       String matrixthree, String matrixfour, String matrixfive, String name, String workunitcode,
                       String workunitid) throws Exception {

        Integer denominatorint = Integer.parseInt(denominator);
        Integer wucodeint = Integer.parseInt(workunitcode);
        Short levelshort = Short.parseShort(level);
        Integer workunitidint = Integer.parseInt(workunitid);

        String query = "INSERT INTO job_structural_maps (cohort, denominator, wu_level, matrix_one, matrix_two, matrix_three," +
                "matrix_four, matrix_five, wu_name, wu_code, wu_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(query, cohort, denominatorint, levelshort, matrixone, matrixtwo, matrixthree,
                matrixfour, matrixfive, name, wucodeint, workunitidint);
    }

    /* Map data from the database to the JobStructuralMaps model */
    private static final RowMapper<JobStructuralMaps> wuMapper = new RowMapper<JobStructuralMaps>() {
        public JobStructuralMaps mapRow(ResultSet rs, int rowNum) throws SQLException {
            JobStructuralMaps JobStructuralMaps = new JobStructuralMaps();
            JobStructuralMaps.setDB_ID(rs.getLong("db_id"));
            JobStructuralMaps.setCOHORT(rs.getString("cohort"));
            JobStructuralMaps.setDENOMINATOR(rs.getInt("denominator"));
            JobStructuralMaps.setLEVEL(rs.getShort("wu_level"));
            JobStructuralMaps.setMATRIX_ONE(rs.getString("matrix_one"));
            JobStructuralMaps.setMATRIX_TWO(rs.getString("matrix_two"));
            JobStructuralMaps.setMATRIX_THREE(rs.getString("matrix_three"));
            JobStructuralMaps.setMATRIX_FOUR(rs.getString("matrix_four"));
            JobStructuralMaps.setMATRIX_FIVE(rs.getString("matrix_five"));
            JobStructuralMaps.setNAME(rs.getString("wu_name"));
//            JobStructuralMaps.setWU_CODE(rs.getLong("wu_code"));
//            JobStructuralMaps.setWU_ID(rs.getInt("wu_id"));
            return JobStructuralMaps;
        }
    };
}
