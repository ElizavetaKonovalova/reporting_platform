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
    private JobRepository jobRepository;


    /* GETTERS */

    /* Find a  job by its Job Code */
    @RequestMapping(value = "gcode", produces = "application/json")
    public List<Jobs> getJobByCode(@RequestParam("code") String job_code) {
        return this.jobRepository.getJobByCode(job_code);
    }

    /* Find a  job by its Job ID */
    @RequestMapping(value = "gid", produces = "application/json")
    public List<Jobs> getJobByID(@RequestParam("id") String job_id) {
        return this.jobRepository.getJobByID(job_id);
    }

    /* Find a  job by its Job Name */
    @RequestMapping(value = "gname", produces = "application/json")
    public List<Jobs> getJobByName(@RequestParam("name") String job_name) {
        return this.jobRepository.getJobsByName(job_name);
    }

    /* Find a  job by its Client ID */
    @RequestMapping(value = "gclient", produces = "application/json")
    public List<Jobs> getJobByClientID(@RequestParam("client") String client_id) {
        return this.jobRepository.getJobsByClientID(client_id);
    }


    /* REMOVALS */

    /* Remove a  job by its Job ID */
    @RequestMapping(value = "rid", produces = "application/json")
    public void removeByID(@RequestParam("id") String job_id) {
        this.jobRepository.removeJobByID(job_id);
    }

    /* Remove a  job by its Job Code */
    @RequestMapping(value = "rcode", produces = "application/json")
    public void removeByCode(@RequestParam("code") String job_code) {
        this.jobRepository.removeJobByCode(job_code);
    }

    /* Create a new job. */
    @RequestMapping(value = "create", produces = "application/json")
    public String create(@RequestParam("code") String jobcode, @RequestParam("name") String jobname, @RequestParam("client") String clientid,
                          @RequestParam("ddate") String deliverydate, @RequestParam("dtype") String deliverytype,
                          @RequestParam("cstart") String censusstart, @RequestParam("cend") String censusend,
                          @RequestParam("pdate") String presentationdate, @RequestParam("rrate") String responserate,
                          @RequestParam("lin") String loggedin, @RequestParam("ssize") String samplesize,
                          @RequestParam("status") Boolean status, @RequestParam("stype") String subtypeid) throws Exception {

        List<Jobs> checkjob = this.jobRepository.getJobByCode(jobcode);
        if(checkjob.get(0).getJOB_CODE() != null) {
            return "This job already exists";
        } else {
            this.jobRepository.createJob(jobcode, jobname, clientid, deliverydate, deliverytype, censusstart, censusend, presentationdate,
                    responserate, loggedin, samplesize, status, subtypeid);
            return "Created";
        }
    }
}
