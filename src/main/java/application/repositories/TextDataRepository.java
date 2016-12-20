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
import java.util.*;

@Repository
public class TextDataRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private FieldRegistry fieldRegistry = new FieldRegistry();
    private ParticipantRepository participantRepository = new ParticipantRepository();
    private SimpleDateFormat sampledate = new SimpleDateFormat("dd/MM/yyyy", new Locale("en-au", "AU"));


    /* GETTERS */

    /* Find text response by a Field Name */
    public List<TextData> getDataByFieldName(String field_name) {
        UUID field_uuid = isTextFieldUUIDExist(field_name);
        if(!field_uuid.toString().equals("0")) {
            String info_query = "SELECT * FROM text_data WHERE text_field_id = ?";
            List<TextData> result = this.jdbcTemplate.query(info_query, textDataRowMapper, field_uuid);
            if(!result.isEmpty()) { return result; } else { return new ArrayList<>(); }
        } else { return new ArrayList<>(); }
    }

    /* Find results based on Response Values */
    public List<TextData> getDataByResponseValue(String response_value) {
        String query = "SELECT * FROM text_data WHERE response_value = ?";
        List<TextData> result = this.jdbcTemplate.query(query, textDataRowMapper, response_value);
        if(result.isEmpty()) { return new ArrayList<>(); } else { return result; }
    }

    /* Find results based on Red Flag Statuses */
    public List<TextData> getDataByRedFlagStat(String redflag_stat) {
        String query = "SELECT * FROM text_data WHERE redflag_status = ?";
        List<TextData> result = this.jdbcTemplate.query(query, textDataRowMapper, redflag_stat);
        if(result.isEmpty()) { return new ArrayList<>(); } else { return result; }
    }

    /* Find results based on Shadow Statuses */
    public List<TextData> getDataByShadowStat(String shadow_stat) {
        String query = "SELECT * FROM text_data WHERE shadow_status = ?";
        List<TextData> result = this.jdbcTemplate.query(query, textDataRowMapper, shadow_stat);
        if(result.isEmpty()) { return new ArrayList<>(); } else { return result; }
    }

    /* Find results based on Field ID */
    public List<TextData> getDataByFieldID(String field_id) {
        UUID field_uuid = UUID.fromString(field_id);
        String query = "SELECT * FROM text_data WHERE text_field_id = ?";
        List<TextData> result = this.jdbcTemplate.query(query, textDataRowMapper, field_uuid);
        if(result.isEmpty()) { return new ArrayList<>(); } else { return result; }
    }

    /* Find results based on Participant ID */
    public List<TextData> getDataByParticipantID(String participant_id) {
        Long participant_id_long = Long.parseLong(participant_id);
        String query = "SELECT * FROM text_data WHERE participant_id = ?";
        List<TextData> result = this.jdbcTemplate.query(query, textDataRowMapper, participant_id_long);
        if(result.isEmpty()) { return new ArrayList<>(); } else { return result; }
    }

    /* Find results based on Date Modified */
    public List<TextData> getDataByDate(String date_modified) throws Exception {
        sampledate.setTimeZone(TimeZone.getTimeZone("AEST"));
        String date_modified_formated = sampledate.format(new java.util.Date());
        String query = "SELECT * FROM text_data WHERE date_modified = ?";
        List<TextData> result = this.jdbcTemplate.query(query, textDataRowMapper, new Date(sampledate.parse(date_modified_formated).getTime()));
        if(result.isEmpty()) { return new ArrayList<>(); } else { return result; }
    }

    /* Find results based on its database ID */
    public List<TextData> getDataByDBID(String db_id) {
        Long db_id_long = Long.parseLong(db_id);
        String query = "SELECT * FROM text_data WHERE db_id = ?";
        List<TextData> result = this.jdbcTemplate.query(query, textDataRowMapper, db_id_long);
        if(result.isEmpty()) { return new ArrayList<>(); } else { return result; }
    }

    /* Find results based on Participant Email */
    public List<TextData> getDataByParticipantEmail(String participant_email) {
        Long participant_id = this.participantRepository.isParticipantIDExist(participant_email);
        if(participant_id != 0) {
            String query = "SELECT * FROM text_data WHERE participant_id = ?";
            List<TextData> result = this.jdbcTemplate.query(query, textDataRowMapper, participant_id);
            if(result.isEmpty()) { return new ArrayList<>(); } else { return result; }
        } else { return new ArrayList<>(); }
    }


    /* CREATORS */

    public String createTextData(String red_flag_status, String response_value, String shadow_status,
                                 String text_field_name, String participant_email) throws Exception {

        sampledate.setTimeZone(TimeZone.getTimeZone("AEST"));
        UUID text_field_id = isTextFieldUUIDExist(text_field_name);
        Long participants_id = this.participantRepository.isParticipantIDExist(participant_email);
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



    /* REMOVALS */

    /* Removes Text Data by its database ID */
    public void removeTextDataByDBID(String db_id) {
        Long db_id_long = Long.parseLong(db_id);
        String query = "DELETE FROM text_data WHERE db_id = ?";
        this.jdbcTemplate.update(query, db_id_long);
    }

    /* Removes Text Data by its Participant ID */
    public void removeTextDataByPartID(String participant_id) {
        Long participant_id_long = Long.parseLong(participant_id);
        String query = "DELETE FROM text_data WHERE participant_id = ?";
        this.jdbcTemplate.update(query, participant_id_long);
    }

    /* Removes Text Data by its Date of Modification */
    public void removeTextDataByDateModif(String date_modified) throws Exception {
        sampledate.setTimeZone(TimeZone.getTimeZone("AEST"));
        String date_modified_formated = sampledate.format(date_modified);
        String query = "DELETE FROM text_data WHERE date_modified = ?";
        this.jdbcTemplate.update(query, new Date(sampledate.parse(date_modified_formated).getTime()));
    }

    /* Removes Text Data by its Red Flag Status */
    public void removeTextDataByRedFlagSt(String redflag_stat) {
        String query = "DELETE FROM text_data WHERE redflag_status = ?";
        this.jdbcTemplate.update(query, redflag_stat);
    }

    /* Removes Text Data by its Response Value */
    public void removeTextDataByResponse(String response_value) {
        String query = "DELETE FROM text_data WHERE response_value = ?";
        this.jdbcTemplate.update(query, response_value);
    }

    /* Removes Text Data by its Shadow Status */
    public void removeTextDataByShadowStat(String shadow_stat) {
        String query = "DELETE FROM text_data WHERE shadow_status = ?";
        this.jdbcTemplate.update(query, shadow_stat);
    }

    /* Removes Text Data by its Field ID */
    public void removeTextDataByFieldID(String field_id) {
        try {
            UUID field_uuid = UUID.fromString(field_id);
            String query = "DELETE FROM text_data WHERE text_field_id = ?";
            this.jdbcTemplate.update(query, field_uuid);
        } catch (Exception e) { return; }
    }

    /* Remove responses for a Participant by Email */
    public String removeTextDataByParticipantEmail(String participant_email) {
        Long participant_id = this.participantRepository.isParticipantIDExist(participant_email);
        if(participant_id != 0) {
            String query = "DELETE FROM text_data WHERE participant_id = ?";
            this.jdbcTemplate.update(query, participant_id);
            return "Deleted";
        } else { return "Could not delete this participant"; }
    }

    /* Remove results based on Field Name */
    public String removeTextDataByFieldName(String field_name) {
        UUID field_uuid = isTextFieldUUIDExist(field_name);
        if(!field_uuid.toString().equals("0")) {
            String query = "DELETE FROM text_data WHERE text_field_id = ?";
            this.jdbcTemplate.update(query, field_uuid);
            return "Deleted";
        } else { return "Could not delete the field"; }
    }


    /* NULLERS */

    /* Set a specific Red Flag Status to Null */
    public void nullRedFlagStatus(String field_id) {
        try {
            UUID field_uuid = UUID.fromString(field_id);
            String query = "UPDATE text_data SET redflag_status = NULL WHERE text_field_id = ?";
            this.jdbcTemplate.update(query, field_uuid);
        } catch (Exception e) { return; }
    }

    /* Set a specific Shadow Status to Null */
    public void nullShadowStatus(String field_id) {
        try {
            UUID field_uuid = UUID.fromString(field_id);
            String query = "UPDATE text_data SET shadow_status = NULL WHERE text_field_id = ?";
            this.jdbcTemplate.update(query, field_uuid);
        } catch (Exception e) { return; }
    }


    /* HELPERS */

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
