package application.repositories;

import application.models.Cohorts;
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
public class CohortRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CohortRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* CREATORS */

    /* Create a new cohort */
    public String create(String cohort_name, String cohort_description, String parent_name) {
        if(cohort_name != null) {
            if(parent_name != null) {
                Long check_parent = checkCohort(parent_name);
                if(check_parent != 0) {
                    this.jdbcTemplate.update("INSERT INTO cohorts (cohort_name, description, parent_id) VALUES (?,?,?)",
                            cohort_name,cohort_description, check_parent);
                } else { return "There is no such cohort"; }
            } else {
                this.jdbcTemplate.update("INSERT INTO cohorts (cohort_name, description) VALUES (?,?)",
                        cohort_name,cohort_description);
            }
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
        return this.update("UPDATE cohorts SET cohort_name = ? WHERE cohort_id = ?", cohort_name_old, cohort_name_new);
    }

    /* Update Cohort Description by Cohort Name */
    public String updateCohortDescription(String cohort_name, String description) {
        return this.update("UPDATE cohorts SET description = ? WHERE cohort_id = ?",
                cohort_name, description);
    }

    /* REMOVALS*/

    /* Remove a cohort by its Cohort Name */
    public String removeCohortByName(String cohort_name) {
        return this.update("DELETE FROM cohorts WHERE cohort_id = ?", cohort_name, "");
    }

    /* Remove a cohort by its Parent ID */
    public String removeCohortByParentID(String parent_name) {
       return this.update("DELETE FROM cohorts WHERE parent_id = ?", parent_name, "");
    }


    /* NULLERS */

    /* Set a Parent ID to Null */
    public String nullParentID(String cohort_name) {
        return this.update("UPDATE cohorts SET parent_id = NULL WHERE cohort_id = ?", cohort_name, "");
    }

    /* Set a Description to Null */
    public String nullDescription(String cohort_name) {
        return this.update("UPDATE cohorts SET description = NULL WHERE cohort_id = ?", cohort_name, "");
    }

    /* GETTERS */

    /* Find all Cohorts */
    public List<Cohorts> getAll() {
        return this.jdbcTemplate.query("SELECT * FROM cohorts ", cohortsRowMapper);
    }

    /* Find a Cohort by its Name */
    public List<Cohorts> getCohortByName(String cohort_name) {
        return this.jdbcTemplate.query("SELECT * FROM cohorts WHERE cohort_name = ?", cohortsRowMapper, cohort_name);
    }

    /* Find a Cohort by its Parent Name */
    public List<Cohorts> getCohortByParentName(String parent_name) {
        Long check_parent = checkCohort(parent_name);
        if(check_parent != 0) {
            return this.jdbcTemplate.query("SELECT * FROM cohorts WHERE parent_id = ?", cohortsRowMapper, check_parent);
        } else { return new ArrayList<>(); }
    }

    /* Find a Cohort by its Description */
    public List<Cohorts> getCohortByDescription(String description) {
        return this.jdbcTemplate.query("SELECT * FROM cohorts WHERE description LIKE %?%", cohortsRowMapper, description);
    }

    /* Find a Cohort by its DB ID */
    public List<Cohorts> getCohortByDBID(String db_id) {
        return this.jdbcTemplate.query("SELECT * FROM cohorts WHERE cohort_id = ?", cohortsRowMapper, Long.parseLong(db_id));
    }

    /* HELPERS*/

    private String update(String query, String parameter_one, String parameter_two) {
        if(parameter_one != null) {
            Long check_cohort = checkCohort(parameter_one);
            if(check_cohort != 0) {
                if(!parameter_two.isEmpty()) {
                    this.jdbcTemplate.update(query, parameter_two, check_cohort);
                } else {
                    this.jdbcTemplate.update(query, check_cohort);
                }
                return "Updated";
            } else { return "There is no such cohort"; }
        } else { return "Parameter one cannot be blank"; }
    }

    /* Check if a specified Parent Name exists in the database */
    public Long checkCohort(String cohort_name) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM cohorts WHERE cohort_name = ?",
                    cohortsRowMapper, cohort_name).getCOHORT_ID();
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
            cohorts.setDATE_MODIFIED(rs.getDate("date_modified"));
            return cohorts;
        }
    };
}
