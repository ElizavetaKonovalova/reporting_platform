package application.repositories;

import application.models.Programs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProgramRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;


    /* GETTERS */

    /* Find a program by its id */
    public Programs getProgramByID(String program_id) {
        try {
            Long p_id = Long.parseLong(program_id);
            return this.jdbcTemplate.queryForObject("SELECT * FROM programs WHERE db_id = ?",
                    programsRowMapper, p_id);
        } catch (Exception e) { return new Programs(); }
    }

    /* Find a program by its name */
    public Programs getProgramByName(String program_name) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM programs WHERE program_name = ?",
                    programsRowMapper, program_name);
        } catch (Exception e) { return new Programs(); }
    }


    /* CREATORS */


    /* Create a new program */
    public String create(String program_name, String module_name) throws Exception {
        this.jdbcTemplate.update("INSERT INTO programs (program_name, module_name) VALUES (?,?)",
                program_name, module_name);
        return "Created";
    }


    /* HELPERS */

    /* Find a Program id in the Program table based on a parsed name */
    public Long getProgramIDByNames(String program_name, String module_name) {
        String query = "SELECT * FROM programs WHERE program_name = ? AND module_name = ?";
        try {
            /* Find a program with a parsed name */
            List<Programs> programs = this.jdbcTemplate.query(query, programsRowMapper, program_name, module_name);
            if(programs.size() == 0) {
                return 0L;
            } else { return programs.get(0).getDB_ID(); }
        } catch (Exception e) { return 0L;}
    }


    /* Map data from the database to the Programs model */
    public static final RowMapper<Programs> programsRowMapper = new RowMapper<Programs>() {
        public Programs mapRow(ResultSet rs, int rowNum) throws SQLException {
            Programs programs = new Programs();
            programs.setDB_ID(rs.getLong("db_id"));
            programs.setPROGRAM_NAME(rs.getString("program_name"));
            programs.setMODULE_NAME(rs.getString("module_name"));
            return programs;
        }
    };
}
