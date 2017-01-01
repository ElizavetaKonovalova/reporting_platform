package application.repositories;

import application.models.FieldRegistry;
import application.models.Programs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FieldRegistryRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public FieldRegistryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private ProgramRepository programRepository = new ProgramRepository(jdbcTemplate);


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
        return this.jdbcTemplate.query("SELECT * FROM field_registry WHERE program_mod_id = ?",
                fieldRegistryRowMapper, Long.parseLong(program_id));
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

    /* Find a Field by its Type */
    public List<FieldRegistry> getFieldByType(String type) {
        if(type.equals("number") || type.equals("text") || type.equals("demo")) {
            return this.jdbcTemplate.query("SELECT * FROM field_registry WHERE type = ?",
                    fieldRegistryRowMapper, type.charAt(0));
        } else { return new ArrayList<>(); }
    }


    /* UPDATERS */

    /* Update a Field Name by a Field ID */
    public String updateFieldNameByID(String field_id, String field_name_new) {
        try {
            this.jdbcTemplate.queryForObject("UPDATE field_registry SET field_name = ? WHERE field_id = ?",
                    fieldRegistryRowMapper, field_name_new, UUID.fromString(field_id));
            return "Updated";
        } catch (Exception e) { return "Could not update"; }
    }

    /* Update a Field Name by an old Field Name */
    public String updateFieldNameByName(String field_name_old, String field_name_new) {
        return this.updateString("UPDATE field_registry SET field_name = ? WHERE field_name = ?",
                field_name_new, field_name_old);
    }

    /* Update a Field Description One by a Field Name */
    public String updateFieldDescriptionOneByName(String field_name, String description_new) {
        return this.updateString("UPDATE field_registry SET field_description_one = ? WHERE field_name = ?",
                description_new, field_name);
    }

    /* Update a Field Description Two by a Field Name */
    public String updateFieldDescriptionTwoByName(String field_name, String description_new) {
        return this.updateString("UPDATE field_registry SET field_description_two = ? WHERE field_name = ?",
                description_new, field_name);
    }

    /* Update a Field Description Three by a Field Name */
    public String updateFieldDescriptionThreeByName(String field_name, String description_new) {
        return this.updateString("UPDATE field_registry SET field_description_three = ? WHERE field_name = ?",
                description_new, field_name);
    }

    /* Update a Program ID by a Field Name */
    public String updateProgramIDByName(String field_name, String program_name, String module_name) {
        try {
            Long program_id = this.programRepository.getProgramIDByNames(program_name, module_name);
            if(program_id != 0) {
                this.jdbcTemplate.queryForObject("UPDATE field_registry SET program_mod_id = ? WHERE field_name = ?",
                        fieldRegistryRowMapper, program_id, field_name);
                return "Updated";
            } else { return "There is no such program and module combination"; }
        } catch (Exception e) { return "Could not update"; }
    }

    /* Update a Type by a Field Name */
    public String updateFieldTypeByName(String field_name, String type) {
        if(type.equals("number") || type.equals("text") || type.equals("demo")) {
            return this.updateString("UPDATE field_registry SET type = ? WHERE field_name = ?",
                    type, field_name);
        } else { return "Please use the correct type format: number, text, or demo"; }
    }



    /* NULLERS */

    /* Set Field Description One to Null by a Field ID */
    public void nullDescriptionOne(String field_id) {
        this.jdbcTemplate.update("UPDATE field_registry SET field_description_one = NULL WHERE field_id = ?",
                UUID.fromString(field_id));
    }

    /* Set Field Description Two to Null by a Field ID */
    public void nullDescriptionTwo(String field_id) {
        this.jdbcTemplate.update("UPDATE field_registry SET field_description_two = NULL WHERE field_id = ?",
                UUID.fromString(field_id));
    }

    /* Set Field Description Three to Null by a Field ID */
    public void nullDescriptionThree(String field_id) {
        this.jdbcTemplate.update("UPDATE field_registry SET field_description_three = NULL WHERE field_id = ?",
                UUID.fromString(field_id));
    }

    /* Set Type to Null by a Field ID */
    public void nullType(String field_id) {
        this.jdbcTemplate.update("UPDATE field_registry SET type = NULL WHERE field_id = ?",
                UUID.fromString(field_id));
    }

    /* Set Program to Null by a Field ID */
    public void nullProgram(String field_id) {
        this.jdbcTemplate.update("UPDATE field_registry SET program_mod_id = NULL WHERE field_id = ?",
                UUID.fromString(field_id));
    }




    /* REMOVALS */

    /* Remove a row by a Field ID */
    public void removeByFieldID(String field_id) {
        this.jdbcTemplate.update("DELETE FROM field_registry WHERE field_id = ?", UUID.fromString(field_id));
    }

    /* Remove a row by a Field Name */
    public void removeByFieldName(String field_name) {
        this.jdbcTemplate.update("DELETE FROM field_registry WHERE field_name = ?", field_name);
    }

    /* Remove a row by a Field Type */
    public void removeByFieldType(String type) {
        this.jdbcTemplate.update("DELETE FROM field_registry WHERE type = ?", type);
    }

    /* Remove a row by a Field Program */
    public void removeByFieldProgram(String program, String module) {
        Long program_id = this.programRepository.getProgramIDByNames(program, module);
        if(program_id !=0 ) {
            this.jdbcTemplate.update("DELETE FROM field_registry WHERE program_mod_id = ?", program_id);
        }
    }



    /* CREATORS */


    public String create(String field_desc_one, String field_desc_two, String field_desc_three, String field_name,
                         String program_name, String module_name, String type) {

        Long program_id = this.programRepository.getProgramIDByNames(program_name, module_name);

        String query = "INSERT INTO field_registry( field_id, field_description_one, field_description_three, field_description_two, " +
                "field_name, program_mod_id, type) VALUES(?,?,?,?,?,?,?)";

        /* Check if there is already a field with this name in the database */
        String check_query = "SELECT * FROM field_registry WHERE field_name = ?";
        List<FieldRegistry> fieldRegistry = this.jdbcTemplate.query(check_query, fieldRegistryRowMapper, field_name);

        /* If no such field create a new one */
        if(fieldRegistry.size() == 0) {

            /* If a program with a provided name exists in the Program table, create a new field */
            if( program_id != 0) {

                /* Check if a right type was parsed */
                if( type.equals("text") || type.equals("number") || type.equals("demo")) {

                    /* If a type is correct, get the first letter of this type and parse as char to the database */
                    char type_char = type.charAt(0);
                    this.jdbcTemplate.update(query, UUID.randomUUID(), field_desc_one, field_desc_three,
                            field_desc_two, field_name, program_id, type_char);
                    return "Created";
                } else { return "Wrong type selected! Supported types Text, Number, and Demo."; }
            } else { return "No such program or module!"; }
        } else { return "A filed with this name already exists!"; }
    }



    /* HELPERS */

    /* Update helper */
    private String updateString(String query, String parameter_one, String parameter_two) {
        try {
            this.jdbcTemplate.queryForObject(query, fieldRegistryRowMapper, parameter_one, parameter_two);
            return "Updated";
        } catch (Exception e) { return "Could not update"; }
    }


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
            fieldRegistry.setDATE_MODIFIED(rs.getDate("date_modified"));
            return fieldRegistry;
        }
    };
}
