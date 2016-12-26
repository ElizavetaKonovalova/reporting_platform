package application.repositories;

import application.models.FieldRegistry;
import application.models.Programs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FieldRegistryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ProgramRepository programRepository = new ProgramRepository();


    /* GETTERS */

    /* Find a Field by ID */
    public List<FieldRegistry> getFieldByID(String uuid) {
        return this.jdbcTemplate.query("SELECT * FROM field_registry WHERE field_id = ?",
                    fieldRegistryRowMapper, UUID.fromString(uuid));
    }

    /* Find a Field by Name */
    public List<FieldRegistry> getFieldByName(String field_name) {
        return this.jdbcTemplate.query("SELECT * FROM field_registry WHERE field_name = ?",
                    fieldRegistryRowMapper, field_name);
    }

    /* Find a Field by a Program ID */
    public List<FieldRegistry> getFieldByProgramID(String program_id) {
        Long prograam_long = Long.parseLong(program_id);
        return this.jdbcTemplate.query("SELECT * FROM field_registry WHERE program_mod_id = ?",
                fieldRegistryRowMapper, prograam_long);
    }

    /* Find a Field by both a Program Name and a Module Name */
    public List<FieldRegistry> getFieldByProgramName(String program_name, String module_name) {
        Long program_long = this.programRepository.getProgramIDByNames(program_name, module_name);
        if(program_long != 0) {
            return this.jdbcTemplate.query("SELECT * FROM field_registry WHERE program_mod_id = ?",
                    fieldRegistryRowMapper, program_long);
        } else { return new ArrayList<>(); }
    }

    /* Find a Field by Descriptions */
    public List<FieldRegistry> getFieldByDescriptions(String description) {
        return this.jdbcTemplate.query("SELECT * FROM field_registry WHERE LIKE %?% IN (field_description_one, " +
                        "field_description_two, field_description_three)",
                    fieldRegistryRowMapper, description);
    }



    /* NULLERS */


    /* REMOVALS */


    /* CREATORS */


    public String create(String field_desc_one, String field_desc_two, String field_desc_three, String field_name,
                         String program_name, String module_name, String type) {

        Long program_id = this.programRepository.getProgramIDByNames(program_name, module_name);

        /* Generate a new UUID for the field_id column */
        UUID uuid = UUID.randomUUID();

        String query = "INSERT INTO field_registry( field_id, field_description_one, field_description_three, field_description_two, " +
                "field_name, program_mod_id, type) VALUES(?,?,?,?,?,?,?)";

        /* Check if there is already a field with this name in the database */
        String check_query = "SELECT * FROM field_registry WHERE field_name = ?";
        List<FieldRegistry> fieldRegistry = this.jdbcTemplate.query(check_query, fieldRegistryRowMapper, field_name);

        /* If no such field create a new one */
        if(fieldRegistry.size() != 0) {

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



    /* HELPERS */

    /* Check if a field with a passed field name exists in the database */
    public UUID checkFieldExists(String field_name) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM field_registry WHERE field_name = ?",
                    fieldRegistryRowMapper, field_name).getFIELD_ID();
        } catch (Exception e) { return new UUID(0L, 0L); }
    }


    /* Map data from the database to the FieldRegistry model */
    private static final RowMapper<FieldRegistry> fieldRegistryRowMapper = new RowMapper<FieldRegistry>() {
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
