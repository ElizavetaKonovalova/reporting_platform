package application.repositories;

import application.models.FieldRegistry;
import application.models.Participants;
import application.models.TextData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

@Repository
public class TextDataRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private FieldRegistry fieldRegistry = new FieldRegistry();
    private Participants participants = new Participants();
    private SimpleDateFormat sampledate = new SimpleDateFormat("dd/MM/yyyy", new Locale("en-au", "AU"));

    public TextData getDataByFieldName(String field_name) {
        UUID field_uuid = isTextFieldUUIDExist(field_name);
        try {
            if(field_uuid.node() != 0L) {
                String info_query = "SELECT * FROM text_data INNER JOIN field_registry ON " +
                        "text_data.text_field_id = field_registry.field_id WHERE text_data.text_field_id = ?";
                return this.jdbcTemplate.queryForObject(info_query, textDataRowMapper, field_uuid);
            } else { return new TextData(); }
        } catch (Exception e) { return new TextData(); }
    }

    public String createTextData(String red_flag_status, String response_value, String shadow_status,
                                 String text_field_name, String participant_email) throws Exception {

        sampledate.setTimeZone(TimeZone.getTimeZone("AEST"));
        UUID text_field_id = isTextFieldUUIDExist(text_field_name);
        Long participants_id = isParticipantIDExist(participant_email);
        Boolean response_exists = isResponseAlreadyInDB(participants_id, text_field_id);

        String query = "INSERT INTO text_data (date_modified, redflag_status, response_value, shadow_status, text_field_id, participant_id)" +
                " VALUES (?,?,?,?,?,?)";

        String date_modified_formated = sampledate.format(new java.util.Date());

        if(!text_field_id.toString().isEmpty() ) {
            if(participants_id != 0L) {
                if(!response_exists) {

                    /* Create a new Text Data field */
                    this.jdbcTemplate.update(query, new Date(sampledate.parse(date_modified_formated).getTime()), red_flag_status,
                            response_value, shadow_status, text_field_id, participants_id);
                    return "Created";
                } else {

                    /* If a response already in the database, update the fields */
                    String update_query = "UPDATE text_data SET date_modified = ?, redflag_status = ?, response_value = ?, " +
                            "shadow_status = ? WHERE participant_id = ?";
                    this.jdbcTemplate.update(update_query, new Date(sampledate.parse(date_modified_formated).getTime()), red_flag_status, response_value,
                            shadow_status, participants_id);
                    return "Updated the response";
                }
            } else { return "There is no such participant"; }
        } else { return "There is no such field"; }
    }

    /* Check if a response for a participant for a field is already in the database */
    private Boolean isResponseAlreadyInDB(Long participant_id, UUID text_filed) {
        try {
            if(participant_id != 0L && !text_filed.toString().equals("0") ) {
                String query = "SELECT * FROM text_data WHERE participant_id = ? AND text_field_id = ?";
                TextData textData = this.jdbcTemplate.queryForObject(query, textDataRowMapper, participant_id, text_filed);
                if(textData.getDB_ID().toString().isEmpty()) { return false; } else { return true; }
            } else { return false; }
        } catch (Exception e) { return false; }
    }

    /* Check if parsed field name exist in the FieldRegisrty table */
    private UUID isTextFieldUUIDExist(String text_field_name) {
        try {
            String query = "SELECT * FROM field_registry WHERE field_name = ?";
            this.fieldRegistry = this.jdbcTemplate.queryForObject(query, FieldRegistryRepository.fieldRegistryRowMapper, text_field_name);
            return fieldRegistry.getFIELD_ID();
        } catch (Exception e) { return new UUID(0L,0L);}
    }

    /* Check if a participant with a parsed email address exists in the Participants database */
    public Long isParticipantIDExist(String participant_email) {
        String query = "SELECT * FROM participants WHERE participant_email = ?";
        try {
            this.participants = this.jdbcTemplate.queryForObject(query, ParticipantRepository.participantsRowMapper, participant_email);
            return this.participants.getPARTICIPANT_ID();
        } catch (Exception e) { return 0L; }
    }

    /* Map data from the database to the TextData model */
    private static final RowMapper<TextData> textDataRowMapper = new RowMapper<TextData>() {
        public TextData mapRow(ResultSet rs, int rowNum) throws SQLException {
            TextData textData = new TextData();
            textData.setFIELD_ID((UUID) rs.getObject("text_field_id"));
            textData.setDATE_MODIFIED(rs.getDate("date_modified"));
            textData.setDB_ID(rs.getLong("db_id"));
            textData.setREDFLAG_STATUS(rs.getString("redflag_status"));
            textData.setSHADOW_STATUS(rs.getString("shadow_status"));
            textData.setRESPONSE_VALUE(rs.getString("response_value"));
            textData.setPARTICIPANT_ID(rs.getLong("participant_id"));
            return textData;
        }
    };

}
