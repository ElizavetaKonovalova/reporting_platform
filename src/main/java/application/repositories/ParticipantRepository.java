package application.repositories;

import application.models.JobStructuralMaps;
import application.models.Jobs;
import application.models.Participants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

@Repository
public class ParticipantRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;
    private JobRepository jobRepository;
    private JobStructuralMapRepository jobStructuralMapRepository = new JobStructuralMapRepository();
    private SimpleDateFormat sampledate = new SimpleDateFormat("dd/MM/yyyy", new Locale("en-au", "AU"));


    /* GETTERS */


    /* REMOVALS */


    /* NULLERS */


    /* CREATORS */

    public String create(String participant_email, String participant_name, String job_code,
                         String password, String status, String wu_code) throws Exception {

        /* Check if a participant is already in the database */
        Long check_participant = this.isParticipantIDExist(participant_email);
        Long check_job = this.jobRepository.isJobExists(job_code);

        if(check_participant == 0 && check_job != 0 ) {

            Long check_wu = this.jobStructuralMapRepository.isWUExists(check_job, wu_code);

            if(check_wu != 0L) {
                String query = "INSERT INTO participants (date_modified, job_id, participant_email, participant_name, password, " +
                        "status, wu_code) VALUES (?,?,?,?,?,?,?)";
                sampledate.setTimeZone(TimeZone.getTimeZone("AEST"));
                String date_modified_formated = sampledate.format(new java.util.Date());
                this.jdbcTemplate.update(query, new Date(sampledate.parse(date_modified_formated).getTime()), check_job,
                        participant_email, participant_name, password, status, check_wu);
                return "Created";
            } else { return "This work unit does not belong to this job"; }
        } else { return "Either this participant is already in db, or this job does not exist!"; }
    }


    /* HELPERS */

    /* Check if current participant exists in the database */
    public Long checkParticipantExists(String participant_email) {
        try {
            return this.jdbcTemplate.queryForObject(
                    "SELECT * FROM participants WHERE participant_email = ?",
                    participantsRowMapper, participant_email).getPARTICIPANT_ID();
        } catch (Exception e) { return 0L; }
    }

    /* Check if a participant with a parsed email address exists in the Participants database */
    public Long isParticipantIDExist(String participant_email) {
        try {
            Participants participants = this.jdbcTemplate.queryForObject(
                    "SELECT * FROM participants WHERE participant_email = ?",
                    participantsRowMapper, participant_email);
            return participants.getPARTICIPANT_ID();
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
            participants.setSTATUS(rs.getString("status").charAt(0));
            participants.setJOB_ID(rs.getLong("job_id"));
            participants.setWU_CODE(rs.getLong("wu_code"));
            return participants;
        }
    };
}
