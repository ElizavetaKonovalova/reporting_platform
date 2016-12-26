package application.repositories;

import application.models.Cohorts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CohortRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /* CREATORS */

    /* Create a new cohort */
    public String create(String cohort_name, String cohort_description) {
        if(!cohort_name.isEmpty()) {
            this.jdbcTemplate.update("INSERT INTO cohorts (cohort_name, description) VALUES (?,?)",
                    cohort_name,cohort_description);
            return "Created";
        } else { return "Cohort name cannot be blank"; }
    }

    /* UPDATERS */

    /* Update Parent ID by Cohort Name */
    public String updateParentID(String cohort_name, String parent) {
        Long check_parent = checkCohort(parent);
        Long check_cohort = checkCohort(cohort_name);
        if(check_parent != 0 && check_cohort != 0) {
            this.jdbcTemplate.update("UPDATE cohorts SET parent_id = ? WHERE cohort_id = ?",
                    check_parent, check_cohort);
            return "Updated";
        } else { return "There is no such cohort"; }
    }

    /* Update Cohort Name by Cohort Name */
    public String updateCohortName(String cohort_name_old, String cohort_name_new) {
        if(cohort_name_new != null) {
            return this.update("UPDATE cohorts SET cohort_name = ? WHERE cohort_id = ?", cohort_name_old, cohort_name_new);
        } else { return "A new name cannot be blank"; }
    }

    /* Update Cohort Description by Cohort Name */
    public String updateCohortDescription(String cohort_name, String description) {
        return this.update("UPDATE cohorts SET description = ? WHERE cohort_id = ?",
                cohort_name, description);
    }

    /* REMOVALS*/

    /* Remove a cohort by its Cohort Name */
    public String removeCohortByName(String cohort_name) {
        return this.remove("DELETE FROM cohorts WHERE cohort_id = ?", cohort_name);
    }

    /* Remove a cohort by its Parent ID */
    public String removeCohortByParentID(String parent_name) {
       return this.remove("DELETE FROM cohorts WHERE parent_id = ?", parent_name);
    }


    /* NULLERS */

    /* GETTERS */

    /* HELPERS*/

    private String update(String query, String parameter_one, String parameter_two) {
        Long check_cohort = checkCohort(parameter_one);
        if(check_cohort != 0) {
            this.jdbcTemplate.update(query, parameter_two, check_cohort);
            return "Updated";
        } else { return "There is no such cohort"; }
    }

    /* Helper for removing rows form the database */
    private String remove(String query, String parameter) {
        Long check_cohort = checkCohort(parameter);
        if(check_cohort != 0) {
            this.jdbcTemplate.update(query, check_cohort);
            return "Removed";
        } else { return "There is no such cohort"; }
    }

    /* Check if a specified Parent Name exists in the database */
    private Long checkCohort(String parent_name) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM cohorts WHERE cohort_name = ?",
                    cohortsRowMapper, parent_name).getCOHORT_ID();
        } catch (Exception e) { return 0L; }
    }

    /* Map data from the database to the Cohorts model */
    private static final RowMapper<Cohorts> cohortsRowMapper = new RowMapper<Cohorts>() {
        public Cohorts mapRow(ResultSet rs, int rowNum) throws SQLException {
            Cohorts cohorts = new Cohorts();
            cohorts.setCOHORT_ID(rs.getLong("cohort_id"));
            cohorts.setCOHORT_NAME(rs.getString("cohort_name"));
            cohorts.setDESCRIPTION(rs.getString("description"));
            cohorts.setPARENT_ID(rs.getLong("parent_id"));
            return cohorts;
        }
    };
}
