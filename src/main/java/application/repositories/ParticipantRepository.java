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

    private JdbcTemplate JDBC_TEMPLATE;

    @Autowired
    public ParticipantRepository(JdbcTemplate JDBC_TEMPLATE) {
        this.JDBC_TEMPLATE = JDBC_TEMPLATE;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.JDBC_TEMPLATE = new JdbcTemplate(dataSource);
    }

    @Autowired
    private PasswordEncoder PASSWORD_ENCODER;

    @Autowired
    private JobRepository JOB = new JobRepository(JDBC_TEMPLATE);

    @Autowired
    private JobStructuralMapRepository JOB_STRUCTURAL_MAP = new JobStructuralMapRepository(JDBC_TEMPLATE);

    /* Date/Time Formatting */
    private SimpleDateFormat SAMPLEDATE = new SimpleDateFormat("dd/MM/yyyy", new Locale("en-au", "AU"));
    private String DATE_NOW_FORMATTED = SAMPLEDATE.format(new java.util.Date());


    /* GETTERS */

    /* Get a Participant by Participant ID */
    public List<Participants> getParticipantByPID(String participant_id) {
        return this.JDBC_TEMPLATE.query("SELECT * FROM participants WHERE participant_id = ?",
                participantsRowMapper, Long.parseLong(participant_id));
    }

    /* Get a Participant by Participant ID */
    public List<Participants> getParticipantByEmail(String participant_email) {
        return this.JDBC_TEMPLATE.query("SELECT * FROM participants WHERE participant_email = ?",
                participantsRowMapper, participant_email);
    }


    /* REMOVALS */


    /* NULLERS */

    /* Set a Participant Email to Null by their Participant ID */
    public void nullParticipantEmailByID(String participant_id) throws Exception {
        this.JDBC_TEMPLATE.update("UPDATE participants SET participant_email = NULL WHERE participant_id = ?",
                new Date(SAMPLEDATE.parse(DATE_NOW_FORMATTED).getTime()), Long.parseLong(participant_id));
    }

    /* Set a Participant Name to Null by their Participant ID */
    public void nullParticipantNameByID(String participant_id) throws Exception {
        this.JDBC_TEMPLATE.update("UPDATE participants SET participant_name = NULL, date_modified = ? WHERE participant_id = ?",
                new Date(SAMPLEDATE.parse(DATE_NOW_FORMATTED).getTime()), Long.parseLong(participant_id));
    }

    /* Set a Participant Name to Null by their Participant Email */
    public void nullParticipantNameByEmail(String participant_email) throws Exception {
        this.JDBC_TEMPLATE.update("UPDATE participants SET participant_name = NULL, date_modified = ? WHERE participant_id = ?",
                new Date(SAMPLEDATE.parse(DATE_NOW_FORMATTED).getTime()), participant_email);
    }

    /* Set a Participant Password to Null by their Participant ID */
    public void nullPasswordByID(String participant_id) throws Exception {
        this.JDBC_TEMPLATE.update("UPDATE participants SET password = NULL, date_modified = ? WHERE participant_id = ?",
                new Date(SAMPLEDATE.parse(DATE_NOW_FORMATTED).getTime()), Long.parseLong(participant_id));
    }

    /* Set a Password to Null by their Participant Email */
    public void nullPasswordByEmail(String participant_email) throws Exception {
        this.JDBC_TEMPLATE.update("UPDATE participants SET password = NULL, date_modified = ? WHERE participant_email = ?",
                new Date(SAMPLEDATE.parse(DATE_NOW_FORMATTED).getTime()), participant_email);
    }




    /* UPDATERS */

    /* Update a Participant Name by their Participant ID */
    public void updateParticipantNameByID(String participant_id, String new_participant_name) throws Exception {
        this.JDBC_TEMPLATE.update("UPDATE participants SET participant_name = ?, date_modified = ? WHERE participant_id = ?",
                new_participant_name, new Date(SAMPLEDATE.parse(DATE_NOW_FORMATTED).getTime()), Long.parseLong(participant_id));
    }

    /* Update a Participant Name by their Participant Email */
    public void updateParticipantNameByEmail(String participant_email, String new_participant_name) throws Exception {
        this.JDBC_TEMPLATE.update("UPDATE participants SET participant_name = ?, date_modified = ? WHERE participant_email = ?",
                new_participant_name, new Date(SAMPLEDATE.parse(DATE_NOW_FORMATTED).getTime()), participant_email);
    }

    /* Update a Participant Email by their Participant ID */
    public void updateParticipantEmailByID(String participant_id, String new_participant_email) throws Exception {
        this.JDBC_TEMPLATE.update("UPDATE participants SET participant_email = ?, date_modified = ? WHERE participant_id = ?",
                new_participant_email, new Date(SAMPLEDATE.parse(DATE_NOW_FORMATTED).getTime()), Long.parseLong(participant_id));
    }

    /* Update a Participant Email by their old Participant Email */
    public void updateParticipantEmailByEmail(String old_participant_email, String new_participant_email) throws Exception {
        this.JDBC_TEMPLATE.update("UPDATE participants SET participant_email = ?, date_modified = ? WHERE participant_email = ?",
                new_participant_email, new Date(SAMPLEDATE.parse(DATE_NOW_FORMATTED).getTime()), old_participant_email);
    }

    /* Update a Participant Password by their Participant Email */
    public void updateParticipantPasswordByEmail(String participant_email, String password) throws Exception {
        this.JDBC_TEMPLATE.update("UPDATE participants SET password = ?, date_modified = ? WHERE participant_email = ?",
                this.PASSWORD_ENCODER.encode(password), new Date(SAMPLEDATE.parse(DATE_NOW_FORMATTED).getTime()), participant_email);
    }

    /* Update a Participant Password by their Participant ID */
    public void updateParticipantPasswordByID(String participant_id, String password) throws Exception {
        this.JDBC_TEMPLATE.update("UPDATE participants SET password = ?, date_modified = ? WHERE participant_id = ?",
                password, new Date(SAMPLEDATE.parse(DATE_NOW_FORMATTED).getTime()), Long.parseLong(participant_id));
    }

    /* Update a Participant Password by their Participant Password */
    public void updateParticipantPasswordByPassword(String old_password, String new_password) throws Exception {
        this.JDBC_TEMPLATE.update("UPDATE participants SET password = ?, date_modified = ? WHERE participant_id = ?",
                new_password, new Date(SAMPLEDATE.parse(DATE_NOW_FORMATTED).getTime()), Long.parseLong(old_password));
    }



    /* CREATORS */

    public String create(String participant_email, String participant_name, String job_code,
                         String password, String status, String work_unit_code) throws Exception {

        /* Check if a job code is already in the database */
        Long check_job = this.JOB.isJobExists(job_code);

        if(this.getParticipantByEmail(participant_email).size() == 0 && check_job != 0 &&
                this.JOB_STRUCTURAL_MAP.getWorkUnitByWUCode(work_unit_code, job_code).size() != 0) {

                /* If a status is null set it to the default value */
                status = ((status == null) ? "Not accessed" : status);

                /* Encode a password if it's not null */
                password = ((password != null) ? PASSWORD_ENCODER.encode(password) : null);

                this.JDBC_TEMPLATE.update("INSERT INTO participants (date_modified, job_id, participant_email, participant_name, " +
                                "password, status, wu_code) VALUES (?,?,?,?,?,?,?)", new Date(SAMPLEDATE.parse(DATE_NOW_FORMATTED)
                                .getTime()), check_job, participant_email, participant_name, password, status, Long.parseLong(work_unit_code));
                return "Created";
        } else { return "Error:\n 1) participant is already in db\n 2) this job does not exist\n 3) there is no such work unit"; }
    }





    /* HELPERS */

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
            participants.setPASSWORD_TWO(rs.getString("password_two"));
            return participants;
        }
    };
}
