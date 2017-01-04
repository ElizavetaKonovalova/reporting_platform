package application.repositories;

import application.models.Jobs;
import application.models.Organisations;
import application.models.SurveyTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class JobRepository  {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JobRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    private OrganisationRepository organisationRepository = new OrganisationRepository(jdbcTemplate);

    @Autowired
    private SurveyTypeRepository surveyTypeRepository = new SurveyTypeRepository(jdbcTemplate);

    private SimpleDateFormat sampledate = new SimpleDateFormat("dd/MM/yyyy", new Locale("en-au", "AU"));
    private String date_modified_formated = sampledate.format(new java.util.Date());


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
        if(client_id != 0) {
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

    /* Select all jobs with a Survey SubType Name */
    public List<Jobs> getJobsBySurveySubTypeName(String sub_type_name) {
        SurveyTypes surveyTypes = this.surveyTypeRepository.getBySubTypeName(sub_type_name).get(0);
        if(surveyTypes.getSUBTYPE_NAME() != null) {
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
    public void removeJobBySurveyTypeName(String survey_sub_type_name) {
        SurveyTypes surveyTypes = this.surveyTypeRepository.getBySubTypeName(survey_sub_type_name).get(0);
        if(surveyTypes.getTYPE_NAME() != null) {
            this.jdbcTemplate.update("DELETE FROM jobs WHERE surveytype_id = " + surveyTypes.getSURVEYTYPE_ID());
        }
    }

    /*Delete a Job by a Survey Type ID */
    public void removeJobBySurveyTypeID(String survey_type_id) {
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

    /* Update a Survey Type ID with a specified Job Code and Survey SubType Name */
    public String updateJobSurveyTypeIDByName(String job_code, String sub_type_name) {
        SurveyTypes surveyTypeRepositories = this.surveyTypeRepository.getBySubTypeName(sub_type_name).get(0);
        if(isJobExists(job_code) != 0 && surveyTypeRepositories.getTYPE_NAME() != null) {
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
    public String createJob(String job_code, String job_name, String client_name, String delivery_date, String delivery_type, String census_start,
                          String census_end, String presentation_date, String response, String logged, String sample, String status,
                          String survey_sub_type, String target_response_rate) throws Exception {

        /* Check if a specified Client exists */
        Long client_id = this.organisationRepository.checkClientExists(client_name);

        /* Check if a Job with this code is already in the database */
        List<Jobs> check_job = this.getJobByCode(job_code);

        /* Check if a specified Survey SubType exists in the database */
        List<SurveyTypes> surveyTypes = this.surveyTypeRepository.getBySubTypeName(survey_sub_type);

        if(client_id != 0 && check_job.size() == 0 && surveyTypes.size() != 0) {

            Integer logged_int, sample_int;
            Short response_short, target_response_rate_short;

            if(status == null) { status = "Active"; }
            if(logged == null) {  logged_int = 0; } else { logged_int = Integer.parseInt(logged); }
            if(response == null) { response_short = 0; } else { response_short = Short.parseShort(response); }
            if(target_response_rate == null) { target_response_rate_short = 0; }
            else { target_response_rate_short = Short.parseShort(target_response_rate); }
            if(sample == null) { sample_int = 0; } else { sample_int = Integer.parseInt(sample); }
            if(delivery_date == null) { delivery_date = date_modified_formated; }
            else { delivery_date = sampledate.format(sampledate.parse(delivery_date)); }
            if(census_start == null) { census_start = date_modified_formated; }
            else { census_start = sampledate.format(sampledate.parse(census_start)); }
            if(census_end == null) { census_end = date_modified_formated; }
            else { census_end = sampledate.format(sampledate.parse(census_end)); }
            if(presentation_date == null) { presentation_date = date_modified_formated; }
            else { presentation_date = sampledate.format(sampledate.parse(presentation_date)); }

            this.jdbcTemplate.update("INSERT INTO jobs (census_end, census_start, delivery_date, delivery_type, job_code, job_name, logged_in, " +
                            "presentation_date, response_rate, sample_size, status, target_response_rate, client_id, surveytype_id,date_modified) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Date(sampledate.parse(census_end).getTime()),
                    new Date(sampledate.parse(census_start).getTime()), new Date(sampledate.parse(delivery_date).getTime()), delivery_type,
                    job_code.toUpperCase(), job_name, logged_int, new Date(sampledate.parse(presentation_date).getTime()), response_short,
                    sample_int, status.charAt(0), target_response_rate_short, client_id, surveyTypes.get(0).getSURVEYTYPE_ID(),
                    new Date(sampledate.parse(date_modified_formated).getTime()));

            return "Created";
        } else { return "There is:\n 1) OR: no such client \n 2) OR: a job with this code is already in the database\n 3) OR: no such survey subtype"; }
    }


    /* HELPERS */

    /* Selects parsed dates from the Jobs database */
    private List<Jobs> dateSelector(String query, String date, String parameter) throws Exception {
        Date date_formatted = new Date(sampledate.parse(sampledate.format(date)).getTime());
        if(parameter.isEmpty()) {
            return this.jdbcTemplate.query(query, jobMapper, date_formatted);
        } else {
            return this.jdbcTemplate.query(query, jobMapper, date_formatted, parameter);
        }
    }

    /* Check if a job with a specified job code exists or not in the Jobs table. */
    public Long isJobExists(String job_code) {
        try { return this.jdbcTemplate.queryForObject("SELECT * FROM jobs WHERE job_code = ?", jobMapper, job_code).getJOB_ID();
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
                job.setDATE_MODIFIED(rs.getDate("date_modified"));
                return job;
        }
    };
}
