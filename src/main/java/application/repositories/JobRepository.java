package application.repositories;

import application.models.Jobs;
import application.models.Organisations;
import application.models.SurveyTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class JobRepository  {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private OrganisationRepository organisationRepository = new OrganisationRepository();
    private SurveyTypeRepository surveyTypeRepository;
    private SimpleDateFormat sampledate = new SimpleDateFormat("dd/MM/yyyy", new Locale("en-au", "AU"));


    /* GETTERS */


    /*Select a job by Job Code */
    public List<Jobs> getJobByCode(String job_code) {
        return this.jdbcTemplate.query( "SELECT * FROM jobs WHERE job_code = ?", jobMapper, job_code.toUpperCase());
    }

    /* Select a job by a Job ID */
    public List<Jobs> getJobByID(String job_id) {
        return this.jdbcTemplate.query( "SELECT * FROM jobs WHERE job_id= ?", jobMapper, Integer.parseInt(job_id));
    }

    /* Select all jobs with a Job Name */
    public List<Jobs> getJobsByName(String job_name) {
        return this.jdbcTemplate.query( "SELECT * FROM jobs WHERE job_name = ?", jobMapper, job_name);
    }

    /* Select all jobs with a specified Client ID */
    public List<Jobs> getJobsByClientID(String client_id) {
        return this.jdbcTemplate.query( "SELECT * FROM jobs WHERE client_id = ?", jobMapper, Long.parseLong(client_id));
    }

    /* Select all jobs with a specified Client Name */
    public List<Jobs> getJobsByClientName(String client_name) {
        Long client_id = this.organisationRepository.getOrgByClientName(client_name).get(0).getCLIENT_ID();
        if(!client_id.toString().equals("0")) {
            return this.jdbcTemplate.query( "SELECT * FROM jobs WHERE client_id = ?", jobMapper, client_id);
        } else { return new ArrayList<>(); }
    }

    /* Select all jobs with a Census Start Date */
    public List<Jobs> getJobsByCensusStart(String census_start) throws Exception {
        return this.dateSelector("SELECT * FROM jobs WHERE census_start = ?", census_start,"");
    }

    /* Select all jobs with a Census End Date */
    public List<Jobs> getJobsByCensusEnd(String census_end) throws Exception {
        return this.dateSelector("SELECT * FROM jobs WHERE census_end = ?", census_end,"");
    }

    /* Select all jobs with a Delivery Date */
    public List<Jobs> getJobsByDeliveryDate(String delivery_date) throws Exception {
        return this.dateSelector("SELECT * FROM jobs WHERE delivery_date = ?", delivery_date,"");
    }

    /* Select all jobs with a Presentation Date */
    public List<Jobs> getJobsByPresentationDate(String presentation_date) throws Exception {
        return this.dateSelector("SELECT * FROM jobs WHERE presentation_date = ?", presentation_date,"");
    }

    /* Select all jobs with a Sample Numbers */
    public List<Jobs> getJobsBySample(String sample) {
        return this.jdbcTemplate.query("SELECT * FROM jobs WHERE sample_size = ?", jobMapper, Integer.parseInt(sample));
    }

    /* Select all jobs with a Status */
    public List<Jobs> getJobsByStatus (String status) {
        return this.jdbcTemplate.query( "SELECT * FROM jobs WHERE status = ?", jobMapper, status.charAt(0));
    }

    /* Select all jobs with a Response Rate */
    public List<Jobs> getJobsByResponseRate(String response_rate) {
        return this.jdbcTemplate.query( "SELECT * FROM jobs WHERE response_rate = ?", jobMapper, Short.parseShort(response_rate));
    }

    /* Select all jobs with a Logged In number */
    public List<Jobs> getJobsByLoggedIn(String logged_in) {
        return this.jdbcTemplate.query( "SELECT * FROM jobs WHERE logged_in = ?", jobMapper, Integer.parseInt(logged_in));
    }

    /* Select all jobs with a Target Response Rate */
    public List<Jobs> getJobsByTargetRR(String target_response_rate) {
        return this.jdbcTemplate.query( "SELECT * FROM jobs WHERE target_response_rate = ?", jobMapper, Short.parseShort(target_response_rate));
    }

    /* Select all jobs with a Survey Type ID */
    public List<Jobs> getJobsBySurveyTypeID(String type_id) {
        return this.jdbcTemplate.query( "SELECT * FROM jobs WHERE surveytype_id = ?", jobMapper, Integer.parseInt(type_id));
    }

    /* Select all jobs with a Survey Type Name */
    public List<Jobs> getJobsBySurveyTypeName(String type_name) {
        this.surveyTypeRepository = new SurveyTypeRepository();
        SurveyTypes surveyTypes = this.surveyTypeRepository.surveyTypeExist(type_name);
        if(surveyTypes.getTYPE_NAME() != null) {
            return this.jdbcTemplate.query("SELECT * FROM jobs WHERE surveytype_id = ?", jobMapper, surveyTypes.getSURVEYTYPE_ID());
        } else { return new ArrayList<>(); }
    }

    /* Select all jobs with a specific Delivery Type */
    public List<Jobs> getJobsByDeliveryType(String delivery_type) {
        return this.jdbcTemplate.query( "SELECT * FROM jobs WHERE delivery_type = ?", jobMapper, delivery_type);
    }

    /* Select all jobs from the database */
    public List<Jobs> getAllJobs(){ return this.jdbcTemplate.query("SELECT * FROM jobs", jobMapper);}


    /* NULLERS */

    /* Set a delivery date to null for a specified job code */
    public void nullDeliveryDate(String job_code) {
        this.jdbcTemplate.update("UPDATE jobs SET delivery_date = NULL WHERE job_code = ?", job_code);
    }

    /* Set a presentation date to null for a specified job code */
    public void nullPresentationDate(String job_code) {
        this.jdbcTemplate.update("UPDATE jobs SET presentation_date = NULL WHERE job_code = ?", job_code);
    }

    /* Set a census start date to null for a specified job code */
    public void nullCensusStart(String job_code) {
        this.jdbcTemplate.update("UPDATE jobs SET census_start = NULL WHERE job_code = ?", job_code);
    }

    /* Set a census end date to null for a specified job code */
    public void nullCensusEnd(String job_code) {
        this.jdbcTemplate.update("UPDATE jobs SET census_end = NULL WHERE job_code = ?", job_code);
    }

    /* Set a delivery type to null for a specified job code */
    public void nullDeliveryType(String job_code) {
        this.jdbcTemplate.update("UPDATE jobs SET delivery_type = NULL WHERE job_code = ?", job_code);
    }

    /* Set logged in number to null for a specified job code */
    public void nullLoggedIn(String job_code) {
        this.jdbcTemplate.update("UPDATE jobs SET logged_in = NULL WHERE job_code = ?", job_code);
    }

    /* Set a Response Rates to null for a specified job code */
    public void nullResponseRates(String job_code) {
        this.jdbcTemplate.update("UPDATE jobs SET response_rate = NULL WHERE job_code = ?", job_code);
    }

    /* Set a sample size to null for a specified job code */
    public void nullSampleSize(String job_code) {
        this.jdbcTemplate.update("UPDATE jobs SET sample_size = NULL WHERE job_code = ?", job_code);
    }


    /* REMOVALS */


    /*Delete jobs by their Client ID */
    public void removeJobByClientID(String client_id) {
        this.jdbcTemplate.update("DELETE FROM jobs WHERE client_id = " + Integer.parseInt(client_id));
    }

    /*Delete jobs by their Survey Type Name */
    public void removeJobBySurveyType(String survey_type) {
        SurveyTypes surveyTypes = this.surveyTypeRepository.surveyTypeExist(survey_type);
        if(surveyTypes.getSUBTYPE_NAME() != null) {
            this.jdbcTemplate.update("DELETE FROM jobs WHERE surveytype_id = " + surveyTypes.getSURVEYTYPE_ID());
        }
    }

    /*Delete a Job by a Survey Type ID */
    public void removeJobBySurveyTypeName(String survey_type_id) {
        this.jdbcTemplate.update("DELETE FROM jobs WHERE surveytype_id = " + Integer.parseInt(survey_type_id));
    }

    /*Delete a Job by ID in the database*/
    public void removeJobByID(String job_id) {
        this.jdbcTemplate.update("DELETE FROM jobs WHERE job_id = " + Long.parseLong(job_id));
    }

    /*Delete a Job by Code in the database */
    public void removeJobByCode(String jobcode) {
        this.jdbcTemplate.update( "DELETE FROM jobs WHERE job_code = ?", jobcode);
    }


    /* UPDATERS */

    /* Update a Job Code with a specified Job Code */
    public String updateJobCode(String old_job_code, String new_job_code) {
        if(isJobExists(old_job_code) != 0) {
            this.jdbcTemplate.update("UPDATE jobs SET job_code = ? WHERE job_code = ?", new_job_code, old_job_code);
            return "Updated";
        } else { return "This Job Code does not exist";}
    }

    /* Update a Job Name with a specified Job Code */
    public String updateJobName(String old_job_code, String name) {
        if(isJobExists(old_job_code) != 0) {
            this.jdbcTemplate.update("UPDATE jobs SET job_name = ? WHERE job_code = ?", name, old_job_code);
            return "Updated";
        } else { return "This Job Code does not exist";}
    }

    /* Update a Census Start Date with a specified Job Code */
    public String updateJobCensusStart(String job_code, String new_census_start) throws Exception {
        if(isJobExists(job_code) != 0) {
            this.dateSelector("UPDATE jobs SET census_start = ? WHERE job_code = ?", new_census_start, job_code);
            return "Updated";
        } else { return "This Job Code does not exist";}
    }

    /* Update a Census End Date with a specified Job Code */
    public String updateJobCensusEnd(String job_code, String new_census_end) throws Exception {
        if(isJobExists(job_code) != 0) {
            this.dateSelector("UPDATE jobs SET census_end = ? WHERE job_code = ?", new_census_end, job_code);
            return "Updated";
        } else { return "This Job Code does not exist";}
    }

    /* Update a Presentation Date with a specified Job Code */
    public String updateJobPresentaiton(String job_code, String new_presentation_date) throws Exception {
        if(isJobExists(job_code) != 0) {
            this.dateSelector("UPDATE jobs SET presentation_date = ? WHERE job_code = ?", new_presentation_date, job_code);
            return "Updated";
        } else { return "This Job Code does not exist";}
    }

    /* Update a Delivery Date with a specified Job Code */
    public String updateJobDelivery(String job_code, String new_delivery_date) throws Exception {
        if(isJobExists(job_code) != 0) {
            this.dateSelector("UPDATE jobs SET delivery_date = ? WHERE job_code = ?", new_delivery_date, job_code);
            return "Updated";
        } else { return "This Job Code does not exist";}
    }

    /* Update a Delivery Type with a specified Job Code */
    public String updateJobDeliveryType(String job_code, String new_delivery_type) {
        if(isJobExists(job_code) != 0) {
            this.jdbcTemplate.update("UPDATE jobs SET delivery_type = ? WHERE job_code = ?", new_delivery_type, job_code);
            return "Updated";
        } else { return "This Job Code does not exist";}
    }

    /* Update a Sample Size with a specified Job Code */
    public String updateJobSampleSize(String job_code, String new_sample_size) {
        if(isJobExists(job_code) != 0) {
            this.jdbcTemplate.update("UPDATE jobs SET sample_size = ? WHERE job_code = ?", Integer.parseInt(new_sample_size), job_code);
            return "Updated";
        } else { return "This Job Code does not exist";}
    }

    /* Update a Logged In number with a specified Job Code */
    public String updateJobLoggedIn(String job_code, String new_logged) {
        if(isJobExists(job_code) != 0) {
            this.jdbcTemplate.update("UPDATE jobs SET logged_in = ? WHERE job_code = ?", Integer.parseInt(new_logged), job_code);
            return "Updated";
        } else { return "This Job Code does not exist";}
    }

    /* Update a Response Rate with a specified Job Code */
    public String updateJobResponseRate(String job_code, String new_response_rate) {
        if(isJobExists(job_code) != 0) {
            this.jdbcTemplate.update("UPDATE jobs SET response_rate = ? WHERE job_code = ?", Short.parseShort(new_response_rate), job_code);
            return "Updated";
        } else { return "This Job Code does not exist";}
    }

    /* Update a Status with a specified Job Code */
    public String updateJobStatus(String job_code, String new_status) {
        if (isJobExists(job_code) != 0) {
            this.jdbcTemplate.update("UPDATE jobs SET status = ? WHERE job_code = ?", new_status, job_code);
            return "Updated";
        } else {
            return "This Job Code does not exist";
        }
    }

    /* Update a Target Response Rate with a specified Job Code */
    public String updateJobTargetResponseRate(String job_code, String new_target_response_rate) {
        if(isJobExists(job_code) != 0) {
            this.jdbcTemplate.update("UPDATE jobs SET target_response_rate = ? WHERE job_code = ?", Short.parseShort(new_target_response_rate), job_code);
            return "Updated";
        } else { return "This Job Code does not exist";}
    }

    /* Update a Client ID with a specified Job Code */
    public String updateJobClientID(String job_code, String client_id) {
        if(isJobExists(job_code) != 0) {
            this.jdbcTemplate.update("UPDATE jobs SET client_id = ? WHERE job_code = ?", Integer.parseInt(client_id), job_code);
            return "Updated";
        } else { return "This Job Code does not exist";}
    }

    /* Update a Survey Type ID with a specified Job Code */
    public String updateJobSurveyTypeID(String job_code, String stype_id) {
        if(isJobExists(job_code) != 0) {
            this.jdbcTemplate.update("UPDATE jobs SET surveytype_id = ? WHERE job_code = ?", Integer.parseInt(stype_id), job_code);
            return "Updated";
        } else { return "This Job Code does not exist";}
    }

    /* Update a Survey Type ID with a specified Job Code and Survey Type Name */
    public String updateJobSurveyTypeIDByName(String job_code, String stype_name) {
        SurveyTypes surveyTypeRepositories = this.surveyTypeRepository.surveyTypeExist(stype_name);
        if(isJobExists(job_code) != 0 && surveyTypeRepositories.getSUBTYPE_NAME() != null) {
            this.jdbcTemplate.update("UPDATE jobs SET surveytype_id = ? WHERE job_code = ?",
                    surveyTypeRepositories.getSURVEYTYPE_ID(), job_code);
            return "Updated";
        } else { return "This Job Code or Survey Type does not exist";}
    }

    /* Update a Client ID with a specified Job Code and Client Name */
    public String updateJobClientIDByName(String job_code, String client_name) {
        Long client_id = this.organisationRepository.checkClientExists(client_name);
        if(isJobExists(job_code) != 0 && client_id != 0) {
            this.jdbcTemplate.update("UPDATE jobs SET client_id = ? WHERE job_code = ?", client_id, job_code);
            return "Updated";
        } else { return "This Job Code or Survey Type does not exist";}
    }



    /* CREATORS */


    /* Create a new job in the database */
    public void createJob(String jobcode, String jobname, String clientid, String deliverydate, String deliverytype, String censusstart,
                          String censusend, String presentationdate, String responserate, String loggedin, String samplesize, String status,
                          String surveysubtype, String target_response_rate) throws Exception {

        /* Make a default date format */
        sampledate.setTimeZone(TimeZone.getTimeZone("AEST"));

        Integer loggedinint, samplesizeint, subtypeidint;
        Short responserateshort, target_response_rate_short;
        jobcode = jobcode.toUpperCase();

        if(status.isEmpty()) { status = "Active"; }
        if(loggedin.isEmpty()) {  loggedinint = 0; } else { loggedinint = Integer.parseInt(loggedin); }
        if(responserate.isEmpty()) { responserateshort = 0; } else { responserateshort = Short.parseShort(responserate); }
        if(target_response_rate.isEmpty()) { target_response_rate_short = 0; }
        else { target_response_rate_short = Short.parseShort(target_response_rate); }
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

        String query = "INSERT INTO jobs (census_end, census_start, delivery_date, delivery_type, " +
                "job_code, job_name, logged_in, presentation_date, response_rate, sample_size, status, " +
                "target_response_rate, client_id, surveytype_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        this.jdbcTemplate.update(query, censusenddate, censusstartdate, deliverdate, deliverytype, jobcode, jobname, loggedinint,
                presentdate, responserateshort, samplesizeint, status.charAt(0), target_response_rate_short, Long.parseLong(clientid), subtypeidint);
    }


    /* HELPERS */

    /* Selects parsed dates from the Jobs database */
    private List<Jobs> dateSelector(String query, String date, String parameter) throws Exception {
        sampledate.setTimeZone(TimeZone.getTimeZone("AEST"));
        String date_modified_formated = sampledate.format(date);
        if(parameter.isEmpty()) {
            return this.jdbcTemplate.query( query, jobMapper, new Date(sampledate.parse(date_modified_formated).getTime()));
        } else {
            return this.jdbcTemplate.query( query, jobMapper, new Date(sampledate.parse(date_modified_formated).getTime()), parameter);
        }
    }

    /* Check if a job with a specified job code exists or not in the Jobs table. */
    public Long isJobExists(String job_code) {
        try {
            String query = "SELECT * FROM jobs WHERE job_code = ?";
            return this.jdbcTemplate.queryForObject(query, jobMapper, job_code).getJOB_ID();
        } catch (Exception e) { return 0L; }
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
                job.setSTATUS(rs.getString("status").charAt(0));
                job.setSAMPLE_SIZE(rs.getInt("sample_size"));
                job.setRESPONSE_RATE(rs.getShort("response_rate"));
                job.setLOGGED_IN(rs.getInt("logged_in"));
                job.setDELIVERY_TYPE(rs.getString("delivery_type"));
                job.setJOB_CODE(rs.getString("job_code"));
                job.setTARGET_RESPONSE_RATE(rs.getShort("target_response_rate"));
                job.setJOB_NAME(rs.getString("job_name"));
                return job;
        }
    };
}
