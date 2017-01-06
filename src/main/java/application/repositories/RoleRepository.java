package application.repositories;

import application.models.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@Repository
public class RoleRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public RoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private SimpleDateFormat sampledate = new SimpleDateFormat("dd/MM/yyyy", new Locale("en-au", "AU"));
    private String date_modified_formated = sampledate.format(new java.util.Date());


    /* GETTERS */

    /* Select a Role by its database ID */
    public List<Roles> getRolesByRoleID(String db_id) throws Exception {
        return this.jdbcTemplate.query("SELECT * FROM roles WHERE db_id = ?", rolesRowMapper, Integer.parseInt(db_id));
    }

    /* Select a Role by its Role Name */
    public List<Roles> getRolesByRoleName(String role_name) {
        return this.jdbcTemplate.query("SELECT * FROM roles WHERE role_name = ?", rolesRowMapper, role_name);
    }

    /* Select a Role by itd Permission Level */
    public List<Roles> getRolesByPermissionLevel(String permission_level) throws Exception {
        return this.jdbcTemplate.query("SELECT * FROM roles WHERE permission_level = ?", rolesRowMapper,
                Short.parseShort(permission_level));
    }

    /* Select a Role by its Modification Date */
    public List<Roles> getRolesByDate(String date) throws Exception {
        return this.jdbcTemplate.query("SELECT * FROM roles WHERE date_modified = ?", rolesRowMapper,
                new Date(sampledate.parse(sampledate.format(date)).getTime()));
    }

    public List<Roles> getAll() {
        return this.jdbcTemplate.query("SELECT * FROM roles", rolesRowMapper);
    }



    /* UPDATERS */

    /* Update a Role Name by its Role Name */
    public void updateRoleNameByName(String old_role_name, String new_role_name) throws Exception {
        this.jdbcTemplate.update("UPDATE roles SET role_name = ?, date_modified = ? WHERE role_name = ?",
                new_role_name, new Date(sampledate.parse(date_modified_formated).getTime()), old_role_name);
    }

    /* Update a Role Name by its database ID */
    public void updateRoleNameByDBID(String db_id, String new_role_name) throws Exception {
        this.jdbcTemplate.update("UPDATE roles SET role_name = ?, date_modified = ? WHERE db_id = ?",
                new_role_name, new Date(sampledate.parse(date_modified_formated).getTime()), Integer.parseInt(db_id));
    }

    /* Update a Permission Level by its database ID */
    public void updatePermissionLevelByDBID(String db_id, String new_permission_lvl) throws Exception {
        this.jdbcTemplate.update("UPDATE roles SET permission_level = ?, date_modified = ? WHERE db_id = ?",
                Short.parseShort(new_permission_lvl), new Date(sampledate.parse(date_modified_formated).getTime()), Integer.parseInt(db_id));
    }

    /* Update a Permission Level by its Role Name */
    public void updatePermissionLevelByRoleName(String role_name, String new_permission_lvl) throws Exception {
        this.jdbcTemplate.update("UPDATE roles SET permission_level = ?, date_modified = ? WHERE role_name = ?",
                Short.parseShort(new_permission_lvl), new Date(sampledate.parse(date_modified_formated).getTime()), role_name);
    }


    /* REMOVALS */


    /* Remove a Role by its Name */
    public void removeRoleByName(String role_name) {
        this.jdbcTemplate.update("DELETE FROM roles WHERE role_name = ?", role_name);
    }

    /* Remove a Role by its database ID */
    public void removeRoleByDBID(String db_id) throws Exception {
        this.jdbcTemplate.update("DELETE FROM roles WHERE db_id = ?", Integer.parseInt(db_id));
    }

    /* Remove a Role by its Permission Level */
    public void removeRoleByPermissionLvl(String permission_level) throws Exception {
        this.jdbcTemplate.update("DELETE FROM roles WHERE permission_level = ?", Short.parseShort(permission_level));
    }


    /* CREATORS */

    public String create(String role_name, String permission_level) throws Exception {

        if(this.getRolesByRoleName(role_name).size() == 0) {

            permission_level = (permission_level == null) ? "0" : permission_level;
            this.jdbcTemplate.update("INSERT INTO roles (role_name, permission_level, date_modified) VALUES (?,?,?)",
                    role_name, Short.parseShort(permission_level), new Date(sampledate.parse(date_modified_formated).getTime()));
            return "Created";

        } else { return "This role already exists!"; }
    }


    /* HELPERS */

    /* Map data from the Roles table to the Roles model */
    private static final RowMapper<Roles> rolesRowMapper = new RowMapper<Roles>() {
        @Override
        public Roles mapRow(ResultSet rs, int rowNum) throws SQLException {
            Roles roles = new Roles();
            roles.setPERMISSION_LEVEL(rs.getShort("permission_level"));
            roles.setROLE_ID(rs.getInt("db_id"));
            roles.setROLE_NAME(rs.getString("role_name"));
            roles.setDATE_MODIFIED(rs.getDate("date_modified"));
            return roles;
        }
    };
}
