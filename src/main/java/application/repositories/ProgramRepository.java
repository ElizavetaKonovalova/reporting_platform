package application.repositories;

import application.models.Programs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProgramRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public ProgramRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /* GETTERS */

    /* Find a program by its id */
    public Programs getProgramByID(String program_id) {
        try {
            Long p_id = Long.parseLong(program_id);
            return this.jdbcTemplate.queryForObject("SELECT * FROM programs WHERE db_id = ?",
                    programsRowMapper, p_id);
        } catch (Exception e) { return new Programs(); }
    }

    /* Find a program by its program name */
    public Programs getProgramByProgName(String program_name) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM programs WHERE program_name = ?",
                    programsRowMapper, program_name);
        } catch (Exception e) { return new Programs(); }
    }

    /* Find a program by its module name */
    public Programs getProgramByModName(String module_name) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM programs WHERE module_name = ?",
                    programsRowMapper, module_name);
        } catch (Exception e) { return new Programs(); }
    }


    /* REMOVALS */

    /* Remove a combination program module by both names */
    public void removeProgramByNames(String program_name, String module_name) {
        Long program_id = this.getProgramIDByNames(program_name, module_name);
        if(program_id != 0) {
            this.jdbcTemplate.update("DELETE FROM programs WHERE db_id = ?", program_id);
        }
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
        try {
             Programs programs = this.jdbcTemplate.queryForObject("SELECT * FROM programs WHERE program_name = ? AND module_name = ?",
                    programsRowMapper, program_name, module_name);
            return programs.getDB_ID();
        } catch (Exception e) { return 0L; }
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
