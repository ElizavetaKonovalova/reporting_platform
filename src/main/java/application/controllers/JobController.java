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

    @RequestMapping(value = {"get","find"}, produces = "application/json")
    public List<Jobs> get(@RequestParam(value = "find", required = false) String find,
                          @RequestParam(value = "target", required = false) String target) throws Exception {

        target = (target == null) ? target = "": target;

        switch (target) {

            /* Find a  job by its Job Code */
            case "jcode": return this.jobRepository.getJobByCode(find);

            /* Find a  job by its Job ID */
            case "jid": return this.jobRepository.getJobByID(find);

            /* Find a  job by its Job Name */
            case "jname": return this.jobRepository.getJobsByName(find);

           /* Find a  job by its Client ID */
            case "cid": return this.jobRepository.getJobsByClientID(find);

            /* Find a  job by its Client Name */
            case "cname": return this.jobRepository.getJobsByClientName(find);

            /* Find a  job by its Job Census Start */
            case "censtart": return this.jobRepository.getJobsByCensusStart(find);

            /* Find a job by its Job Census End */
            case "cenend": return this.jobRepository.getJobsByCensusEnd(find);

            /* Find a  job by its Job Delivery Date */
            case "deldate": return this.jobRepository.getJobsByDeliveryDate(find);

            /* Find a  job by its Job Delivery Type */
            case "deltype": return this.jobRepository.getJobsByDeliveryType(find);

            /* Find a  job by its Job Presentation Date */
            case "present": return this.jobRepository.getJobsByPresentationDate(find);

            /* Find a  job by its Sample Size */
            case "sample": return this.jobRepository.getJobsBySample(find);

            /* Find a  job by its Status */
            case "status": return this.jobRepository.getJobsByStatus(find);

            /* Find a  job by its Response Rate */
            case "rr": return this.jobRepository.getJobsByResponseRate(find);

            /* Find a  job by its Logged In Number */
            case "log": return this.jobRepository.getJobsByLoggedIn(find);

            /* Find a  job by its Target Response Rate */
            case "trr": return this.jobRepository.getJobsByTargetRR(find);

            /* Find a  job by its Survey Type ID */
            case "stypeid": return this.jobRepository.getJobsBySurveyTypeID(find);

            /* Find a  job by its Survey Type Name */
            case "subname": return this.jobRepository.getJobsBySurveySubTypeName(find);

            /* Select ALL Jobs */
            default: return this.jobRepository.getAllJobs();
        }
    }


    /* UPDATERS */


    /* REMOVALS */

    @RequestMapping(value = {"remove", "delete"}, produces = "application/json")
    public void remove(@RequestParam(value = "find", required = false) String find, @RequestParam(value = "target", required = false) String target) {

        switch (target) {

            /* Remove a  job by its Job ID */
            case "jid": this.jobRepository.removeJobByID(find);break;

            /* Remove a  job by its Job Code */
            case "jcode": this.jobRepository.removeJobByCode(find);break;

            /* Remove a  job by its Client ID */
            case "cid": this.jobRepository.removeJobByClientID(find);break;

            /* Remove a  job by its Survey SubType Name */
            case "subname": this.jobRepository.removeJobBySurveyTypeName(find);break;

            /* Remove a  job by its Survey Type ID */
            case "stid": this.jobRepository.removeJobBySurveyTypeID(find);break;
            default: break;
        }
    }


    /* NULLERS */

    @RequestMapping(value = "null", produces = "application/json")
    public void nuller(@RequestParam(value = "find", required = false) String find,
                          @RequestParam(value = "target", required = false) String target) throws Exception {

        target = (target == null) ? target = "": target;

        switch (target) {

            /* Find a  job by its Job Census Start */
            case "censtart": this.jobRepository.nullCensusStart(find);break;

            /* Find a job by its Job Census End */
            case "cenend": this.jobRepository.nullCensusEnd(find);break;

            /* Find a  job by its Job Delivery Date */
            case "deldate": this.jobRepository.nullDeliveryDate(find);break;

            /* Find a  job by its Job Delivery Type */
            case "deltype": this.jobRepository.nullDeliveryType(find);break;

            /* Find a  job by its Job Presentation Date */
            case "present": this.jobRepository.nullPresentationDate(find);break;

            /* Find a  job by its Sample Size */
            case "sample": this.jobRepository.nullSampleSize(find);break;

            /* Find a  job by its Response Rate */
            case "rr": this.jobRepository.nullResponseRates(find);break;

            /* Find a  job by its Logged In Number */
            case "log": this.jobRepository.nullLoggedIn(find);break;

            /* Find a  job by its Target Response Rate */
            case "trr": this.jobRepository.getJobsByTargetRR(find); break;
            default: break;
        }
    }


    /* CREATORS*/

    @RequestMapping(value = "create", produces = "application/json")
    public String create(@RequestParam("jcode") String job_code, @RequestParam("jname") String job_name, @RequestParam("cname") String client_name,
                         @RequestParam(value = "ddate", required = false) String delivery_date,
                         @RequestParam(value = "dtype", required = false) String delivery_type,
                         @RequestParam(value = "cstart", required = false) String census_start, @RequestParam(value = "cend", required = false) String census_end,
                         @RequestParam(value = "pdate", required = false) String presentation_date,
                         @RequestParam(value = "rrate", required = false) String response_rate,
                         @RequestParam(value = "trate", required = false) String target_response_rate,
                         @RequestParam(value = "lin", required = false) String logged_in, @RequestParam(value = "sample", required = false) String sample_size,
                         @RequestParam("status") String status, @RequestParam("sub") String sub_type_name) throws Exception {

            return this.jobRepository.createJob(job_code, job_name, client_name, delivery_date, delivery_type, census_start, census_end, presentation_date,
                    response_rate, logged_in, sample_size, status, sub_type_name, target_response_rate);
    }
}
