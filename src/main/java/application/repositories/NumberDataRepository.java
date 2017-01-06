package application.repositories;

import application.models.NumberData;
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
import java.util.*;

@Repository
public class NumberDataRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public NumberDataRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private SimpleDateFormat sampledate = new SimpleDateFormat("dd/MM/yyyy", new Locale("en-au", "AU"));

    @Autowired
    private final ParticipantRepository participantRepository = new ParticipantRepository(jdbcTemplate);

    @Autowired
    private final FieldRegistryRepository fieldRegistryRepository = new FieldRegistryRepository(jdbcTemplate);


    /* CREATORS */


    /* Create a new Number Data row */
    public String create(String field_name, String participant_email, String positivity_result, String response_value) throws Exception {
        UUID number_field_uuid = this.fieldRegistryRepository.checkFieldExists(field_name);
        List<Participants> participant = this.participantRepository.getParticipantByEmail(participant_email);
        Short response_value_short = Short.parseShort(response_value);

        if(participant.size() != 0 && number_field_uuid != new UUID(0L,0L) ) {
            if(positivity_result.contains("positive") || positivity_result.contains("middle")
                    || positivity_result.contains("negative")) {
                String date_modified_formated = sampledate.format(new java.util.Date());
                String query = "INSERT INTO number_data (date_modified, number_field_id, participant_id, positivity_result, response_value) VALUES (?,?,?,?,?)";
                this.jdbcTemplate.update(query, new Date(sampledate.parse(date_modified_formated).getTime()), number_field_uuid,
                        participant.get(0).getPARTICIPANT_ID(), positivity_result.charAt(0), response_value_short);
                return "Created";
            } else { return "Please select Positive, Middle, or Negative"; }
        } else { return "This participant or field does not exist!"; }
    }


    /* GETTERS */

    /* Find results by their Participant IDs */
    public List<NumberData> getNumberDataByParticipantID(String participant_id) {
        try {
            String query = "SELECT * FROM number_data INNER JOIN participants ON " +
                    "number_data.participant_id = participants.participant_id WHERE participant_id = ?";
            return this.jdbcTemplate.query(query, numberDataRowMapper, Long.parseLong(participant_id));
        } catch (Exception e) { return new ArrayList<>(); }
    }

    /* Find results by their Field IDs */
    public List<NumberData> getNumberDataByFieldID(String field_id) {
        String query = "SELECT * FROM number_data WHERE number_field_id = ?";
        return this.jdbcTemplate.query(query, numberDataRowMapper, UUID.fromString(field_id));
    }

    /* Find results by a Date they were Modified */
    public List<NumberData> getNumberDataByDate(String date_modified) throws Exception {
        return this.jdbcTemplate.query("SELECT * FROM number_data WHERE date_modified = ?",
                numberDataRowMapper, new Date(sampledate.parse(sampledate.format(date_modified)).getTime()));
    }

    /* Find results by their Positivity */
    public List<NumberData> getNumberDataByPositivity(String positivity) {
        return this.jdbcTemplate.query("SELECT * FROM number_data WHERE positivity_result = ?",
                numberDataRowMapper, positivity.charAt(0));
    }

    /* Find results by their Response Values */
    public List<NumberData> getNumberDataByResponseValue(String response_value) {
        return this.jdbcTemplate.query("SELECT * FROM number_data WHERE response_value = ?", numberDataRowMapper, Short.parseShort(response_value));
    }

    /* Find results by their Participant Emails */
    public List<NumberData> getNumberDataByParticipantEmail(String participant_email) {
            return this.jdbcTemplate.query("SELECT * FROM number_data INNER JOIN participants ON " +
                    "number_data.participant_id = participants.participant_id WHERE participant_email = ?", numberDataRowMapper, participant_email);
    }

    /* Find results by their Field Name */
    public List<NumberData> getNumberDataByFieldName(String field_name) {
        return this.jdbcTemplate.query("SELECT * FROM number_data INNER JOIN field_registry ON " +
                    "number_data.number_field_id = field_registry.field_id WHERE field_name = ?", numberDataRowMapper, field_name);
    }



    /* REMOVALS */


    /* HELPERS */

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
