package application.repositories;

import application.models.FieldRegistry;
import application.models.Programs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class FieldRegistryRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public FieldRegistry getFieldByID(String uuid) {
        try {
            UUID field_uuid = UUID.fromString(uuid);
            String query = "SELECT * FROM field_registry WHERE field_id = ?";
            return this.jdbcTemplate.queryForObject(query, fieldRegistryRowMapper, uuid);
        } catch (Exception e) { return new FieldRegistry(); }
    }

    public FieldRegistry getFieldByName(String field_name) {
        try {
            String query = "SELECT * FROM field_registry WHERE field_name = ?";
            return this.jdbcTemplate.queryForObject(query, fieldRegistryRowMapper, field_name);
        } catch (Exception e) { return new FieldRegistry(); }
    }

    public String create(String field_desc_one, String field_desc_two, String field_desc_three, String field_name,
                         String program_name, String module_name, String type) {

        Long program_id = getProgramIDByNames(program_name, module_name);

        /* Generate a new UUID for the field_id column */
        UUID uuid = UUID.randomUUID();

        String query = "INSERT INTO field_registry( field_id, field_description_one, field_description_three, field_description_two, " +
                "field_name, program_mod_id, type) VALUES(?,?,?,?,?,?,?)";

        /* Check if there is already a field with this name in the database */
        String check_query = "SELECT field_id FROM field_registry WHERE field_name = ?";
        List<FieldRegistry> fieldRegistry = this.jdbcTemplate.query(check_query, fieldRegistryRowMapper, field_name);

        /* If no such field create a new one */
        if(fieldRegistry.isEmpty()) {

            /* If a program with a provided name exists in the Program table, create a new field */
            if( program_id != 0L) {

                /* Check if a right type was parsed */
                if( type.equals("Text") || type.equals("Number") || type.equals("Demo")) {

                    /* If a type is correct, get the first letter of this type and parse as char to the database */
                    char type_char = type.charAt(0);
                    this.jdbcTemplate.update(query, uuid, field_desc_one, field_desc_three, field_desc_two, field_name, program_id, type_char);
                    return "Created";
                } else { return "Wrong type selected! Supported types Text, Number, and Demo."; }
            } else { return "No such program or module!"; }
        } else { return "A filed with this name already exists!"; }
    }

    /* Find a program id in the Program table based on a parsed name */
    private Long getProgramIDByNames(String program_name, String module_name) {
        String query = "SELECT db_id FROM programs WHERE program_name = ? AND module_name = ?";
        try {
            /* Find a program with a parsed name */
            List<Programs> programs = this.jdbcTemplate.query(query, ProgramRepository.programsRowMapper, program_name, module_name);
            if(programs.isEmpty()) {
                return 0L;
            } else { return programs.get(0).getDB_ID(); }
        } catch (Exception e) { return 0L;}
    }

    /* Map data from the database to the FieldRegistry model */
    public static final RowMapper<FieldRegistry> fieldRegistryRowMapper = new RowMapper<FieldRegistry>() {
        public FieldRegistry mapRow(ResultSet rs, int rowNum) throws SQLException {
            FieldRegistry fieldRegistry = new FieldRegistry();
            fieldRegistry.setFIELD_ID((java.util.UUID)rs.getObject("field_id"));
            fieldRegistry.setFIELD_DESCRIPTION_ONE(rs.getString("field_description_one"));
            fieldRegistry.setFIELD_DESCRIPTION_TWO(rs.getString("field_description_two"));
            fieldRegistry.setFIELD_DESCRIPTION_THREE(rs.getString("field_description_three"));
            fieldRegistry.setFIELD_NAME(rs.getString("field_name"));
            fieldRegistry.setPROGRAM_MOD_ID(rs.getLong("program_mod_id"));
            fieldRegistry.setTYPE(rs.getString("type"));
            return fieldRegistry;
        }
    };
}
