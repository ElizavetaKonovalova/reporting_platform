package application.repositories;
import application.models.Jobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekonovalova on 12/5/2016.
 */
@Repository
public class JobRepository  {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /*Select a job by Job Code */
    public Jobs getJobByCode(String jobcode){
        String query = "SELECT * FROM jobs WHERE jobcode = ?";
        try { return this.jdbcTemplate.queryForObject( query, jobMapper, jobcode); }
        catch (Exception e) { return new Jobs(); }
    }

    /* Select a job by ID in the database */
    public Jobs getJobByID(Long jobid){
        String query = "SELECT * FROM jobs WHERE id = ?";
        try { return this.jdbcTemplate.queryForObject( query, jobMapper, jobid); }
        catch (Exception e) { return new Jobs(); }
    }

    /* Select all jobs with a specified name */
    public List<Jobs> getJobsByName(String jobname){
        String query = "SELECT * FROM jobs WHERE jobname= ?";
        try { return this.jdbcTemplate.query( query, jobMapper, jobname);}
        catch(Exception e) { return new ArrayList<>();}
    }

    /* Select all jobs with a specified client id */
    public List<Jobs> getJobsByClientID(Long clientid){
        String query = "SELECT * FROM jobs WHERE clientid = ?";
        try { return this.jdbcTemplate.query( query, jobMapper, clientid);}
        catch(Exception e) { return new ArrayList<>();}
    }

    /* Create a new job in the database */
    public void createJob(String jobcode, String jobname, Long clientid, Date deliverydate, String deliverytype, Date censusstart,
                          Date censusend, Date presentationdate, Short responserate, Integer loggedin, Integer samplesize, Boolean status,
                          Integer surveysubtype) throws SQLException {

        String query = "INSERT INTO jobs (jobcode, jobname, clientid, deliverydate, deliverytype, censusstart, censusend, " +
                "presentationdate, responserate, loggedin,samplesize, status, surveysubtypeid) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        this.jdbcTemplate.update(query, jobcode, jobname, clientid,
                deliverydate, deliverytype, censusstart, censusend, presentationdate, responserate, loggedin, samplesize, status,
                surveysubtype);
    }

    /*Delete a job by ID in the database*/
    public void removeJobByID(Long jobid) {
        this.jdbcTemplate.update("DELETE FROM jobs WHERE id = " + jobid);
    }

    /*Delete a job by ID in the database*/
    public void removeJobByCode(String jobcode) {
        String query = "DELETE FROM jobs WHERE jobcode = ?";
        this.jdbcTemplate.update( query, jobcode);
    }

    /* Map data from the database to the Jobs model */
    private static final RowMapper<Jobs> jobMapper = new RowMapper<Jobs>() {
        public Jobs mapRow(ResultSet rs, int rowNum) throws SQLException {
                Jobs job = new Jobs();
                job.setCENSUSSTART(rs.getDate("censusstart"));
                job.setCENSUSEND(rs.getDate("censusend"));
                job.setDELIVERYDATE(rs.getDate("deliverydate"));
                job.setPRESENTATIONDATE(rs.getDate("presentationdate"));
                job.setID(rs.getLong("id"));
                job.setSTATUS(rs.getBoolean("status"));
                job.setSAMPLESIZE(rs.getInt("samplesize"));
                job.setRESPONSERATE(rs.getShort("responserate"));
                job.setLOGGEDIN(rs.getInt("loggedin"));
                job.setDELIVERYTYPE(rs.getString("deliverytype"));
                job.setCLIENTID(rs.getLong("clientid"));
                job.setJOBCODE(rs.getString("jobcode"));
                job.setJOBNAME(rs.getString("jobname"));
                job.setSURVEYSUBTYPEID(rs.getInt("surveysubtypeid"));
                return job;
        }
    };
}
