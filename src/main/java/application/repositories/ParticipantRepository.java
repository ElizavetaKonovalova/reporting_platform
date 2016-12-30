package application.repositories;

import application.models.Participants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@Repository
public class ParticipantRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ParticipantRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JobRepository jobRepository = new JobRepository(jdbcTemplate);

    @Autowired
    private JobStructuralMapRepository jobStructuralMapRepository = new JobStructuralMapRepository(jdbcTemplate);
    private SimpleDateFormat sampledate = new SimpleDateFormat("dd/MM/yyyy", new Locale("en-au", "AU"));
    private String date_modified_formated = sampledate.format(new java.util.Date());


    /* GETTERS */

    /* Get a Participant by Participant ID */
    public List<Participants> getParticipantByPID(String participant_id) {
        Long participant_id_long = Long.parseLong(participant_id);
        return this.jdbcTemplate.query("SELECT * FROM participants WHERE participant_id = ?",
                participantsRowMapper, participant_id_long);
    }


    /* REMOVALS */


    /* NULLERS */

    /* Set a Participant Email to Null by their Participant ID */
    public void nullParticipantEmailByID(String participant_id) throws Exception {
        this.jdbcTemplate.update("UPDATE participants SET participant_email = NULL WHERE participant_id = ?",
                new Date(sampledate.parse(date_modified_formated).getTime()), Long.parseLong(participant_id));
    }

    /* Set a Participant Name to Null by their Participant ID */
    public void nullParticipantNameByID(String participant_id) throws Exception {
        this.jdbcTemplate.update("UPDATE participants SET participant_name = NULL, date_modified = ? WHERE participant_id = ?",
                new Date(sampledate.parse(date_modified_formated).getTime()), Long.parseLong(participant_id));
    }

    /* Set a Participant Name to Null by their Participant Email */
    public void nullParticipantNameByEmail(String participant_email) throws Exception {
        this.jdbcTemplate.update("UPDATE participants SET participant_name = NULL, date_modified = ? WHERE participant_id = ?",
                new Date(sampledate.parse(date_modified_formated).getTime()), participant_email);
    }

    /* Set a Participant Password to Null by their Participant ID */
    public void nullPasswordByID(String participant_id) throws Exception {
        this.jdbcTemplate.update("UPDATE participants SET password = NULL, date_modified = ? WHERE participant_id = ?",
                new Date(sampledate.parse(date_modified_formated).getTime()), Long.parseLong(participant_id));
    }

    /* Set a Password to Null by their Participant Email */
    public void nullPasswordByEmail(String participant_email) throws Exception {
        this.jdbcTemplate.update("UPDATE participants SET password = NULL, date_modified = ? WHERE participant_email = ?",
                new Date(sampledate.parse(date_modified_formated).getTime()), participant_email);
    }




    /* UPDATERS */

    /* Update a Participant Name by their Participant ID */
    public void updateParticipantNameByID(String participant_id, String new_participant_name) throws Exception {
        this.jdbcTemplate.update("UPDATE participants SET participant_name = ?, date_modified = ? WHERE participant_id = ?",
                new_participant_name, new Date(sampledate.parse(date_modified_formated).getTime()), Long.parseLong(participant_id));
    }

    /* Update a Participant Name by their Participant Email */
    public void updateParticipantNameByEmail(String participant_email, String new_participant_name) throws Exception {
        this.jdbcTemplate.update("UPDATE participants SET participant_name = ?, date_modified = ? WHERE participant_email = ?",
                new_participant_name, new Date(sampledate.parse(date_modified_formated).getTime()), participant_email);
    }

    /* Update a Participant Email by their Participant ID */
    public void updateParticipantEmailByID(String participant_id, String new_participant_email) throws Exception {
        this.jdbcTemplate.update("UPDATE participants SET participant_email = ?, date_modified = ? WHERE participant_id = ?",
                new_participant_email, new Date(sampledate.parse(date_modified_formated).getTime()), Long.parseLong(participant_id));
    }

    /* Update a Participant Email by their old Participant Email */
    public void updateParticipantEmailByEmail(String old_participant_email, String new_participant_email) throws Exception {
        this.jdbcTemplate.update("UPDATE participants SET participant_email = ?, date_modified = ? WHERE participant_email = ?",
                new_participant_email, new Date(sampledate.parse(date_modified_formated).getTime()), old_participant_email);
    }

    /* Update a Participant Password by their Participant Email */
    public void updateParticipantPasswordByEmail(String participant_email, String password) throws Exception {
        this.jdbcTemplate.update("UPDATE participants SET password = ?, date_modified = ? WHERE participant_email = ?",
                this.passwordEncoder.encode(password), new Date(sampledate.parse(date_modified_formated).getTime()), participant_email);
    }

    /* Update a Participant Password by their Participant ID */
    public void updateParticipantPasswordByID(String participant_id, String password) throws Exception {
        this.jdbcTemplate.update("UPDATE participants SET password = ?, date_modified = ? WHERE participant_id = ?",
                password, new Date(sampledate.parse(date_modified_formated).getTime()), Long.parseLong(participant_id));
    }

    /* Update a Participant Password by their Participant Password */
    public void updateParticipantPasswordByPassword(String old_password, String new_password) throws Exception {
        this.jdbcTemplate.update("UPDATE participants SET password = ?, date_modified = ? WHERE participant_id = ?",
                new_password, new Date(sampledate.parse(date_modified_formated).getTime()), Long.parseLong(old_password));
    }



    /* CREATORS */

    public String create(String participant_email, String participant_name, String job_code,
                         String password, String status, String wu_code) throws Exception {

        /* Check if a participant is already in the database */
        Long check_participant = this.checkParticipantIDExists(participant_email);
        Long check_job = this.jobRepository.isJobExists(job_code);

        if(check_participant == 0 && check_job != 0 ) {

            Long check_wu = this.jobStructuralMapRepository.isWUExists(check_job, wu_code);

            if(check_wu != 0L) {

                /* If a status is null set it to the default value */
                status = ((status == null) ? "Not accessed" : status);

                /* Encode a password if it's not null */
                password = ((password != null) ? passwordEncoder.encode(password) : null);

                this.jdbcTemplate.update("INSERT INTO participants (date_modified, job_id, participant_email, participant_name, " +
                                "password, status, wu_code) VALUES (?,?,?,?,?,?,?)", new Date(sampledate.parse(date_modified_formated)
                                .getTime()), check_job, participant_email, participant_name, password, status, check_wu);
                return "Created";
            } else { return "This work unit does not belong to this job"; }
        } else { return "Either this participant is already in db, or this job does not exist!"; }
    }





    /* HELPERS */

    /* Check if current participant exists in the database */
    public Long checkParticipantIDExists(String participant_email) {
        try {
            return this.jdbcTemplate.queryForObject(
                    "SELECT * FROM participants WHERE participant_email = ?",
                    participantsRowMapper, participant_email).getPARTICIPANT_ID();
        } catch (Exception e) { return 0L; }
    }

    /* Map data from the database to the Participants model */
    private static final RowMapper<Participants> participantsRowMapper = new RowMapper<Participants>() {
        public Participants mapRow(ResultSet rs, int rowNum) throws SQLException {
            Participants participants = new Participants();
            participants.setPARTICIPANT_ID(rs.getLong("participant_id"));
            participants.setDATE_MODIFIED(rs.getDate("date_modified"));
            participants.setPARTICIPANT_EMAIL(rs.getString("participant_email"));
            participants.setPARTICIPANT_NAME(rs.getString("participant_name"));
            participants.setPASSWORD(rs.getString("password"));
            participants.setSTATUS(rs.getString("status"));
            participants.setJOB_ID(rs.getLong("job_id"));
            participants.setWU_CODE(rs.getLong("wu_code"));
            return participants;
        }
    };
}
