package application.repositories;

import application.models.JobStructuralMaps;
import application.models.Jobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JobStructuralMapRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public JobStructuralMapRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private final JobRepository jobRepository = new JobRepository(jdbcTemplate);

    @Autowired
    private final OrganisationRepository organisationRepository = new OrganisationRepository(jdbcTemplate);

    /* GETTERS */

    /* Select a work unit by its ID in the database */
    public List<JobStructuralMaps> getWorkUnitByID(Long id, String job_code) {
        return this.jdbcTemplate.query( "SELECT * FROM "+ job_code.toUpperCase() + "_structural_maps WHERE db_id = ?", wuMapper, id);
    }

    /* Select a work unit by its Name */
    public List<JobStructuralMaps> getJobStructuralMapsByName(String name, String job_code) {
        return this.jdbcTemplate.query( "SELECT * FROM "+ job_code.toUpperCase() + "_structural_maps WHERE wu_name = ?", wuMapper, name);
    }

    /* Select a work unit by its Work Unit Code */
    public List<JobStructuralMaps> getWorkUnitByWUCode(String wu_code, String job_code) {
        return this.jdbcTemplate.query( "SELECT * FROM "+ job_code.toUpperCase() + "_structural_maps WHERE wu_code = ?",
                    wuMapper, Integer.parseInt(wu_code));
    }

    /* Select a work unit by its Job ID */
    public List<JobStructuralMaps> getWUByJobID(String job_id, String job_code) {
        return this.jdbcTemplate.query( "SELECT * FROM "+ job_code.toUpperCase() + "_structural_maps WHERE job_id = ?",
                    wuMapper, Long.parseLong(job_id));
    }

    /* Select a work unit by its Work Unit ID */
    public List<JobStructuralMaps> getWorkUnitByWUID(String wuid, String job_code) {
        return this.jdbcTemplate.query( "SELECT * FROM "+ job_code.toUpperCase() + "_structural_maps WHERE wu_id = ?",
                    wuMapper, Integer.parseInt(wuid));
    }

    /* Select work units by level */
    public List<JobStructuralMaps> getJobStructuralMapsByLevel(String level, String job_code) {
        return this.jdbcTemplate.query( "SELECT * FROM "+ job_code.toUpperCase() + "_structural_maps WHERE ? IN (wu_level_one, wu_level_zero)",
                wuMapper, level);
    }

    /* Select work units by their Cohort */
    public List<JobStructuralMaps> getJobStructuralMapsByCohort(String wucohort, String job_code) {
        return this.jdbcTemplate.query("SELECT * FROM "+ job_code.toUpperCase() + "_structural_maps WHERE cohort = ?", wuMapper, wucohort);
    }

    /* Select work units by their Denominator */
    public List<JobStructuralMaps> getJobByDenominator(String denominator, String job_code) {
        return this.jdbcTemplate.query("SELECT * FROM "+ job_code.toUpperCase() + "_structural_maps WHERE denominator = ?",
                wuMapper, Integer.parseInt(denominator));
    }


    /* REMOVALS */



    /* Remove a Work Unit by its Work Unit Code */
    public void removeByWUCode(String wu_code, String job_code) {
        this.jdbcTemplate.update("DELETE FROM "+ job_code.toUpperCase() + "_structural_maps WHERE wu_code = ?",
                Long.parseLong(wu_code));
    }

    /* Remove a Work Unit by its Work Unit ID */
    public void removeByWUID(String wu_id, String job_code) {
        this.jdbcTemplate.update("DELETE FROM "+ job_code.toUpperCase() + "_structural_maps WHERE wu_id = ?",
                Integer.parseInt(wu_id));
    }

    /* Remove a Work Unit by its database ID */
    public void removeByDBID(String db_id, String job_code) {
        this.jdbcTemplate.update("DELETE FROM "+ job_code.toUpperCase() + "_structural_maps WHERE db_id = ?", Long.parseLong(db_id));
    }

    /* Remove a Work Unit by its cohort */
    public void removeByCohort(String cohort, String job_code) {
        String query = "DELETE FROM "+ job_code.toUpperCase() + "_structural_maps WHERE cohort = ?";
        this.jdbcTemplate.update(query, cohort);
    }

    /* Remove Work Units by their Job ID */
    public void removeByJobID(String job_id, String job_code) {
        this.jdbcTemplate.update("DELETE FROM "+ job_code.toUpperCase() + "_structural_maps WHERE job_id = ?", Long.parseLong(job_id));
    }

    /* Remove Work Units by level one */
    public void removeByLevelOne(String wu_lvl_one, String job_code) {
        this.jdbcTemplate.update("DELETE FROM "+ job_code.toUpperCase() + "_structural_maps WHERE wu_level_one = ?", wu_lvl_one);
    }

    /* Remove Work Units by level zero */
    public void removeByLevelZero(String wu_lvl_zero, String job_code) {
        this.jdbcTemplate.update("DELETE FROM "+ job_code.toUpperCase() + "_structural_maps WHERE wu_level_zero = ?", wu_lvl_zero);
    }

    /* Remove Work Units by their Denominator */
    public void removeByDenominator(String denominator, String job_code) {
        this.jdbcTemplate.update("DELETE FROM "+ job_code.toUpperCase() + "_structural_maps WHERE denominator = ?", Integer.parseInt(denominator));
    }


    /* NULLERS */


    /* UPDATERS */

    /* Set a Work Unit Name to null by its Work Unit Code */
    public void updateWUNameByWUCode(String wu_code, String wu_name, String job_code) {
        this.jdbcTemplate.update("UPDATE "+ job_code.toUpperCase() + "_structural_maps SET wu_name = ? WHERE wu_code = ?", wu_name, Long.parseLong(wu_code));
    }



    /* CREATORS */

    /* Create a new Structural Map table for a PARTICULAR Job */
    public String createTable(String job_code, String client_name) {

        if(this.organisationRepository.getOrgByClientName(client_name).size() != 0
                && this.jobRepository.getJobByCode(job_code).size() != 0) {
            try {
                this.jdbcTemplate.execute("create table "+ job_code.toUpperCase() + "_structural_maps (\n db_id  bigserial not null," +
                        "\n cohort varchar(100),\n date_modified date not null,\n denominator int4,\n wu_code int8,\n wu_id bigserial not null,\n" +
                        " wu_level_one varchar(255),\n wu_level_zero varchar(255),\n wu_name varchar(100) not null,\n participant_id bigserial,\n" +
                        " primary key (db_id)\n); " +

                    /* Creation of a unique constraint */
                        "alter table " + job_code.toUpperCase() + "_structural_maps \n add constraint " + job_code.toUpperCase() +
                        "_wu_code unique (wu_code);" +

                    /* Creation of Indexes */
                        "alter table " + job_code.toUpperCase() + "_structural_maps \n add constraint "+ job_code.toUpperCase()+
                        "StrMapIndex unique (wu_id, wu_code);" +

                    /* Foreign Key relationship with the Participants Table by a Work Unit Code */
                        "alter table participants \n add constraint " + job_code.toUpperCase() + "_participants_wu_code_fk \n foreign key (wu_code) \n"
                        + "references " + job_code.toUpperCase() + "_structural_maps (wu_code);" +

                    /* Foreign Key relationship with the Participants Table by a Participant ID */
                        "alter table " + job_code.toUpperCase() + "_structural_maps \n add constraint " + job_code.toUpperCase()
                        + "_participant_id_fk \n foreign key (participant_id) \n references participants;" +

                    /* Foreign Key relationship with a Client Table by a Work Unit ID */
                        "alter table " + job_code.toUpperCase() + "_structural_maps \n add constraint " + client_name.toLowerCase() +
                        "_" + job_code.toUpperCase() + "_structural_map_fk \n foreign key (wu_id) \n references " + client_name.toLowerCase()
                        + "_structural_map (wu_id);");

                return "Created";
            } catch (Exception e) { return "Oops, could not create a new table"; }
        } else { return "There is no job with this code or a client with this name"; }
    }

    /* Create a work unit */
    public void create(String cohort, String denominator, String job_id, String level_zero, String level_one,
                       String name, String workunitcode, String workunitid, String job_code) throws Exception {

        this.jdbcTemplate.update("INSERT INTO "+ job_code.toUpperCase() + "_structural_maps (cohort, denominator, job_id, wu_code, wu_level_one, " +
                        "wu_level_zero, wu_name, wu_id) VALUES (?,?,?,?,?,?,?,?)", cohort, Integer.parseInt(denominator), Long.parseLong(job_id),
                Integer.parseInt(workunitcode), level_one, level_zero, name, Integer.parseInt(workunitid));
    }


    /* HELPERS */


    /* Check if a work unit code exists in a parsed job. */
    public Long isWUExists(String wu_code, String job_code) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM "+ job_code.toUpperCase() + "_structural_maps WHERE wu_code = ?",
                    wuMapper, Long.parseLong(wu_code)).getWU_CODE();
        } catch (Exception e) { return 0L; }
    }

    /* Map data from the database to the JobStructuralMaps model */
    private static final RowMapper<JobStructuralMaps> wuMapper = new RowMapper<JobStructuralMaps>() {
        public JobStructuralMaps mapRow(ResultSet rs, int rowNum) throws SQLException {
            JobStructuralMaps jobStructuralMaps = new JobStructuralMaps();
            jobStructuralMaps.setDB_ID(rs.getLong("db_id"));
            jobStructuralMaps.setCOHORT(rs.getString("cohort"));
            jobStructuralMaps.setDENOMINATOR(rs.getInt("denominator"));
            jobStructuralMaps.setWU_LEVEL_ONE(rs.getString("wu_level_one"));
            jobStructuralMaps.setWU_LEVEL_ZERO(rs.getString("wu_level_zero"));
            jobStructuralMaps.setNAME(rs.getString("wu_name"));
            jobStructuralMaps.setWU_CODE(rs.getLong("wu_code"));
            jobStructuralMaps.setWU_ID(rs.getLong("wu_id"));
            jobStructuralMaps.setDATE_MODIFIED(rs.getDate("date_modified"));
            jobStructuralMaps.setPARTICIPANT_ID(rs.getLong("participant_id"));
            return jobStructuralMaps;
        }
    };
}
