package application.repositories;
import application.models.Jobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ekonovalova on 12/5/2016.
 */
@Repository
public class JobRepository  {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public Jobs getJobByCode(String jobcode){
        return this.jdbcTemplate.queryForObject("SELECT * FROM jobs WHERE jobcode=" + jobcode, jobMapper);
    }

    public Jobs getJobByID(String jobid){
        return this.jdbcTemplate.queryForObject("SELECT * FROM jobs WHERE id=" + jobid, jobMapper);
    }

    public List<Jobs> getJobByName(String jobname){
        return this.jdbcTemplate.query("SELECT * FROM jobs WHERE jobname=" + jobname, jobMapper);
    }

    public void createJob(String jobcode, String jobname, Long clientid, Date deliverydate, String deliverytype, Date censusstart,
                          Date censusend, Date presentationdate, Short responserate, Integer loggedin, Short samplesize, Boolean status) {
        this.jdbcTemplate.update("INSERT INTO jobs (jobcode, jobname, clientid, deliverydate, deliverytype, censusstart, censusend, " +
                "presentationdate, responserate, loggedin,samplesize, status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", jobcode, jobname, clientid,
                deliverydate, deliverytype, censusstart, censusend, presentationdate, responserate, loggedin, samplesize, status);
    }

    private static final RowMapper<Jobs> jobMapper = new RowMapper<Jobs>() {
        public Jobs mapRow(ResultSet rs, int rowNum) throws SQLException {
            Jobs job = new Jobs();
            job.setCENSUSSTART(rs.getDate("censusstart"));
            job.setCENSUSEND(rs.getDate("censusend"));
            job.setDELIVERYDATE(rs.getDate("deliverydate"));
            job.setPRESENTATIONDATE(rs.getDate("presentationdate"));
            job.setID(rs.getLong("id"));
            job.setSTATUS(rs.getBoolean("status"));
            job.setSAMPLESIZE(rs.getShort("samplesize"));
            job.setRESPONSERATE(rs.getShort("responserate"));
            job.setLOGGEDIN(rs.getInt("loggedin"));
            job.setDELIVERYTYPE(rs.getString("deliverytype"));
            job.setCLIENTID(rs.getLong("clientid"));
            job.setJOBCODE(rs.getString("jobcode"));
            job.setJOBNAME(rs.getString("jobname"));
            return job;
        }
    };
}
