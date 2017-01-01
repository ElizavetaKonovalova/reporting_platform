package application.repositories;

import application.models.ClientsStructuralMaps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClientStructuralMapRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ClientStructuralMapRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* GETTERS */

    /* Select a Work Unit by its ID in the database */
    public ClientsStructuralMaps getWorkUnitByDBID(String id) {
        try {
            Long db_id_long = Long.parseLong(id);
            String query = "SELECT * FROM client_structural_maps WHERE db_id = ?";
            return this.jdbcTemplate.queryForObject( query, wuMapper, db_id_long);
        } catch (Exception e) { return new ClientsStructuralMaps(); }
    }

    /* Select a Work Unit by its Name */
    public List<ClientsStructuralMaps> getClientsStructuralMapsByName(String name) {
        String query = "SELECT * FROM client_structural_maps WHERE wu_name = ?";
        return this.jdbcTemplate.query( query, wuMapper, name);
    }

    /* Select a Work Unit by its Work Unit ID */
    public ClientsStructuralMaps getWorkUnitByWUID(String wuid) {
        try {
            return this.jdbcTemplate.queryForObject( "SELECT * FROM client_structural_maps WHERE wu_id = ?",
                    wuMapper, Integer.parseInt(wuid));
        } catch (Exception e) { return new ClientsStructuralMaps(); }
    }

    /* Select Work Units by Level */
    public List<ClientsStructuralMaps> getClientsStructuralMapsByLevel(String level) {
        return this.jdbcTemplate.query( "SELECT * FROM client_structural_maps WHERE ? IN (wu_level_zero, wu_level_one, " +
                "wu_level_two, wu_level_three, wu_level_four, wu_level_five)", wuMapper, level);
    }

    /* Select Work Units by their Cohort */
    public List<ClientsStructuralMaps> getClientsStructuralMapsByCohort(String wucohort) {
        return this.jdbcTemplate.query( "SELECT * FROM client_structural_maps WHERE cohort = ?", wuMapper, wucohort);
    }

    /* Select Work Units by their Matrix */
    public List<ClientsStructuralMaps> getClientsStructuralMapsByMatrix(String matrix) {
        String query = "SELECT * FROM client_structural_maps WHERE ? IN (matrix_one, matrix_two, matrix_three, matrix_four, matrix_five)";
        return this.jdbcTemplate.query( query, wuMapper, matrix);
    }

    /* Select Work Units by their Niche */
    public List<ClientsStructuralMaps> getClientsStructuralMapsByNiche(String niche) {
        String query = "SELECT * FROM client_structural_maps WHERE niche = ?";
        return this.jdbcTemplate.query( query, wuMapper, niche);
    }

    /* Select Work Units by their Sector */
    public List<ClientsStructuralMaps> getClientsStructuralMapsBySector(String sector) {
        String query = "SELECT * FROM client_structural_maps WHERE sector = ?";
        return this.jdbcTemplate.query( query, wuMapper, sector);
    }

    /* Select Work Units by their Location */
    public List<ClientsStructuralMaps> getClientsStructuralMapsByLocation(String location) {
        String query = "SELECT * FROM client_structural_maps WHERE location = ?";
        return this.jdbcTemplate.query( query, wuMapper, location);
    }

    /* Select Work Units by their Client ID */
    public List<ClientsStructuralMaps> getClientsStructuralMapsByClientID(String client_id) {
        String query = "SELECT * FROM client_structural_maps WHERE client_id = ?";
        return this.jdbcTemplate.query( query, wuMapper, client_id);
    }


    /* REMOVALS */


    /* Remove Work Units by Name */
    public void removeWUByName(String wu_name) {
        String query = "DELETE FROM client_structural_maps WHERE wu_name = ?";
        this.jdbcTemplate.update(query, wu_name);
    }

    /* Remove Work Units by Work Unit ID */
    public void removeWUByWUID(String wu_id) {
        Integer wu_id_int = Integer.parseInt(wu_id);
        String query = "DELETE FROM client_structural_maps WHERE wu_id = ?";
        this.jdbcTemplate.update(query, wu_id);
    }

    /* Remove Work Units by database ID */
    public void removeWUByDBID(String db_id) {
        Long db_id_long = Long.parseLong(db_id);
        String query = "DELETE FROM client_structural_maps WHERE db_id = ?";
        this.jdbcTemplate.update(query, db_id_long);
    }

    /* Remove Work Units by Client ID */
    public void removeWUByClientID(String client_id) {
        Integer client_id_int = Integer.parseInt(client_id);
        String query = "DELETE FROM client_structural_maps WHERE client_id = ?";
        this.jdbcTemplate.update(query, client_id_int);
    }

    /* Remove Work Units by Location */
    public void removeWUByLocation(String location) {
        String query = "DELETE FROM client_structural_maps WHERE location = ?";
        this.jdbcTemplate.update(query, location);
    }

    /* Remove Work Units by Cohort */
    public void removeWUByCohort(String cohort) {
        String query = "DELETE FROM client_structural_maps WHERE cohort = ?";
        this.jdbcTemplate.update(query, cohort);
    }

    /* Remove Work Units by Sector */
    public void removeWUBySector(String sector) {
        String query = "DELETE FROM client_structural_maps WHERE sector = ?";
        this.jdbcTemplate.update(query, sector);
    }

    /* Remove Work Units by Niche */
    public void removeWUByNiche(String niche) {
        String query = "DELETE FROM client_structural_maps WHERE niche = ?";
        this.jdbcTemplate.update(query, niche);
    }


    /* NULLERS */



    /* Null Niche */
    public void nullNiche(String niche) {
        String query = "UPDATE client_structural_maps SET niche = NULL WHERE niche = ?";
        this.jdbcTemplate.update(query, niche);
    }

    /* Null Matrix One by its Name */
    public void nullMatrixOne(String matrix_one) {
        String query = "UPDATE client_structural_maps SET matrix_one = NULL WHERE matrix_one = ?";
        this.jdbcTemplate.update(query, matrix_one);
    }

    /* Null Matrix Two by its Name */
    public void nullMatrixTwo(String matrix_two) {
        String query = "UPDATE client_structural_maps SET matrix_two = NULL WHERE matrix_two = ?";
        this.jdbcTemplate.update(query, matrix_two);
    }

    /* Null Matrix Three by its Name */
    public void nullMatrixThree(String matrix) {
        String query = "UPDATE client_structural_maps SET matrix_three = NULL WHERE matrix_three = ?";
        this.jdbcTemplate.update(query, matrix);
    }

    /* Null Matrix Four by its Name */
    public void nullMatrixFour(String matrix) {
        String query = "UPDATE client_structural_maps SET matrix_four = NULL WHERE matrix_four = ?";
        this.jdbcTemplate.update(query, matrix);
    }

    /* Null Matrix Five by its Name */
    public void nullMatrixFive(String matrix) {
        String query = "UPDATE client_structural_maps SET matrix_five = NULL WHERE matrix_five = ?";
        this.jdbcTemplate.update(query, matrix);
    }

    /* Null Level Zero by its Name */
    public void nullLevelZero(String level) {
        String query = "UPDATE client_structural_maps SET wu_level_zero = NULL WHERE wu_level_zero = ?";
        this.jdbcTemplate.update(query, level);
    }

    /* Null Level One by its Name */
    public void nullLevelOne(String level) {
        String query = "UPDATE client_structural_maps SET wu_level_one = NULL WHERE wu_level_one = ?";
        this.jdbcTemplate.update(query, level);
    }

    /* Null Level Two by its Name */
    public void nullLevelTwo(String level) {
        String query = "UPDATE client_structural_maps SET wu_level_two = NULL WHERE wu_level_two = ?";
        this.jdbcTemplate.update(query, level);
    }

    /* Null Level Three by its Name */
    public void nullLevelThree(String level) {
        String query = "UPDATE client_structural_maps SET wu_level_three = NULL WHERE wu_level_three = ?";
        this.jdbcTemplate.update(query, level);
    }

    /* Null Level Four by its Name */
    public void nullLevelFour(String level) {
        String query = "UPDATE client_structural_maps SET wu_level_four = NULL WHERE wu_level_four= ?";
        this.jdbcTemplate.update(query, level);
    }

    /* Null Level Five by its Name */
    public void nullLevelFive(String level) {
        String query = "UPDATE client_structural_maps SET wu_level_five = NULL WHERE wu_level_five = ?";
        this.jdbcTemplate.update(query, level);
    }

    /* Null Cohort */
    public void nullCohort(String cohort) {
        String query = "UPDATE client_structural_maps SET cohort = NULL WHERE cohort = ?";
        this.jdbcTemplate.update(query, cohort);
    }

    /* Null Sector */
    public void nullSector(String sector) {
        String query = "UPDATE client_structural_maps SET sector = NULL WHERE sector = ?";
        this.jdbcTemplate.update(query, sector);
    }

    /* Null Location */
    public void nullLocation(String location) {
        String query = "UPDATE client_structural_maps SET location = NULL WHERE location = ?";
        this.jdbcTemplate.update(query, location);
    }


    /* CREATORS */


    /* Create a Work Unit */
    public String create(String cohort, String location, String niche, String matrixone, String matrixtwo,
                       String matrixthree, String matrixfour, String matrixfive, String wu_name, String wu_id,
                       String sector, String wu_level_five, String wu_level_four, String wu_level_one,
                       String wu_level_three, String wu_level_two, String wu_level_zero, String client_id) throws Exception {

        Integer client_id_int = Integer.parseInt(client_id);
        Integer wu_id_int = Integer.parseInt(wu_id);

        String query = "INSERT INTO client_structural_maps (cohort, location, " +
                "matrix_five, matrix_four, matrix_one, matrix_three, matrix_two, niche, sector, wu_id, " +
                "wu_level_five, wu_level_four, wu_level_one, wu_level_three, wu_level_two, wu_level_zero, wu_name, client_id)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(query, cohort, location, matrixfive, matrixfour, matrixone, matrixthree,
                matrixtwo, niche, sector, wu_id_int, wu_level_five, wu_level_four, wu_level_one,
                wu_level_three, wu_level_two, wu_level_zero, wu_name, client_id_int);

        return "Created";
    }


    /* HELPERS */


    /* Map data from the database to the ClientsStructuralMaps model */
    private static final RowMapper<ClientsStructuralMaps> wuMapper = new RowMapper<ClientsStructuralMaps>() {
        public ClientsStructuralMaps mapRow(ResultSet rs, int rowNum) throws SQLException {
            ClientsStructuralMaps client_structural_maps = new ClientsStructuralMaps();
            client_structural_maps.setDB_ID(rs.getLong("db_id"));
            client_structural_maps.setCOHORT(rs.getString("cohort"));
            client_structural_maps.setLOCATION(rs.getString("location"));
            client_structural_maps.setMATRIX_ONE(rs.getString("matrix_one"));
            client_structural_maps.setMATRIX_TWO(rs.getString("matrix_two"));
            client_structural_maps.setMATRIX_THREE(rs.getString("matrix_three"));
            client_structural_maps.setMATRIX_FOUR(rs.getString("matrix_four"));
            client_structural_maps.setMATRIX_FIVE(rs.getString("matrix_five"));
            client_structural_maps.setNICHE(rs.getString("niche"));
            client_structural_maps.setSECTOR(rs.getString("sector"));
            client_structural_maps.setWU_ID(rs.getInt("wu_id"));
            client_structural_maps.setWU_LEVEL_FIVE(rs.getString("wu_level_five"));
            client_structural_maps.setWU_LEVEL_FOUR(rs.getString("wu_level_four"));
            client_structural_maps.setWU_LEVEL_ONE(rs.getString("wu_level_one"));
            client_structural_maps.setWU_LEVEL_THREE(rs.getString("wu_level_three"));
            client_structural_maps.setWU_LEVEL_TWO(rs.getString("wu_level_two"));
            client_structural_maps.setWU_LEVEL_ZERO(rs.getString("wu_level_zero"));
            client_structural_maps.setNAME(rs.getString("wu_name"));
            client_structural_maps.setCLIENT(rs.getLong("client_id"));
            client_structural_maps.setDATE_MODIFIED(rs.getDate("date_modified"));
            return client_structural_maps;
        }
    };
}
