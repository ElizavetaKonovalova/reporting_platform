package application.repositories;

import application.models.DemoData;
import application.models.Participants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Repository
public class DemoDataRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public DemoDataRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private FieldRegistryRepository fieldRegistryRepository = new FieldRegistryRepository(jdbcTemplate);

    @Autowired
    private ParticipantRepository participantRepository = new ParticipantRepository(jdbcTemplate);

    @Autowired
    private CohortRepository cohortRepository = new CohortRepository(jdbcTemplate);
    private SimpleDateFormat sampledate = new SimpleDateFormat("dd/MM/yyyy", new Locale("en-au", "AU"));
    private String date_modified_formated = sampledate.format(new java.util.Date());

    /* CREATORS */

    public String create(String demo_field_id, String response, String participant_id) throws Exception {

        /* Check if a passed Field ID exists in Field Registry table */
        UUID check_field = this.fieldRegistryRepository.checkFieldExists(demo_field_id);

        /* Check if a passed participant exists in the Participants table */
        List<Participants> participants = this.participantRepository.getParticipantByPID(participant_id);
        if(check_field != new UUID(0L,0L) && participants.size() != 0) {

            Short response_short = Short.parseShort(response);

            this.jdbcTemplate.update("INSERT INTO demo_data (date_modified, " +
                    "demo_field_id, response_value, participant_id) VALUES (?,?,?,?)",
                    new Date(sampledate.parse(date_modified_formated).getTime()), check_field, response_short, participant_id);

            return "Created";
        } else { return "There is no such field or participant id";}
    }

    /* REMOVALS*/

    /* Remove a Demo Data row by DB ID */
    public void removeDemoDataByDBID(String db_id) {
        Long db_long = Long.parseLong(db_id);
        this.jdbcTemplate.update("DELETE FROM demo_data WHERE db_id = ?", db_id);
    }

    /* Remove a Demo Data row by Field ID */
    public void removeDemoDataByFieldID(String field_id) {
        UUID field_uuid = UUID.fromString(field_id);
        this.jdbcTemplate.update("DELETE FROM demo_data WHERE demo_field_id = ?", field_uuid);
    }

    /* Remove a Demo Data row by Field ID */
    public void removeDemoDataByParticipantID(String participant_id) {
        Long participant_long = Long.parseLong(participant_id);
        this.jdbcTemplate.update("DELETE FROM demo_data WHERE participant_id = ?", participant_long);
    }

    /* Remove a Demo Data row by Date Modified */
    public void removeDemoDataByDateModified(String date_modified) throws Exception {
        String date_modified_form = sampledate.format(date_modified);
        this.jdbcTemplate.update("DELETE FROM demo_data WHERE date_modified = ?",
                new Date(sampledate.parse(date_modified_form).getTime()));
    }



    /* GETTERS */

    /* Get Demo Data by a Participant ID */
    public List<DemoData> getDataByParticipantID(String participant_id) {
        Long participant_id_long = Long.parseLong(participant_id);
        return this.jdbcTemplate.query("SELECT * FROM demo_data WHERE participant_id = ?",
                demoDataRowMapper, participant_id_long);
    }

    /* Get Demo Data by a Field ID */
    public List<DemoData> getDataByFieldID(String field_id) {
        UUID field_uuid = UUID.fromString(field_id);
        return this.jdbcTemplate.query("SELECT * FROM demo_data WHERE demo_field_id = ?",
                demoDataRowMapper, field_uuid);
    }

    /* Get Demo Data by a DB ID */
    public List<DemoData> getDataByDBID(String db_id) {
        Long db_id_long = Long.parseLong(db_id);
        return this.jdbcTemplate.query("SELECT * FROM demo_data WHERE db_id = ?",
                demoDataRowMapper, db_id_long);
    }

    /* Get Demo Data by a Date it was Modified */
    public List<DemoData> getDataByDateModified(String date_modified) throws Exception {

        /* Format date */
        String date_modified_form = sampledate.format(date_modified);

        return this.jdbcTemplate.query("SELECT * FROM demo_data WHERE date_modified = ?",
                demoDataRowMapper, new Date(sampledate.parse(date_modified_form).getTime()));
    }

    /* Get Demo Data by a Cohort ID */
    public List<DemoData> getDataByCohortID(String cohort_id) {
        Long cohort_id_long = Long.parseLong(cohort_id);
        return this.jdbcTemplate.query("SELECT * FROM demo_data WHERE demo_cohort_id = ?",
                demoDataRowMapper, cohort_id_long);
    }

    /* Get Demo Data by a Response Value */
    public List<DemoData> getDataByResponseValue(String response) {
        Short response_short = Short.parseShort(response);
        return this.jdbcTemplate.query("SELECT * FROM demo_data WHERE response_value = ?",
                demoDataRowMapper, response_short);
    }

    /* Get Demo Data by a Response Value and Field ID */
    public List<DemoData> getDataByResponseValueFieldID(String response, String field_id) {
        Short response_short = Short.parseShort(response);
        UUID field_uuid = UUID.fromString(field_id);
        return this.jdbcTemplate.query("SELECT * FROM demo_data WHERE response_value = ? AND demo_field_id = ?",
                demoDataRowMapper, response_short, field_uuid);
    }

    /* Get Demo Data by a Participant ID and Field ID */
    public List<DemoData> getDataByParticipantIDFieldID(String participant_id, String field_id) {
        Long participant_long = Long.parseLong(participant_id);
        UUID field_uuid = UUID.fromString(field_id);
        return this.jdbcTemplate.query("SELECT * FROM demo_data WHERE participant_id = ? AND demo_field_id = ?",
                demoDataRowMapper, participant_long, field_uuid);
    }


    /* UPDATERS */

    /* Update a Cohort ID by Participant ID and Field UUID */
    public String updateCohortID(String cohort_name, String participant_id, String field_id) throws Exception {

        /* Get response by Participant ID and Field UUID */
        DemoData demoData = checkParticipantFieldResponse(participant_id, field_id);

        /* Check if a specified cohort exists in the Cohort table */
        Long check_cohort = this.cohortRepository.checkCohort(cohort_name);
        if(check_cohort != 0 && demoData != null) {

            this.jdbcTemplate.update("UPDATE demo_data SET demo_cohort_id = ?, date_modified = ? " +
                            "WHERE participant_id = ? AND demo_field_id = ?",
                    check_cohort, new Date(sampledate.parse(date_modified_formated).getTime()),
                    demoData.getPARTICIPANT_ID(), demoData.getFIELD_ID());
            return "Updated";
        } else { return "There is no such cohort"; }
    }

    /* Update a Response Value by a Participant ID and a Field ID */
    public String updateResponseValue(String participant_id, String respose_value, String field_id) throws Exception {

        /* Check if there is a response for this participant and this field id */
        DemoData demoData = checkParticipantFieldResponse(participant_id, field_id);

        if(demoData != null) {
            Short response_short = Short.parseShort(respose_value);
            this.jdbcTemplate.update("UPDATE demo_data SET response_value = ?, date_modified = ? WHERE participant_id = ? AND demo_field_id = ?",
                    response_short, new Date(sampledate.parse(date_modified_formated).getTime()),
                    demoData.getPARTICIPANT_ID(), demoData.getFIELD_ID());
            return "Updated";
        } else { return "There is no responses stored for this participant id and field id"; }
    }


    /* NULLERS */

    /* Set a Cohort ID to Null by Participant and Field IDs */
    public void nullCohortID(String participant_id, String field_id) throws Exception {
        DemoData demoData = checkParticipantFieldResponse(participant_id, field_id);

        if(demoData != null) {
            this.jdbcTemplate.update("UPDATE demo_data SET demo_cohort_id = NULL, date_modified = ? WHERE participant_id = ? AND demo_field_id = ?",
                    new Date(sampledate.parse(date_modified_formated).getTime()), demoData.getPARTICIPANT_ID(), demoData.getFIELD_ID());
        }
    }


    /* HELPERS*/

    private DemoData checkParticipantFieldResponse(String participant_id, String field_id) {
        Long participant_id_long = Long.parseLong(participant_id);
        UUID field_uuid = UUID.fromString(field_id);
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM demo_data WHERE participant_id = ? AND demo_field_id = ?",
                    demoDataRowMapper, participant_id_long, field_uuid);
        } catch (Exception e) { return new DemoData(); }
    }


    /* Map data from SQL to the DemoData model */
    private static final RowMapper<DemoData> demoDataRowMapper = new RowMapper<DemoData>() {
        @Override
        public DemoData mapRow(ResultSet rs, int rowNum) throws SQLException {
            DemoData demoData = new DemoData();
            demoData.setDB_ID(rs.getLong("db_id"));
            demoData.setDATE_MODIFIED(rs.getDate("date_modified"));
            demoData.setDEMO_COHORT_ID(rs.getLong("demo_cohort_id"));
            demoData.setFIELD_ID((UUID) rs.getObject("demo_field_id"));
            demoData.setRESPONSE_VALUE(rs.getShort("response_value"));
            demoData.setPARTICIPANT_ID(rs.getLong("participant_id"));
            return demoData;
        }
    };
}
