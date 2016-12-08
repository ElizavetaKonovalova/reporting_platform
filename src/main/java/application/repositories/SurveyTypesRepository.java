package application.repositories;

import application.models.SurveyTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ekonovalova on 07-Dec-16.
 */

@Repository
public class SurveyTypesRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /* Select a survey type based on its Type Name [a.k.a Workforce, Partner] */
    public SurveyTypes getByTypeName(String typename) {
        String query = "SELECT * FROM survey_types WHERE type_name = ?";
        try {
            return this.jdbcTemplate.queryForObject( query, surveyTypesRowMapper, typename);
        } catch (Exception e) { return new SurveyTypes(); }
    }

    /* Select a survey type based on its SubType Name [a.k.a Employees, Volunteers, Ambulatory ] */
    public SurveyTypes getBySubTypeName(String subtypename) {
        String query = "SELECT * FROM survey_types WHERE subtype_name = ?";
        try {
            return this.jdbcTemplate.queryForObject( query, surveyTypesRowMapper, subtypename);
        } catch (Exception e) { return new SurveyTypes(); }
    }

    /* Select a survey type based on its SubType Name */
    public SurveyTypes getByID(String id) {
        String query = "SELECT * FROM survey_types WHERE survey_type_id = ?";
        try {
            Long subtypeid = Long.parseLong(id);
            return this.jdbcTemplate.queryForObject( query, surveyTypesRowMapper, subtypeid);
        } catch (Exception e) { return new SurveyTypes(); }
    }

    /* Create a survey type */
    public void create(String subtypename, String typename) {
        String query = "INSERT INTO survey_types (subtype_name, type_name) VALUES (?,?)";
        this.jdbcTemplate.update(query, subtypename, typename);
    }

    /* Remove a survey type by its ID */
    public void removeSubTypeByID(String id) {
        Long subytypeid = Long.parseLong(id);
        String query = "DELETE FROM survey_types WHERE survey_type_id = ?";
        this.jdbcTemplate.update(query, subytypeid);
    }

    /* Remove a survey type by its Survey SubType Name */
    public void removeSubTypeBySubName(String subtypename) {
        String query = "DELETE FROM survey_types WHERE subtype_name = ?";
        this.jdbcTemplate.update(query, subtypename);
    }

    /* Remove a survey type by its Survey Type Name */
    public void removeSubTypeByTypeName(String typename) {
        String query = "DELETE FROM survey_types WHERE type_name = ?";
        this.jdbcTemplate.update(query, typename);
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
