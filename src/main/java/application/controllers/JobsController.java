package application.controllers;

import application.models.Jobs;
import application.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

/**
 * Created by ekonovalova on 12/5/2016.
 */

@RestController
@RequestMapping("jobs")
public class JobsController {

    @Autowired
    JobRepository jobRepository;

    @RequestMapping(value = "getall")
    public Jobs getJobByCode(@RequestParam("jobcode") String jobcode) {
        return this.jobRepository.getJobByCode(jobcode);
    }

    @RequestMapping(value = "createfull")
    public void createFullJob(@RequestParam("jobcode") String jobcode, @RequestParam("jobname") String jobname, @RequestParam("clientid") Long clientid,
                          @RequestParam("deliverydate") Date deliverydate, @RequestParam("deliverytype") String deliverytype,
                          @RequestParam("censusstart") Date censusstart, @RequestParam("censusend") Date censusend,
                          @RequestParam("presentationdate") Date presentationdate, @RequestParam("responserate") Short responserate,
                          @RequestParam("loggedin") Integer loggedin, @RequestParam("samplesize") Short samplesize,
                          @RequestParam("status") Boolean status) {
        this.jobRepository.createJob(jobcode, jobname, clientid, deliverydate, deliverytype, censusstart, censusend, presentationdate,
                responserate, loggedin, samplesize, status);
    }

    /*JobDAO jobDAO;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/getall")
    Iterable<Jobs> getAllJobs() {
        return this.jobDAO.listAllJobs();
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/create/{clientid, jobname, jobcode}")
    Jobs createJob(@PathVariable Long clientid, @PathVariable String jobname, @PathVariable String jobcode) {
        return this.jobDAO.save(new Jobs(clientid, jobcode, jobname));
    }*/
}
