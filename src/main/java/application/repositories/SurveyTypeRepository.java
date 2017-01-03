package application.repositories;

import application.models.SurveyTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@Repository("surveyTypes")
public class SurveyTypeRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SurveyTypeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new JdbcTemplate();
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private SimpleDateFormat sampledate = new SimpleDateFormat("dd/MM/yyyy", new Locale("en-au", "AU"));
    private String date_modified_formated = sampledate.format(new java.util.Date());

    /* CREATORS */


    /* Create a Survey Type */
    public String create(String type_name, String sub_type_name) throws Exception {

        if(this.checkIfCombinationSubTypeExists(type_name,sub_type_name)) {
            return "Combination of subtype and type already in the database";
        } else {
            this.jdbcTemplate.update("INSERT INTO survey_types (subtype_name, type_name,date_modified) VALUES (?,?,?)",
                    sub_type_name, type_name, new Date(sampledate.parse(date_modified_formated).getTime()));
            return "Created";
        }
    }


    /* GETTERS */


    /* Select a survey type based on its Type Name [a.k.a Workforce, Partner] */
    public List<SurveyTypes> getByTypeName(String typename) {
        return this.jdbcTemplate.query("SELECT * FROM survey_types WHERE type_name = ?",
                    surveyTypesRowMapper, typename);
    }

    /* Select a survey type based on its SubType Name [a.k.a Employees, Volunteers, Ambulatory ] */
    public List<SurveyTypes> getBySubTypeName(String subtypename) {
        return this.jdbcTemplate.query("SELECT * FROM survey_types WHERE subtype_name = ?",
                    surveyTypesRowMapper, subtypename);
    }

    /* Select a survey type based on its SubType Name */
    public List<SurveyTypes> getByID(String id) {
        return this.jdbcTemplate.query("SELECT * FROM survey_types WHERE surveytype_id = ?",
                    surveyTypesRowMapper, Long.parseLong(id));
    }

    /* Select All Types */
    public List<SurveyTypes> getAll() {
        return this.jdbcTemplate.query("SELECT * FROM ", surveyTypesRowMapper);
    }



    /* UPDATERS */




    /* REMOVALS */

    /* Remove a survey type by its ID */
    public void removeSubTypeByID(String id) {
        this.jdbcTemplate.update("DELETE FROM survey_types WHERE surveytype_id = ?",
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

    /* Check if a combination of Subtype and Type is already in the database */
    private Boolean checkIfCombinationSubTypeExists(String survey_type, String sub_type) {
        return (this.jdbcTemplate.query("SELECT * FROM survey_types WHERE type_name = ? AND subtype_name = ?",
                surveyTypesRowMapper, survey_type, sub_type).size() != 0) ? true: false;
    }

    /* Map data from the database to the SurveyTypes model */
    private static final RowMapper<SurveyTypes> surveyTypesRowMapper = new RowMapper<SurveyTypes>() {
        public SurveyTypes mapRow(ResultSet rs, int rowNum) throws SQLException {
            SurveyTypes surveyTypes = new SurveyTypes();
            surveyTypes.setSURVEYTYPE_ID(rs.getLong("survey_type_id"));
            surveyTypes.setSUBTYPE_NAME(rs.getString("subtype_name"));
            surveyTypes.setTYPE_NAME(rs.getString("type_name"));
            surveyTypes.setDATE_MODIFIED(rs.getDate("date_modified"));
            return surveyTypes;
        }
    };
}
