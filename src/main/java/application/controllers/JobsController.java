package application.controllers;

import application.models.Jobs;
import application.service.Jobs.JobDAO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ekonovalova on 12/5/2016.
 */

@RestController
@RequestMapping("/jobs")
public class JobsController {

    JobDAO jobDAO;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Iterable<Jobs> dashboard(Model model) {
        Jobs newjob = new Jobs((long) 1234, "EASDF4", "JOBNAME");
        this.jobDAO.save(newjob);
        return this.jobDAO.listAllJobs();
    }
}
