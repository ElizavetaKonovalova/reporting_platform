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

    public Organisations getOrgByName(String orgname) {
        String query = "SELECT * FROM organisations WHERE orgname = ?";
        try {
            return this.jdbcTemplate.queryForObject( query, orgMapper, orgname);
        } catch (Exception e) { return new Organisations(); }
    }

    public Organisations getOrgByID(Long id) {
        String query = "SELECT * FROM organisations WHERE id = ?";
        try {
            return this.jdbcTemplate.queryForObject(query, orgMapper, id);
        } catch (Exception e) { return new Organisations(); }
    }

    public void create(String orgname, String location) {
        String query = "INSERT INTO organisations (orgname, location) VALUES (?,?)";
        this.jdbcTemplate.update(query, orgname, location);
    }

    public void removeOrgByID(Long id) {
        String query = "DELETE FROM organisations WHERE id = ?";
        this.jdbcTemplate.update(query, id);
    }

    public void removeOrgByName(String orgname) {
        String query = "DELETE FROM organisations WHERE orgname = ?";
        this.jdbcTemplate.update(query, orgname);
    }

    /* Map data from the database to the Jobs model */
    private static final RowMapper<Organisations> orgMapper = new RowMapper<Organisations>() {
        public Organisations mapRow(ResultSet rs, int rowNum) throws SQLException {
            Organisations organisation = new Organisations();
            organisation.setORGNAME(rs.getString("orgname"));
            organisation.setLOCATION(rs.getString("location"));
            return organisation;
        }
    };
}
