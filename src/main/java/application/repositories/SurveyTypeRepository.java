package application.repositories;

import application.models.SurveyTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SurveyTypeRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;


    /* GETTERS */


    /* Select a survey type based on its Type Name [a.k.a Workforce, Partner] */
    public SurveyTypes getByTypeName(String typename) {
        try {
            String query = "SELECT * FROM survey_types WHERE type_name = ?";
            return this.jdbcTemplate.queryForObject( query, surveyTypesRowMapper, typename);
        } catch (Exception e) { return new SurveyTypes(); }
    }

    /* Select a survey type based on its SubType Name [a.k.a Employees, Volunteers, Ambulatory ] */
    public SurveyTypes getBySubTypeName(String subtypename) {
        try {
            String query = "SELECT * FROM survey_types WHERE subtype_name = ?";
            return this.jdbcTemplate.queryForObject( query, surveyTypesRowMapper, subtypename);
        } catch (Exception e) { return new SurveyTypes(); }
    }

    /* Select a survey type based on its SubType Name */
    public SurveyTypes getByID(String id) {
        try {
            String query = "SELECT * FROM survey_types WHERE survey_type_id = ?";
            Long subtypeid = Long.parseLong(id);
            return this.jdbcTemplate.queryForObject( query, surveyTypesRowMapper, subtypeid);
        } catch (Exception e) { return new SurveyTypes(); }
    }


    /* CREATORS */


    /* Create a survey type */
    public void create(String subtypename, String typename) {
        String query = "INSERT INTO survey_types (subtype_name, type_name) VALUES (?,?)";
        this.jdbcTemplate.update(query, subtypename, typename);
    }


    /* REMOVALS */

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


    /* HELPERS */

    /* Check if a Survey Type exists in the database */
    public List<SurveyTypes> surveyTypeExist(String subtype_name) {
        return this.jdbcTemplate.query( "SELECT * FROM survey_types WHERE ? IN (subtype_name, type_name)",
                surveyTypesRowMapper, subtype_name);
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
