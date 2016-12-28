package application.repositories;

import application.models.SurveyTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository("surveyTypes")
public class SurveyTypeRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SurveyTypeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new JdbcTemplate();
    }


    /* GETTERS */


    /* Select a survey type based on its Type Name [a.k.a Workforce, Partner] */
    public SurveyTypes getByTypeName(String typename) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM survey_types WHERE type_name = ?",
                    surveyTypesRowMapper, typename);
        } catch (Exception e) { return new SurveyTypes(); }
    }

    /* Select a survey type based on its SubType Name [a.k.a Employees, Volunteers, Ambulatory ] */
    public SurveyTypes getBySubTypeName(String subtypename) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM survey_types WHERE subtype_name = ?",
                    surveyTypesRowMapper, subtypename);
        } catch (Exception e) { return new SurveyTypes(); }
    }

    /* Select a survey type based on its SubType Name */
    public SurveyTypes getByID(String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM survey_types WHERE survey_type_id = ?",
                    surveyTypesRowMapper, Long.parseLong(id));
        } catch (Exception e) { return new SurveyTypes(); }
    }


    /* CREATORS */


    /* Create a survey type */
    public void create(String subtypename, String typename) {
        this.jdbcTemplate.update("INSERT INTO survey_types (subtype_name, type_name) VALUES (?,?)",
                subtypename, typename);
    }


    /* REMOVALS */

    /* Remove a survey type by its ID */
    public void removeSubTypeByID(String id) {
        this.jdbcTemplate.update("DELETE FROM survey_types WHERE survey_type_id = ?",
                Long.parseLong(id));
    }

    /* Remove a survey type by its Survey SubType Name */
    public void removeSubTypeBySubName(String subtypename) {
        this.jdbcTemplate.update("DELETE FROM survey_types WHERE subtype_name = ?",
                subtypename);
    }

    /* Remove a survey type by its Survey Type Name */
    public void removeSubTypeByTypeName(String typename) {
        this.jdbcTemplate.update("DELETE FROM survey_types WHERE type_name = ?",
                typename);
    }


    /* HELPERS */

    /* Check if a Survey Type exists in the database */
    public SurveyTypes surveyTypeExist(String subtype_name) {
        try {
            return this.jdbcTemplate.queryForObject( "SELECT * FROM survey_types WHERE subtype_name = ?",
                    surveyTypesRowMapper, subtype_name);
        } catch (Exception e) { return new SurveyTypes(); }
    }

    /* Map data from the database to the SurveyTypes model */
    private static final RowMapper<SurveyTypes> surveyTypesRowMapper = new RowMapper<SurveyTypes>() {
        public SurveyTypes mapRow(ResultSet rs, int rowNum) throws SQLException {
            SurveyTypes surveyTypes = new SurveyTypes();
            surveyTypes.setSURVEYTYPE_ID(rs.getLong("id"));
            surveyTypes.setSUBTYPE_NAME(rs.getString("subtype_name"));
            surveyTypes.setTYPE_NAME(rs.getString("type_name"));
            return surveyTypes;
        }
    };
}
