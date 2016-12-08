package application.repositories;
import application.models.Organisations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ekonovalova on 12/5/2016.
 */
@Repository
public class OrganisationRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /* Select an organisation based on its ORGName */
    public Organisations getOrgByOrgName(String orgname) {
        String query = "SELECT * FROM organisations WHERE orgname = ?";
        try {
            return this.jdbcTemplate.queryForObject( query, orgMapper, orgname);
        } catch (Exception e) { return new Organisations(); }
    }

    /* Select an organisation based on its Name */
    public Organisations getOrgByClientName(String clientname) {
        String query = "SELECT * FROM organisations WHERE clientname = ?";
        try {
            return this.jdbcTemplate.queryForObject( query, orgMapper, clientname);
        } catch (Exception e) { return new Organisations(); }
    }

    /* Select an organisation based on its Location*/
    public Organisations getOrgByClientID(String clientid) {
        Long id = Long.parseLong(clientid);
        String query = "SELECT * FROM organisations WHERE clientid = ?";
        try {
            return this.jdbcTemplate.queryForObject( query, orgMapper, id);
        } catch (Exception e) { return new Organisations(); }
    }

    /* Create an organisation */
    public void create(String orgname, String clientname) {
        String query = "INSERT INTO organisations (orgname, clientname) VALUES (?,?)";
        this.jdbcTemplate.update(query, orgname, clientname);
    }

    /* Remove an organisation by its database ID */
    public void removeOrgByClientID(String clientid) {
        Long id = Long.parseLong(clientid);
        String query = "DELETE FROM organisations WHERE clientid = ?";
        this.jdbcTemplate.update(query, id);
    }

    /* Remove an organisation by its Org Name */
    public void removeOrgByOrgName(String orgname) {
        String query = "DELETE FROM organisations WHERE orgname = ?";
        this.jdbcTemplate.update(query, orgname);
    }

    /* Remove an organisation by its Org Name */
    public void removeOrgByClientName(String clientname) {
        String query = "DELETE FROM organisations WHERE clientname = ?";
        this.jdbcTemplate.update(query, clientname);
    }

    /* Map data from the database to the Organisations model */
    private static final RowMapper<Organisations> orgMapper = new RowMapper<Organisations>() {
        public Organisations mapRow(ResultSet rs, int rowNum) throws SQLException {
            Organisations organisation = new Organisations();
            organisation.setCLIENT_ID(rs.getLong("clientid"));
            organisation.setORG_NAME(rs.getString("orgname"));
            organisation.setCLIENT_NAME(rs.getString("clientname"));
            return organisation;
        }
    };
}
