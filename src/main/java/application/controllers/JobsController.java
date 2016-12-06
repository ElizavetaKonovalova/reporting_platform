package application.controllers;

import application.models.Jobs;
import application.repositories.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by ekonovalova on 12/5/2016.
 */

@RestController
@RequestMapping("jobs")
public class JobsController {

    @Autowired
    JobRepository jobRepository;
    protected Logger logger = LoggerFactory.getLogger(JobsController.class);

    @RequestMapping(value = "getjc", produces = "application/json")
    public Jobs getJobByCode(@RequestParam("jc") String jobcode) {
        return this.jobRepository.getJobByCode(jobcode);
    }

    @RequestMapping(value = "getji", produces = "application/json")
    public Jobs getJobByID(@RequestParam("ji") String jobid) {
        Long id = Long.parseLong(jobid);
        return this.jobRepository.getJobByID(id);
    }

    @RequestMapping(value = "getjn", produces = "application/json")
    public List<Jobs> getJobByName(@RequestParam("jn") String jobname) {
        return this.jobRepository.getJobsByName(jobname);
    }

    @RequestMapping(value = "getjci", produces = "application/json")
    public List<Jobs> getJobByClientID(@RequestParam("ci") String client) {
        Long clientid = Long.parseLong(client);
        return this.jobRepository.getJobsByClientID(clientid);
    }

    @RequestMapping(value = "remji", produces = "application/json")
    public void removeByID(@RequestParam("ji") String jobid) {
        Long id = Long.parseLong(jobid);
        this.jobRepository.removeJobByID(id);
    }

    @RequestMapping(value = "remjc", produces = "application/json")
    public void removeByCode(@RequestParam("jc") String jobcode) {
        this.jobRepository.removeJobByCode(jobcode);
    }

    @RequestMapping(value = "create", produces = "application/json")
    public String create(@RequestParam("jc") String jobcode, @RequestParam("jn") String jobname, @RequestParam("ci") String clientid,
                          @RequestParam("ddate") String deliverydate, @RequestParam("dtype") String deliverytype,
                          @RequestParam("cstart") String censusstart, @RequestParam("cend") String censusend,
                          @RequestParam("pdate") String presentationdate, @RequestParam("rrate") String responserate,
                          @RequestParam("lin") String loggedin, @RequestParam("ssize") String samplesize,
                          @RequestParam("status") Boolean status, @RequestParam("stype") String subtypeid) throws SQLException, ParseException {

        Jobs checkjob = this.jobRepository.getJobByCode(jobcode);
        if(checkjob.getJOBCODE() != null) {
            return "This job already exists";
        } else {
            this.jobRepository.createJob(jobcode, jobname, clientid, deliverydate, deliverytype, censusstart, censusend, presentationdate,
                    responserate, loggedin, samplesize, status, subtypeid);
            return "Created";
        }
    }
}
