package application.repositories;

import application.models.Programs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProgramRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /* Find a program by its id */
    public Programs getProgramByID(String program_id) {
        Long p_id = Long.parseLong(program_id);
        String query = "SELECT * FROM programs WHERE db_id = ?";
        return this.jdbcTemplate.queryForObject(query, programsRowMapper, p_id);
    }

    /* Find a program by its name */
    public Programs getProgramByName(String program_name) {
        String query = "SELECT * FROM programs WHERE program_name = ?";
        return this.jdbcTemplate.queryForObject(query, programsRowMapper, program_name);
    }

    /* Create a new program */
    public String create(String program_name, String module_name) throws Exception {
        String query = "INSERT INTO programs (program_name, module_name) VALUES (?,?)";
        this.jdbcTemplate.update(query, program_name, module_name);
        return "Created";
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
