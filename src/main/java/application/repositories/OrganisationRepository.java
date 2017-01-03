package application.repositories;
import application.models.Organisations;
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

    private SimpleDateFormat sampledate = new SimpleDateFormat("dd/MM/yyyy", new Locale("en-au", "AU"));
    private String date_modified_formated = sampledate.format(new java.util.Date());

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

    /* Select all Clients */
    public List<Organisations> getAll() {
        return this.jdbcTemplate.query("SELECT * FROM clients", orgMapper);
    }


    /* CREATORS */

    /* Create an organisation */
    public void create(String orgname, String clientname) throws Exception {
        this.jdbcTemplate.update("INSERT INTO clients (org_name, client_name,date_modified) VALUES (?,?,?)",
                orgname, clientname, new Date(sampledate.parse(date_modified_formated).getTime()));
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

    /* Check if a Client is already linked to an Organisation */
    public Boolean checkCombinationClientOrgExists(String client_name, String org_name) {
        return (this.jdbcTemplate.query("SELECT * FROM clients WHERE client_name = ? AND org_name = ?",
                orgMapper, client_name, org_name).size() != 0) ? true : false;
    }

    /* Map data from the database to the Organisations model */
    private static final RowMapper<Organisations> orgMapper = new RowMapper<Organisations>() {
        public Organisations mapRow(ResultSet rs, int rowNum) throws SQLException {
            Organisations organisation = new Organisations();
            organisation.setCLIENT_ID(rs.getLong("client_id"));
            organisation.setORG_NAME(rs.getString("org_name"));
            organisation.setCLIENT_NAME(rs.getString("client_name"));
            organisation.setDATE_MODIFIED(rs.getDate("date_modified"));
            return organisation;
        }
    };
}
