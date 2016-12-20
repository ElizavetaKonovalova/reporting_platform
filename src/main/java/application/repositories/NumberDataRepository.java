package application.repositories;

import application.models.NumberData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class NumberDataRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SimpleDateFormat sampledate = new SimpleDateFormat("dd/MM/yyyy", new Locale("en-au", "AU"));
    private final ParticipantRepository participantRepository = new ParticipantRepository();
    private final TextDataRepository textDataRepository = new TextDataRepository();


    /* CREATORS */


    /* Create a new Number Data row */
    public String create(String field_name, String participant_email, String positivity_result, String response_value) throws Exception {
        UUID number_field_uuid = checkFieldExists(field_name);
        Long participant_id_int = checkParticipantExists(participant_email);
        Short response_value_short = Short.parseShort(response_value);

        if(participant_id_int != 0 && number_field_uuid.toString() != "0" ) {
            if(positivity_result.contains("positive") || positivity_result.contains("middle")
                    || positivity_result.contains("negative")) {
                sampledate.setTimeZone(TimeZone.getTimeZone("AEST"));
                String date_modified_formated = sampledate.format(new java.util.Date());
                String query = "INSERT INTO number_data (date_modified, number_field_id, participant_id, positivity_result, response_value) VALUES (?,?,?,?,?)";
                this.jdbcTemplate.update(query, new Date(sampledate.parse(date_modified_formated).getTime()), number_field_uuid, participant_id_int,
                        positivity_result.charAt(0), response_value_short);
                return "Created";
            } else { return "Please select Positive, Middle, or Negative"; }
        } else { return "This participant or field does not exist!"; }
    }


    /* GETTERS */

    /* Find results by their Participant IDs */
    public List<NumberData> getNumberDataByParticipantID(String participant_id) {
        try {
            Long participant_id_long = Long.parseLong(participant_id);
            String query = "SELECT * FROM number_data INNER JOIN participants ON " +
                    "number_data.participant_id = participants.participant_id WHERE participant_id = ?";
            return this.jdbcTemplate.query(query, numberDataRowMapper, participant_id_long);
        } catch (Exception e) { return new ArrayList<>(); }
    }

    /* Find results by their Field IDs */
    public List<NumberData> getNumberDataByFieldID(String field_id) {
        UUID field_uuid = UUID.fromString(field_id);
        String query = "SELECT * FROM number_data WHERE number_field_id = ?";
        return this.jdbcTemplate.query(query, numberDataRowMapper, field_uuid);
    }

    /* Find results by a Date they were Modified */
    public List<NumberData> getNumberDataByDate(String date_modified) throws Exception {
        sampledate.setTimeZone(TimeZone.getTimeZone("AEST"));
        String date_formated = sampledate.format(date_modified);
        String query = "SELECT * FROM number_data WHERE date_modified = ?";
        return this.jdbcTemplate.query(query, numberDataRowMapper, new Date(sampledate.parse(date_formated).getTime()));
    }

    /* Find results by their Positivity */
    public List<NumberData> getNumberDataByPositivity(String positivity) {
        String query = "SELECT * FROM number_data WHERE positivity_result = ?";
        return this.jdbcTemplate.query(query, numberDataRowMapper, positivity.charAt(0));
    }

    /* Find results by their Response Values */
    public List<NumberData> getNumberDataByResponseValue(String response_value) {
        Short response_short = Short.parseShort(response_value);
        String query = "SELECT * FROM number_data WHERE response_value = ?";
        return this.jdbcTemplate.query(query, numberDataRowMapper, response_short);
    }

    /* Find results by their Participant Emails */
    public List<NumberData> getNumberDataByParticipantEmail(String participant_email) {
        Long participant_id = this.textDataRepository.isParticipantIDExist(participant_email);
            String query = "SELECT * FROM number_data INNER JOIN participants ON " +
                    "number_data.participant_id = participants.participant_id WHERE participant_email = ?";
            return this.jdbcTemplate.query(query, numberDataRowMapper, participant_email);
    }

    /* Find results by their Field Name */
    public List<NumberData> getNumberDataByFieldName(String field_name) {
        try {
            String query = "SELECT * FROM number_data INNER JOIN field_registry ON " +
                    "number_data.number_field_id = field_registry.field_id WHERE field_name = ?";
            return this.jdbcTemplate.query(query, numberDataRowMapper, field_name);
        } catch (Exception e) { return new ArrayList<>(); }
    }



    /* REMOVALS */


    /* HELPERS */

    /* Check if current participant exists in the database */
    private Long checkParticipantExists(String participant_email) {
        try {
            String query = "SELECT * FROM participants WHERE participant_email = ?";
            return this.jdbcTemplate.queryForObject(query, this.participantRepository.participantsRowMapper,
                    participant_email).getPARTICIPANT_ID();
        } catch (Exception e) { return 0L; }
    }

    /* Check if a field with a passed field name exists in the database */
    private UUID checkFieldExists(String field_name) {
        try {
            String query = "SELECT * FROM field_registry WHERE field_name = ?";
            return this.jdbcTemplate.queryForObject(query, FieldRegistryRepository.fieldRegistryRowMapper,
                    field_name).getFIELD_ID();
        } catch (Exception e) { return new UUID(0L, 0L); }
    }

    /* Map data from the database to the NumberData model */
    private static final RowMapper<NumberData> numberDataRowMapper = new RowMapper<NumberData>() {
        public NumberData mapRow(ResultSet rs, int rowNum) throws SQLException {
            NumberData numberData = new NumberData();
            numberData.setDB_ID(rs.getLong("db_id"));
            numberData.setDATE_MODIFIED(rs.getDate("date_modified"));
            numberData.setNUMBER_FIELD_ID((UUID) rs.getObject("number_field_id"));
            numberData.setPARTICIPANT_ID(rs.getLong("participant_id"));
            numberData.setPOSITIVITY_RESULT(rs.getString("positivity_result").charAt(0));
            numberData.setRESPONSE_VALUE(rs.getShort("response_value"));
            return numberData;
        }
    };
}
