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

    /* Select an organisation based on its Name */
    public Organisations getOrgByName(String orgname) {
        String query = "SELECT * FROM organisations WHERE orgname = ?";
        try {
            return this.jdbcTemplate.queryForObject( query, orgMapper, orgname);
        } catch (Exception e) { return new Organisations(); }
    }

    /* Select an organisation based on its Location*/
    public Organisations getOrgByLocation(String location) {
        String query = "SELECT * FROM organisations WHERE location = ?";
        try {
            return this.jdbcTemplate.queryForObject( query, orgMapper, location);
        } catch (Exception e) { return new Organisations(); }
    }

    /* Select an organisation based on its database ID */
    public Organisations getOrgByID(String id) {
        Long orgid = Long.parseLong(id);
        String query = "SELECT * FROM organisations WHERE id = ?";
        try {
            return this.jdbcTemplate.queryForObject(query, orgMapper, orgid);
        } catch (Exception e) { return new Organisations(); }
    }

    /* Create an organisation */
    public void create(String orgname, String location) {
        String query = "INSERT INTO organisations (orgname, location) VALUES (?,?)";
        this.jdbcTemplate.update(query, orgname, location);
    }

    /* Remove an organisation by its database ID */
    public void removeOrgByID(Long id) {
        String query = "DELETE FROM organisations WHERE id = ?";
        this.jdbcTemplate.update(query, id);
    }

    /* Remove an organisation by its Name */
    public void removeOrgByName(String orgname) {
        String query = "DELETE FROM organisations WHERE orgname = ?";
        this.jdbcTemplate.update(query, orgname);
    }

    /* Map data from the database to the Organisations model */
    private static final RowMapper<Organisations> orgMapper = new RowMapper<Organisations>() {
        public Organisations mapRow(ResultSet rs, int rowNum) throws SQLException {
            Organisations organisation = new Organisations();
            organisation.setID(rs.getLong("id"));
            organisation.setORGNAME(rs.getString("orgname"));
            organisation.setLOCATION(rs.getString("location"));
            return organisation;
        }
    };
}
