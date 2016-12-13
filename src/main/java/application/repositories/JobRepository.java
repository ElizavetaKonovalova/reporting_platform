package application.repositories;
import application.models.Jobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class JobRepository  {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /*Select a job by Job Code */
    public Jobs getJobByCode(String jobcode) {
        try {
            String query = "SELECT * FROM jobs WHERE job_code = ?";
            return this.jdbcTemplate.queryForObject( query, jobMapper, jobcode.toLowerCase());
        } catch (Exception e) { return new Jobs(); }
    }

    /* Select a job by ID in the database */
    public Jobs getJobByID(Long jobid) {
        try {
            String query = "SELECT * FROM jobs WHERE job_id= ?";
            return this.jdbcTemplate.queryForObject( query, jobMapper, jobid);
        } catch (Exception e) { return new Jobs(); }
    }

    /* Select all jobs with a specified name */
    public List<Jobs> getJobsByName(String jobname) {
        String query = "SELECT * FROM jobs WHERE job_name = ?";
        return this.jdbcTemplate.query( query, jobMapper, jobname);
    }

    /* Select all jobs with a specified client id */
    public List<Jobs> getJobsByClientID(Long clientid) {
        String query = "SELECT * FROM jobs WHERE client_id = ?";
        return this.jdbcTemplate.query( query, jobMapper, clientid);
    }

    /* Create a new job in the database */
    public void createJob(String jobcode, String jobname, String clientid, String deliverydate, String deliverytype, String censusstart,
                          String censusend, String presentationdate, String responserate, String loggedin, String samplesize, Boolean status,
                          String surveysubtype) throws Exception {

        /* Make a default date format */
        SimpleDateFormat sampledate = new SimpleDateFormat("dd/MM/yyyy", new Locale("en-au", "AU"));
        sampledate.setTimeZone(TimeZone.getTimeZone("AEST"));

        Integer loggedinint, samplesizeint, subtypeidint;
        Short responserateshort;

        Long clientidlong = Long.parseLong(clientid);
        jobcode = jobcode.toLowerCase();

        if(status == null) { status = false; }
        if(loggedin.isEmpty()) {  loggedinint = 0; } else { loggedinint = Integer.parseInt(loggedin); }
        if(responserate.isEmpty()) { responserateshort = 0; } else { responserateshort = Short.parseShort(responserate); }
        if(samplesize.isEmpty()) { samplesizeint = 0; } else { samplesizeint = Integer.parseInt(samplesize); }
        if(surveysubtype.isEmpty()) { subtypeidint = 0; } else { subtypeidint = Integer.parseInt(surveysubtype); }
        if(deliverydate.isEmpty()) { deliverydate = sampledate.format(new java.util.Date()); }
        else { deliverydate = sampledate.format(sampledate.parse(deliverydate)); }
        if(censusstart.isEmpty()) { censusstart = sampledate.format(new java.util.Date()); }
        else { censusstart = sampledate.format(sampledate.parse(censusstart)); }
        if(censusend.isEmpty()) { censusend = sampledate.format(new java.util.Date()); }
        else { censusend = sampledate.format(sampledate.parse(censusend)); }
        if(presentationdate.isEmpty()) { presentationdate = sampledate.format(new java.util.Date()); }
        else { presentationdate = sampledate.format(sampledate.parse(presentationdate)); }

        /* Parse dates to the SQL Date format*/
        java.util.Date csdate = sampledate.parse(censusstart);
        Date censusstartdate = new Date(csdate.getTime());
        java.util.Date cedate = sampledate.parse(censusend);
        Date censusenddate = new Date(cedate.getTime());
        java.util.Date ddate = sampledate.parse(deliverydate);
        Date deliverdate = new Date(ddate.getTime());
        java.util.Date pdate = sampledate.parse(presentationdate);
        Date presentdate = new Date(pdate.getTime());

        String query = "INSERT INTO jobs (job_code, job_name, client_id, delivery_date, delivery_type, census_start, census_end, " +
                "presentation_date, response_rate, logged_in, sample_size, status, surveytype_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        this.jdbcTemplate.update(query, jobcode, jobname, clientidlong,
                deliverdate, deliverytype, censusstartdate, censusenddate, presentdate, responserateshort, loggedinint, samplesizeint, status,
                subtypeidint);
    }

    /*Delete a job by ID in the database*/
    public void removeJobByID(Long jobid) { this.jdbcTemplate.update("DELETE FROM jobs WHERE job_id = " + jobid);
    }

    /*Delete a job by ID in the database*/
    public void removeJobByCode(String jobcode) {
        String query = "DELETE FROM jobs WHERE job_code = ?";
        this.jdbcTemplate.update( query, jobcode);
    }

    /* Map data from the database to the Jobs model */
    private static final RowMapper<Jobs> jobMapper = new RowMapper<Jobs>() {
        public Jobs mapRow(ResultSet rs, int rowNum) throws SQLException {
                Jobs job = new Jobs();
                job.setCENSUS_START(rs.getDate("census_start"));
                job.setCENSUS_END(rs.getDate("census_end"));
                job.setDELIVERY_DATE(rs.getDate("delivery_date"));
                job.setPRESENTATION_DATE(rs.getDate("presentation_date"));
                job.setJOB_ID(rs.getLong("job_id"));
                job.setSTATUS(rs.getBoolean("status"));
                job.setSAMPLE_SIZE(rs.getInt("sample_size"));
                job.setRESPONSE_RATE(rs.getShort("response_rate"));
                job.setLOGGED_IN(rs.getInt("logged_in"));
                job.setDELIVERY_TYPE(rs.getString("delivery_type"));
                job.setJOB_CODE(rs.getString("job_code"));
                job.setTARGET_RESPONSE_RATE(rs.getShort("target_response_rate"));
                job.setJOB_NAME(rs.getString("job_name"));
//                job.setSURVEY_SUBTYPEID(rs.getInt("survey_subtypeid"));
                return job;
        }
    };
}
