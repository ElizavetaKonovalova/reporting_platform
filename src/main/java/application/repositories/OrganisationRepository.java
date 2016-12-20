package application.repositories;
import application.models.Organisations;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrganisationRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;


    /* GETTERS */


    /* Select an organisation based on its ORGName */
    public Organisations getOrgByOrgName(String orgname) {
        try {
            String query = "SELECT * FROM clients WHERE org_name = ?";
            return this.jdbcTemplate.queryForObject( query, orgMapper, orgname);
        } catch (Exception e) { return new Organisations(); }
    }

    /* Select an organisation based on its Name */
    public Organisations getOrgByClientName(String clientname) {
        try {
            String query = "SELECT * FROM clients WHERE client_name = ?";
            return this.jdbcTemplate.queryForObject( query, orgMapper, clientname);
        } catch (Exception e) { return new Organisations(); }
    }

    /* Select an organisation based on its Location*/
    public Organisations getOrgByClientID(String clientid) {
        try {
            Long id = Long.parseLong(clientid);
            String query = "SELECT * FROM clients WHERE client_id = ?";
            return this.jdbcTemplate.queryForObject( query, orgMapper, id);
        } catch (Exception e) { return new Organisations(); }
    }


    /* CREATORS */

    /* Create an organisation */
    public void create(String orgname, String clientname) {
        String query = "INSERT INTO clients (org_name, client_name) VALUES (?,?)";
        this.jdbcTemplate.update(query, orgname, clientname);
    }


    /* REMOVALS */


    /* Remove an organisation by its database ID */
    public void removeOrgByClientID(String clientid) {
        Long id = Long.parseLong(clientid);
        String query = "DELETE FROM clients WHERE client_id = ?";
        this.jdbcTemplate.update(query, id);
    }

    /* Remove an organisation by its Org Name */
    public void removeOrgByOrgName(String orgname) {
        String query = "DELETE FROM clients WHERE org_name = ?";
        this.jdbcTemplate.update(query, orgname);
    }

    /* Remove an organisation by its Org Name */
    public void removeOrgByClientName(String clientname) {
        String query = "DELETE FROM clients WHERE client_name = ?";
        this.jdbcTemplate.update(query, clientname);
    }


    /* NULLERS */


    /* HELPERS */

    /* Check if a Client exists in the database */
    public Long checkClientExists(String client_name) {
        List<Organisations> organisations = this.jdbcTemplate.query("SELECT * FROM clients WHERE client_name = ?", orgMapper, client_name);
        if(!organisations.get(0).getCLIENT_NAME().isEmpty()) { return organisations.get(0).getCLIENT_ID(); } else { return 0L; }
    }

    /* Map data from the database to the Organisations model */
    private static final RowMapper<Organisations> orgMapper = new RowMapper<Organisations>() {
        public Organisations mapRow(ResultSet rs, int rowNum) throws SQLException {
            Organisations organisation = new Organisations();
            organisation.setCLIENT_ID(rs.getLong("client_id"));
            organisation.setORG_NAME(rs.getString("org_name"));
            organisation.setCLIENT_NAME(rs.getString("client_name"));
            return organisation;
        }
    };
}
