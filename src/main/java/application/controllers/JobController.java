package application.controllers;

import application.models.Jobs;
import application.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("job")
public class JobController {

    @Autowired
    JobRepository jobRepository;

    @RequestMapping(value = "gcode", produces = "application/json")
    public Jobs getJobByCode(@RequestParam("code") String jobcode) {
        return this.jobRepository.getJobByCode(jobcode);
    }

    @RequestMapping(value = "gid", produces = "application/json")
    public Jobs getJobByID(@RequestParam("id") String jobid) {
        Long id = Long.parseLong(jobid);
        return this.jobRepository.getJobByID(id);
    }

    @RequestMapping(value = "gname", produces = "application/json")
    public List<Jobs> getJobByName(@RequestParam("name") String jobname) {
        return this.jobRepository.getJobsByName(jobname);
    }

    @RequestMapping(value = "gclient", produces = "application/json")
    public List<Jobs> getJobByClientID(@RequestParam("client") String client) {
        Long clientid = Long.parseLong(client);
        return this.jobRepository.getJobsByClientID(clientid);
    }

    @RequestMapping(value = "rid", produces = "application/json")
    public void removeByID(@RequestParam("id") String jobid) {
        Long id = Long.parseLong(jobid);
        this.jobRepository.removeJobByID(id);
    }

    @RequestMapping(value = "rcode", produces = "application/json")
    public void removeByCode(@RequestParam("code") String jobcode) {
        this.jobRepository.removeJobByCode(jobcode);
    }

    /* Create a new job.
    *
    * Required: code, client id, and name
    *
    * Template request link :
    * "/job/create?code=&name=&client=&ddate=&dtype=&cstart=&cend=&pdate=&rrate=&lin=&ssize=&status=&stype="
    *
    * */
    @RequestMapping(value = "create", produces = "application/json")
    public String create(@RequestParam("code") String jobcode, @RequestParam("name") String jobname, @RequestParam("client") String clientid,
                          @RequestParam("ddate") String deliverydate, @RequestParam("dtype") String deliverytype,
                          @RequestParam("cstart") String censusstart, @RequestParam("cend") String censusend,
                          @RequestParam("pdate") String presentationdate, @RequestParam("rrate") String responserate,
                          @RequestParam("lin") String loggedin, @RequestParam("ssize") String samplesize,
                          @RequestParam("status") Boolean status, @RequestParam("stype") String subtypeid) throws Exception {

        Jobs checkjob = this.jobRepository.getJobByCode(jobcode);
        if(checkjob.getJOB_CODE() != null) {
            return "This job already exists";
        } else {
            this.jobRepository.createJob(jobcode, jobname, clientid, deliverydate, deliverytype, censusstart, censusend, presentationdate,
                    responserate, loggedin, samplesize, status, subtypeid);
            return "Created";
        }
    }
}
