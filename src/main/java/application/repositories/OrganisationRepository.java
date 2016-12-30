package application.repositories;
import application.models.Organisations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrganisationRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public OrganisationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* GETTERS */


    /* Select an organisation based on its ORGName */
    public List<Organisations> getOrgByOrgName(String orgname) {
            return this.jdbcTemplate.query( "SELECT * FROM clients WHERE org_name = ?",
                    orgMapper, orgname);
    }

    /* Select an organisation based on its Name */
    public List<Organisations> getOrgByClientName(String clientname) {
        return this.jdbcTemplate.query("SELECT * FROM clients WHERE client_name = ?",
                orgMapper, clientname);
    }

    /* Select an organisation based on its Location*/
    public List<Organisations> getOrgByClientID(String clientid) {
            return this.jdbcTemplate.query("SELECT * FROM clients WHERE client_id = ?",
                    orgMapper, Long.parseLong(clientid));
    }


    /* CREATORS */

    /* Create an organisation */
    public void create(String orgname, String clientname) {
        this.jdbcTemplate.update("INSERT INTO clients (org_name, client_name) VALUES (?,?)",
                orgname, clientname);
    }


    /* REMOVALS */


    /* Remove an organisation by its database ID */
    public void removeOrgByClientID(String client_id) {
        this.jdbcTemplate.update("DELETE FROM clients WHERE client_id = ?",
                Long.parseLong(client_id));
    }

    /* Remove an organisation by its Org Name */
    public void removeOrgByOrgName(String org_name) {
        this.jdbcTemplate.update("DELETE FROM clients WHERE org_name = ?", org_name);
    }

    /* Remove an organisation by its Org Name */
    public void removeOrgByClientName(String client_name) {
        this.jdbcTemplate.update("DELETE FROM clients WHERE client_name = ?", client_name);
    }


    /* NULLERS */


    /* HELPERS */

    /* Check if a Client exists in the database */
    public Long checkClientExists(String client_name) {
        List<Organisations> organisations = this.jdbcTemplate.query("SELECT * FROM clients WHERE client_name = ?", orgMapper, client_name);
        if(organisations.size() != 0) { return organisations.get(0).getCLIENT_ID(); } else { return 0L; }
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
