package application.repositories;

import application.models.ClientsStructuralMaps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClientStructuralMapsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /* Select a work unit by its ID in the database */
    public ClientsStructuralMaps getWorkUnitByID(Long id) {
        try {
            String query = "SELECT * FROM client_structural_maps WHERE db_id = ?";
            return this.jdbcTemplate.queryForObject( query, wuMapper, id);
        } catch (Exception e) { return new ClientsStructuralMaps(); }
    }

    /* Select a work unit by its Name */
    public List<ClientsStructuralMaps> getClientsStructuralMapsByName(String name) {
        String query = "SELECT * FROM client_structural_maps WHERE wu_name = ?";
        return this.jdbcTemplate.query( query, wuMapper, name);
    }

    /* Select a work unit by its Work Unit ID */
    public ClientsStructuralMaps getWorkUnitByWUID(String wuid) {
        try {
            Integer workunitid = Integer.parseInt(wuid);
            String query = "SELECT * FROM client_structural_maps WHERE wu_id = ?";
            return this.jdbcTemplate.queryForObject( query, wuMapper, workunitid);
        } catch (Exception e) { return new ClientsStructuralMaps(); }
    }

    /* Select work units by level */
    public List<ClientsStructuralMaps> getClientsStructuralMapsByLevel(String level) {
        String query = "SELECT * FROM client_structural_maps WHERE ? IN (wu_level_zero, wu_level_one, " +
                "wu_level_two, wu_level_three, wu_level_four, wu_level_five)";
        return this.jdbcTemplate.query( query, wuMapper, level);
    }

    /* Select work units by thier Cohort */
    public List<ClientsStructuralMaps> getClientsStructuralMapsByCohort(String wucohort) {
        String query = "SELECT * FROM client_structural_maps WHERE cohort = ?";
        return this.jdbcTemplate.query( query, wuMapper, wucohort);
    }

    /* Select work units by thier Cohort */
    public List<ClientsStructuralMaps> getClientsStructuralMapsByMatrix(String matrix) {
        String query = "SELECT * FROM client_structural_maps WHERE ? IN (matrix_one, matrix_two, matrix_three, matrix_four, matrix_five)";
        return this.jdbcTemplate.query( query, wuMapper, matrix);
    }

    /* Create a work unit */
    public void create(String cohort, String location, String niche, String matrixone, String matrixtwo,
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
    }

    /* Map data from the database to the ClientsStructuralMaps model */
    public static final RowMapper<ClientsStructuralMaps> wuMapper = new RowMapper<ClientsStructuralMaps>() {
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
            return client_structural_maps;
        }
    };
}
