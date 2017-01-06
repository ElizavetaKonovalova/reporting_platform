package application.repositories;

import application.models.ClientsStructuralMaps;
import application.models.Cohorts;
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

    @Autowired
    private final OrganisationRepository organisationRepository = new OrganisationRepository(jdbcTemplate);

    @Autowired
    private final CohortRepository cohortRepository = new CohortRepository(jdbcTemplate);

    private SimpleDateFormat sampledate = new SimpleDateFormat("dd/MM/yyyy", new Locale("en-au", "AU"));
    private String date_modified_formated = sampledate.format(new java.util.Date());




    /* CREATORS */


    /* Create a new table for a Client */
    public String createAClientTable(String client_name) {

        /* Check if a specified client exists */
        Long check_client = this.organisationRepository.checkClientExists(client_name);

        if(check_client != 0) {
            try {
                this.jdbcTemplate.execute("create table " + client_name.toLowerCase() + "_structural_map (\n" +
                        " db_id  bigserial not null,\n cohort INTEGER,\n  date_modified date not null,\n" +
                        " location varchar(50),\n matrix_five varchar(20),\n matrix_four varchar(20),\n" +
                        " matrix_one varchar(20),\n matrix_three varchar(20),\n matrix_two varchar(20),\n" +
                        " niche varchar(100),\n sector varchar(50),\n wu_id int4 not null,\n" +
                        " wu_level_five varchar(100),\n wu_level_four varchar(100),\n wu_level_one varchar(100),\n" +
                        " wu_level_three varchar(100),\n wu_level_two varchar(100),\n wu_level_zero varchar(100),\n" +
                        " wu_name varchar(100) not null,\n client_id bigserial not null,\n primary key (db_id)\n);" +

                        "alter table " + client_name.toLowerCase() + "_structural_map \n add constraint " + client_name.toLowerCase() +
                        "CustomIndex unique (wu_id);" +

                        "alter table " + client_name.toLowerCase() + "_structural_map \n add constraint " + client_name.toLowerCase() +
                        "_clients_fk \n foreign key (client_id) \n references clients;");

                return "Created";

            } catch (Exception e) { return "A table for this client already exists!"; }
        } else { return "There is no such client in the database!";}
    }



    /* Create a Work Unit */
    public String create(String cohort, String location, String niche, String matrixone, String matrixtwo,
                         String matrixthree, String matrixfour, String matrixfive, String wu_name, String wu_id,
                         String sector, String wu_level_five, String wu_level_four, String wu_level_one,
                         String wu_level_three, String wu_level_two, String wu_level_zero, String client_name) throws Exception {

        /* Check if a specified client exists */
        Long check_client = this.organisationRepository.checkClientExists(client_name);

        if(check_client != 0) {

            try {
                this.jdbcTemplate.update("INSERT INTO " + client_name.toLowerCase() + "_structural_map (cohort, location, " +
                                "matrix_five, matrix_four, matrix_one, matrix_three, matrix_two, niche, sector, wu_id, " +
                                "wu_level_five, wu_level_four, wu_level_one, wu_level_three, wu_level_two, wu_level_zero, wu_name, client_id, date_modified)" +
                                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", cohort, location, matrixfive, matrixfour, matrixone, matrixthree,
                        matrixtwo, niche, sector, Integer.parseInt(wu_id), wu_level_five, wu_level_four, wu_level_one,
                        wu_level_three, wu_level_two, wu_level_zero, wu_name, check_client,
                        new Date(sampledate.parse(date_modified_formated).getTime()));

                return "Created";
            } catch (Exception e) { return "There is either duplicate id or no such table in the database!"; }
        } else { return "There is no such client in the database!";}
    }



    /* GETTERS */


    /* Select All Work Units from a Client table */
    public List<ClientsStructuralMaps> getAll(String client_name) {
        return this.jdbcTemplate.query("SELECT * FROM " + client_name.toLowerCase() +"_structural_map", wuMapper);
    }

    /* Select a Work Unit by its ID in the database */
    public List<ClientsStructuralMaps> getWorkUnitByDBID(String id, String client_name) {
        return this.jdbcTemplate.query("SELECT * FROM " + client_name.toLowerCase() +"_structural_map WHERE db_id = ?",
                    wuMapper, Long.parseLong(id));
    }

    /* Select a Work Unit by its Name */
    public List<ClientsStructuralMaps> getClientsStructuralMapsByName(String name, String client_name) {
        String query = "SELECT * FROM " + client_name.toLowerCase() +"_structural_map WHERE wu_name = ?";
        return this.jdbcTemplate.query( query, wuMapper, name);
    }

    /* Select a Work Unit by its Work Unit ID */
    public List<ClientsStructuralMaps> getWorkUnitByWUID(String wuid, String client_name) {
        return this.jdbcTemplate.query( "SELECT * FROM " + client_name.toLowerCase() +"_structural_map WHERE wu_id = ?",
                    wuMapper, Integer.parseInt(wuid));
    }

    /* Select Work Units by Level */
    public List<ClientsStructuralMaps> getClientsStructuralMapsByLevel(String level, String client_name) {
        return this.jdbcTemplate.query( "SELECT * FROM " + client_name.toLowerCase() +"_structural_map WHERE ? IN (wu_level_zero, wu_level_one, " +
                "wu_level_two, wu_level_three, wu_level_four, wu_level_five)", wuMapper, level);
    }

    /* Select Work Units by their Cohort */
    public List<ClientsStructuralMaps> getClientsStructuralMapsByCohort(String wucohort, String client_name) {
        return this.jdbcTemplate.query( "SELECT * FROM " + client_name.toLowerCase() +"_structural_map WHERE cohort = ?", wuMapper, wucohort);
    }

    /* Select Work Units by their Matrix */
    public List<ClientsStructuralMaps> getClientsStructuralMapsByMatrix(String matrix, String client_name) {
        return this.jdbcTemplate.query("SELECT * FROM " + client_name.toLowerCase() +"_structural_map WHERE ? IN (matrix_one, matrix_two," +
                " matrix_three, matrix_four, matrix_five)", wuMapper, matrix);
    }

    /* Select Work Units by their Niche */
    public List<ClientsStructuralMaps> getClientsStructuralMapsByNiche(String niche, String client_name) {
        return this.jdbcTemplate.query("SELECT * FROM " + client_name.toLowerCase() +"_structural_map WHERE niche = ?", wuMapper, niche);
    }

    /* Select Work Units by their Sector */
    public List<ClientsStructuralMaps> getClientsStructuralMapsBySector(String sector, String client_name) {
        return this.jdbcTemplate.query("SELECT * FROM " + client_name.toLowerCase() +"_structural_map WHERE sector = ?", wuMapper, sector);
    }

    /* Select Work Units by their Location */
    public List<ClientsStructuralMaps> getClientsStructuralMapsByLocation(String location, String client_name) {
        return this.jdbcTemplate.query("SELECT * FROM " + client_name.toLowerCase() +"_structural_map WHERE location = ?", wuMapper, location);
    }

    /* Select Work Units by their Client ID */
    public List<ClientsStructuralMaps> getClientsStructuralMapsByClientID(String client_id, String client_name) {
        return this.jdbcTemplate.query("SELECT * FROM " + client_name.toLowerCase() +"_structural_map WHERE client_id = ?",
                wuMapper, Long.parseLong(client_id));
    }


    /* REMOVALS */


    /* Remove Work Units by Name */
    public void removeWUByName(String wu_name, String client_name) {
        this.jdbcTemplate.update("DELETE FROM " + client_name.toLowerCase() +"_structural_map WHERE wu_name = ?", wu_name);
    }

    /* Remove Work Units by Work Unit ID */
    public void removeWUByWUID(String wu_id, String client_name) {
        this.jdbcTemplate.update("DELETE FROM " + client_name.toLowerCase() +"_structural_map WHERE wu_id = ?", Integer.parseInt(wu_id));
    }

    /* Remove Work Units by database ID */
    public void removeWUByDBID(String db_id, String client_name) {
        this.jdbcTemplate.update("DELETE FROM " + client_name.toLowerCase() +"_structural_map WHERE db_id = ?", Long.parseLong(db_id));
    }

    /* Remove Work Units by Client ID */
    public void removeWUByClientID(String client_id, String client_name) {
        this.jdbcTemplate.update("DELETE FROM " + client_name.toLowerCase() +"_structural_map WHERE client_id = ?", Integer.parseInt(client_id));
    }

    /* Remove Work Units by Location */
    public void removeWUByLocation(String location, String client_name) {
        this.jdbcTemplate.update("DELETE FROM " + client_name.toLowerCase() +"_structural_map WHERE location = ?", location);
    }

    /* Remove Work Units by Cohort */
    public void removeWUByCohort(String cohort, String client_name) {
        this.jdbcTemplate.update("DELETE FROM " + client_name.toLowerCase() +"_structural_map WHERE cohort = ?", cohort);
    }

    /* Remove Work Units by Sector */
    public void removeWUBySector(String sector, String client_name) {
        this.jdbcTemplate.update("DELETE FROM " + client_name.toLowerCase() +"_structural_map WHERE sector = ?", sector);
    }

    /* Remove Work Units by Niche */
    public void removeWUByNiche(String niche, String client_name) {
        this.jdbcTemplate.update("DELETE FROM " + client_name.toLowerCase() +"_structural_map WHERE niche = ?", niche);
    }


    /* NULLERS */



    /* Null Niche */
    public void nullNiche(String niche, String client_name) throws Exception {
        this.jdbcTemplate.update("UPDATE " + client_name.toLowerCase() +"_structural_map SET niche = NULL, date_modified = ?  WHERE niche = ?",
                new Date(sampledate.parse(date_modified_formated).getTime()), niche);
    }

    /* Null Matrix One by its Name */
    public void nullMatrixOne(String matrix_one, String client_name) throws Exception {
        this.jdbcTemplate.update("UPDATE " + client_name.toLowerCase() +"_structural_map SET matrix_one = NULL, date_modified = ? WHERE matrix_one = ?",
                new Date(sampledate.parse(date_modified_formated).getTime()), matrix_one);
    }

    /* Null Matrix Two by its Name */
    public void nullMatrixTwo(String matrix_two, String client_name) throws Exception {
        String query = "UPDATE " + client_name.toLowerCase() +"_structural_map SET matrix_two = NULL, date_modified = ? WHERE matrix_two = ?";
        this.jdbcTemplate.update(query, new Date(sampledate.parse(date_modified_formated).getTime()), matrix_two);
    }

    /* Null Matrix Three by its Name */
    public void nullMatrixThree(String matrix, String client_name) throws Exception {
        String query = "UPDATE " + client_name.toLowerCase() +"_structural_map SET matrix_three = NULL, date_modified = ? WHERE matrix_three = ?";
        this.jdbcTemplate.update(query, new Date(sampledate.parse(date_modified_formated).getTime()), matrix);
    }

    /* Null Matrix Four by its Name */
    public void nullMatrixFour(String matrix, String client_name) throws Exception {
        String query = "UPDATE " + client_name.toLowerCase() +"_structural_map SET matrix_four = NULL, date_modified = ? WHERE matrix_four = ?";
        this.jdbcTemplate.update(query, new Date(sampledate.parse(date_modified_formated).getTime()), matrix);
    }

    /* Null Matrix Five by its Name */
    public void nullMatrixFive(String matrix, String client_name) throws Exception {
        String query = "UPDATE " + client_name.toLowerCase() +"_structural_map SET matrix_five = NULL, date_modified = ? WHERE matrix_five = ?";
        this.jdbcTemplate.update(query, new Date(sampledate.parse(date_modified_formated).getTime()), matrix);
    }

    /* Null Level Zero by its Name */
    public void nullLevelZero(String level, String client_name) throws Exception {
        String query = "UPDATE " + client_name.toLowerCase() +"_structural_map SET wu_level_zero = NULL, date_modified = ? WHERE wu_level_zero = ?";
        this.jdbcTemplate.update(query, new Date(sampledate.parse(date_modified_formated).getTime()), level);
    }

    /* Null Level One by its Name */
    public void nullLevelOne(String level, String client_name) throws Exception {
        String query = "UPDATE " + client_name.toLowerCase() +"_structural_map SET wu_level_one = NULL, date_modified = ? WHERE wu_level_one = ?";
        this.jdbcTemplate.update(query, new Date(sampledate.parse(date_modified_formated).getTime()), level);
    }

    /* Null Level Two by its Name */
    public void nullLevelTwo(String level, String client_name) throws Exception {
        String query = "UPDATE " + client_name.toLowerCase() +"_structural_map SET wu_level_two = NULL, date_modified = ? WHERE wu_level_two = ?";
        this.jdbcTemplate.update(query, new Date(sampledate.parse(date_modified_formated).getTime()), level);
    }

    /* Null Level Three by its Name */
    public void nullLevelThree(String level, String client_name) throws Exception {
        String query = "UPDATE " + client_name.toLowerCase() +"_structural_map SET wu_level_three = NULL, date_modified = ? WHERE wu_level_three = ?";
        this.jdbcTemplate.update(query, new Date(sampledate.parse(date_modified_formated).getTime()), level);
    }

    /* Null Level Four by its Name */
    public void nullLevelFour(String level, String client_name) throws Exception {
        String query = "UPDATE " + client_name.toLowerCase() +"_structural_map SET wu_level_four = NULL, date_modified = ? WHERE wu_level_four= ?";
        this.jdbcTemplate.update(query, new Date(sampledate.parse(date_modified_formated).getTime()), level);
    }

    /* Null Level Five by its Name */
    public void nullLevelFive(String level, String client_name) throws Exception {
        String query = "UPDATE " + client_name.toLowerCase() +"_structural_map SET wu_level_five = NULL, date_modified = ? WHERE wu_level_five = ?";
        this.jdbcTemplate.update(query, new Date(sampledate.parse(date_modified_formated).getTime()), level);
    }

    /* Null Cohort */
    public void nullCohort(String cohort, String client_name) throws Exception {
        String query = "UPDATE " + client_name.toLowerCase() +"_structural_map SET cohort = NULL, date_modified = ? WHERE cohort = ?";
        this.jdbcTemplate.update(query, new Date(sampledate.parse(date_modified_formated).getTime()), cohort);
    }

    /* Null Sector */
    public void nullSector(String sector, String client_name) throws Exception {
        String query = "UPDATE " + client_name.toLowerCase() +"_structural_map SET sector = NULL, date_modified = ? WHERE sector = ?";
        this.jdbcTemplate.update(query, new Date(sampledate.parse(date_modified_formated).getTime()), sector);
    }

    /* Null Location */
    public void nullLocation(String location, String client_name) throws Exception {
        String query = "UPDATE " + client_name.toLowerCase() + "_structural_map SET location = NULL, date_modified = ? WHERE location = ?";
        this.jdbcTemplate.update(query, new Date(sampledate.parse(date_modified_formated).getTime()), location);
    }


    /* UPDATERS */


    /* Update a Cohort by a database ID and a new Cohort Name */
    public String updateCohortByDBID(String db_id, String new_cohort_name, String client_name) throws Exception {
        List<Cohorts> cohorts = this.cohortRepository.getCohortByName(new_cohort_name);
        if(cohorts.size() != 0) {
            this.jdbcTemplate.update("UPDATE " + client_name.toLowerCase() + "_structural_map SET cohort = ?, date_modified = ? WHERE db_id = ?",
                    cohorts.get(0).getCOHORT_ID(), new Date(sampledate.parse(date_modified_formated).getTime()), Long.parseLong(db_id));
            return "Updated";
        } else { return "Could not update the cohort because there is no such cohort in the database!"; }
    }

    /* Update a Cohort by an old Cohort Name and a new Cohort Name */
    public String updateCohortByCohortName(String old_cohort_name, String new_cohort_name, String client_name) throws Exception {
        List<Cohorts> cohorts_check_old = this.cohortRepository.getCohortByName(old_cohort_name);
        List<Cohorts> cohorts_check_new = this.cohortRepository.getCohortByName(new_cohort_name);

        if(cohorts_check_new.size() !=0 && cohorts_check_old.size() != 0) {
            this.jdbcTemplate.update("UPDATE " + client_name.toLowerCase() + "" +
                    "_structural_map SET cohort = ?, date_modified = ? WHERE cohort = ?", Integer.parseInt(new_cohort_name),
                    new Date(sampledate.parse(date_modified_formated).getTime()), Integer.parseInt(old_cohort_name));
            return "Updated";
        } else { return "Could not update the cohort because there is no such cohort in the database!"; }
    }

    /* Update a Location by a database ID */
    public String updateLocationByDBID(String db_id, String location, String client_name) throws Exception {
        try {
            this.jdbcTemplate.update("UPDATE " + client_name.toLowerCase() + "_structural_map SET location = ?, date_modified = ? WHERE db_id = ?",
                    location, new Date(sampledate.parse(date_modified_formated).getTime()), Long.parseLong(db_id));
            return "Updated";
        } catch (Exception e) { return "Could not update the value!"; }
    }

    /* Update Locations by an old Location */
    public String updateLocationByLocation(String old_location, String new_location, String client_name) throws Exception {
        try {
            this.jdbcTemplate.update("UPDATE " + client_name.toLowerCase() + "_structural_map SET location = ?, date_modified = ? WHERE db_id = ?",
                    new_location, new Date(sampledate.parse(date_modified_formated).getTime()), old_location);
            return "Updated";
        } catch (Exception e) { return "Could not updated the value!"; }
    }

    /* Update a Matrix One by a database ID and a new Matrix Name */
    public String updateMtxOneByDBID(String db_id, String new_matrix, String client_name) throws Exception {
        try {
            this.jdbcTemplate.update("UPDATE " + client_name.toLowerCase() + "_structural_map SET matrix_one = ?, date_modified = ? WHERE db_id = ?",
                    new_matrix, new Date(sampledate.parse(date_modified_formated).getTime()), Long.parseLong(db_id));
            return "Updated";
        } catch (Exception e) { return "Could not updated the value!"; }

    }

    /* Update a Matrix Two by a database ID and a new Matrix Name */
    public String updateMtxTwoByDBID(String db_id, String new_matrix, String client_name) throws Exception {
        try {
            this.jdbcTemplate.update("UPDATE " + client_name.toLowerCase() + "_structural_map SET matrix_two = ?, date_modified = ? WHERE db_id = ?",
                    new_matrix, new Date(sampledate.parse(date_modified_formated).getTime()), Long.parseLong(db_id));
            return "Updated";
        } catch (Exception e) { return "Could not updated the value!"; }
    }

    /* Update a Matrix Three by a database ID and a new Matrix Name */
    public String updateMtxThreeByDBID(String db_id, String new_matrix, String client_name) throws Exception {
        try {
            this.jdbcTemplate.update("UPDATE " + client_name.toLowerCase() + "_structural_map SET matrix_three = ?, date_modified = ? WHERE db_id = ?",
                    new_matrix, new Date(sampledate.parse(date_modified_formated).getTime()), Long.parseLong(db_id));
            return "Updated";
        } catch (Exception e) { return "Could not updated the value!"; }
    }

    /* Update a Matrix Four by a database ID and a new Matrix Name */
    public String updateMtxFourByDBID(String db_id, String new_matrix, String client_name) throws Exception {
        try {
            this.jdbcTemplate.update("UPDATE " + client_name.toLowerCase() + "_structural_map SET matrix_four = ?, date_modified = ? WHERE db_id = ?",
                    new_matrix, new Date(sampledate.parse(date_modified_formated).getTime()), Long.parseLong(db_id));
            return "Updated";
        } catch (Exception e) { return "Could not updated the value!"; }
    }

    /* Update a Matrix Five by a database ID and a new Matrix Name */
    public String updateMtxFiveByDBID(String db_id, String new_matrix, String client_name) throws Exception {
        try {
            this.jdbcTemplate.update("UPDATE " + client_name.toLowerCase() + "_structural_map SET matrix_five = ?, date_modified = ? WHERE db_id = ?",
                    new_matrix, new Date(sampledate.parse(date_modified_formated).getTime()), Long.parseLong(db_id));
            return "Updated";
        } catch (Exception e) { return "Could not updated the value!"; }
    }

    /* Update a Niche by a database ID */
    public String updateNicheByDBID(String db_id, String new_niche, String client_name) throws Exception {
        try {
            this.jdbcTemplate.update("UPDATE " + client_name.toLowerCase() + "_structural_map SET niche = ?, date_modified = ? WHERE db_id = ?",
                    new_niche, new Date(sampledate.parse(date_modified_formated).getTime()), Long.parseLong(db_id));
            return "Updated";
        } catch (Exception e) { return "Could not updated the value!"; }
    }

    /* Update a Sector by a database ID */
    public String updateSectorByDBID(String db_id, String new_sector, String client_name) throws Exception {
        try {
            this.jdbcTemplate.update("UPDATE " + client_name.toLowerCase() + "_structural_map SET matrix_one = ?, date_modified = ? WHERE db_id = ?",
                    new_sector, new Date(sampledate.parse(date_modified_formated).getTime()), Long.parseLong(db_id));
            return "Updated";
        } catch (Exception e) { return "Could not updated the value!"; }
    }

    /* Update a Client ID by a database ID and a new Client Name */
    public String updateClientIDByNameDBID(String db_id, String new_client, String client_name) throws Exception {
        try {
            this.jdbcTemplate.update("UPDATE " + client_name.toLowerCase() + "_structural_map SET matrix_one = ?, date_modified = ? WHERE db_id = ?",
                    new_client, new Date(sampledate.parse(date_modified_formated).getTime()), Long.parseLong(db_id));
            return "Updated";
        } catch (Exception e) { return "Could not updated the value!"; }
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
            client_structural_maps.setWU_NAME(rs.getString("wu_name"));
            client_structural_maps.setCLIENT(rs.getLong("client_id"));
            client_structural_maps.setDATE_MODIFIED(rs.getDate("date_modified"));
            return client_structural_maps;
        }
    };
}
