package application.repositories;

import application.models.Participants;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ParticipantRepository {

    /* Map data from the database to the Participants model */
    public static final RowMapper<Participants> participantsRowMapper = new RowMapper<Participants>() {
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
