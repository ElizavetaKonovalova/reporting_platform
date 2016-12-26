package application.controllers;

import application.models.Jobs;
import application.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("job")
public class JobController {

    @Autowired
    private JobRepository jobRepository;


    /* GETTERS */

    /* Return all Jobs */
    @RequestMapping(value = "/", produces = "application/json")
    public List<Jobs> getJobs() { return this.jobRepository.getAllJobs(); }

    /* Find a  job by its Job Code */
    @RequestMapping(value = "jobcode/{code:.+}", produces = "application/json")
    public List<Jobs> getJobByCode(@PathVariable("code") String job_code) {
        return this.jobRepository.getJobByCode(job_code);
    }

    /* Find a  job by its Job ID */
    @RequestMapping(value = "jobid/{id:.+}", produces = "application/json")
    public List<Jobs> getJobByID(@PathVariable("id") String job_id) {
        return this.jobRepository.getJobByID(job_id);
    }

    /* Find a  job by its Job Name */
    @RequestMapping(value = "jobname/{name:.+}", produces = "application/json")
    public List<Jobs> getJobByName(@PathVariable("name") String job_name) {
        return this.jobRepository.getJobsByName(job_name);
    }

    /* Find a  job by its Client ID */
    @RequestMapping(value = "clientid/{client:.+}", produces = "application/json")
    public List<Jobs> getJobByClientID(@PathVariable("client") String client_id) {
        return this.jobRepository.getJobsByClientID(client_id);
    }

    /* Find a  job by its Client Name */
    @RequestMapping(value = "clientname/{name:.+}", produces = "application/json")
    public List<Jobs> getJobByClientName(@PathVariable("name") String client_name) {
        return this.jobRepository.getJobsByClientName(client_name);
    }

    /* Find a  job by its Job Census Start */
    @RequestMapping(value = "censtart/{date:.+}", produces = "application/json")
    public List<Jobs> getJobByCensusStart(@PathVariable("date") String census_start) throws Exception {
        return this.jobRepository.getJobsByCensusStart(census_start);
    }

    /* Find a  job by its Job Census End */
    @RequestMapping(value = "cenend/{date:.+}", produces = "application/json")
    public List<Jobs> getJobByCensusEnd(@PathVariable("date") String census_end) throws Exception {
        return this.jobRepository.getJobsByCensusEnd(census_end);
    }

    /* Find a  job by its Job Delivery Date */
    @RequestMapping(value = "delivery/{date:.+}", produces = "application/json")
    public List<Jobs> getJobByDeliveryDate(@PathVariable("date") String delivery_date) throws Exception {
        return this.jobRepository.getJobsByDeliveryDate(delivery_date);
    }

    /* Find a  job by its Job Presentation Date */
    @RequestMapping(value = "present/{date:.+}", produces = "application/json")
    public List<Jobs> getJobByPresentationDate(@PathVariable("date") String presentation) throws Exception {
        return this.jobRepository.getJobsByPresentationDate(presentation);
    }

    /* Find a  job by its Sample Size */
    @RequestMapping(value = "sample/{size:.+}", produces = "application/json")
    public List<Jobs> getJobBySampleSize(@PathVariable("size") String sample_size) {
        return this.jobRepository.getJobsBySample(sample_size);
    }

    /* Find a  job by its Status */
    @RequestMapping(value = "status/{name:.+}", produces = "application/json")
    public List<Jobs> getJobByStatus(@PathVariable("name") String status) {
        return this.jobRepository.getJobsByStatus(status);
    }

    /* Find a  job by its Response Rate */
    @RequestMapping(value = "rate/{number:.+}", produces = "application/json")
    public List<Jobs> getJobByResponseRate(@PathVariable("number") String response_rate) {
        return this.jobRepository.getJobsByResponseRate(response_rate);
    }

    /* Find a  job by its Logged In Number */
    @RequestMapping(value = "logged/{number:.+}", produces = "application/json")
    public List<Jobs> getJobByLoggedIn(@PathVariable("number") String logged_in) {
        return this.jobRepository.getJobsByLoggedIn(logged_in);
    }

    /* Find a  job by its Target Response Rate */
    @RequestMapping(value = "targetrr/{rate:.+}", produces = "application/json")
    public List<Jobs> getJobByTargetResponseRate(@PathVariable("rate") String target_response_rate) {
        return this.jobRepository.getJobsByTargetRR(target_response_rate);
    }

    /* Find a  job by its Survey Type ID */
    @RequestMapping(value = "stypeid/{id:.+}", produces = "application/json")
    public List<Jobs> getJobBySurveyTypeID(@PathVariable("id") String survey_type_id) {
        return this.jobRepository.getJobsBySurveyTypeID(survey_type_id);
    }

    /* Find a  job by its Survey Type Name */
    @RequestMapping(value = "stypename/{name:.+}", produces = "application/json")
    public List<Jobs> getJobBySurveyTypeName(@PathVariable("name") String survey_name) {
        return this.jobRepository.getJobsBySurveyTypeName(survey_name);
    }




    /* REMOVALS */

    /* Remove a  job by its Job ID */
    @RequestMapping(value = "remjobid", produces = "application/json")
    public void removeByID(@PathVariable("id") String job_id) {
        this.jobRepository.removeJobByID(job_id);
    }

    /* Remove a  job by its Job Code */
    @RequestMapping(value = "remjobcode", produces = "application/json")
    public void removeByCode(@PathVariable("code") String job_code) {
        this.jobRepository.removeJobByCode(job_code);
    }


    /* NULLERS */


    /* CREATORS*/

    /* Create a new job. */
    @RequestMapping(value = "create", produces = "application/json")
    public String create(@RequestParam("code") String jobcode, @RequestParam("name") String jobname, @RequestParam("client") String clientid,
                          @RequestParam("ddate") String deliverydate, @RequestParam("dtype") String deliverytype,
                          @RequestParam("cstart") String censusstart, @RequestParam("cend") String censusend,
                          @RequestParam("pdate") String presentationdate, @RequestParam("rrate") String responserate,
                          @RequestParam("trate") String target_responserate, @RequestParam("lin") String loggedin,
                          @RequestParam("ssize") String samplesize, @RequestParam("status") String status, @RequestParam("stype") String subtypeid) throws Exception {

        List<Jobs> checkjob = this.jobRepository.getJobByCode(jobcode);
        if(checkjob.size() != 0) {
            return "This job already exists";
        } else {
            this.jobRepository.createJob(jobcode, jobname, clientid, deliverydate, deliverytype, censusstart, censusend, presentationdate,
                    responserate, loggedin, samplesize, status, subtypeid, target_responserate);
            return "Created";
        }
    }
}
