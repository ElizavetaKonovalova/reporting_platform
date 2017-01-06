package application.repositories;

import application.models.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

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


    /* GETTERS */

    /* UPDATERS */

    /* REMOVALS */


    /* CREATORS */


    /* HELPERS */

    /* Map data from the Roles table to the Roles model */
    private static final RowMapper<Roles> rolesRowMapper = new RowMapper<Roles>() {
        @Override
        public Roles mapRow(ResultSet rs, int rowNum) throws SQLException {
            Roles roles = new Roles();
            roles.setPERMISSION_LEVEL(rs.getShort("permission_level"));
            roles.setROLE_ID(rs.getInt("db_id"));
            roles.setROLE_NAME(rs.getString("role_name"));
            return roles;
        }
    };
}
