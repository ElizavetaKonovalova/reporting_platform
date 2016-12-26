package application.controllers;

import application.repositories.CohortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cohort")
public class CohortController {

    @Autowired
    CohortRepository cohortRepository;

    /* CREATORS */

    @RequestMapping(value = "/create",  produces = "application/json")
    public String create(@RequestParam("name") String cohort_name, @RequestParam("desc") String description) {
        return this.cohortRepository.create(cohort_name, description);
    }


    /* UPDATERS */

    @RequestMapping(value = "/update", produces = "application/json")
    public String update(@RequestParam(value = "cname", required = false) String cohort_name,
                         @RequestParam(value = "pname", required = false) String parent_name,
                         @RequestParam(value = "old", required = false) String cohort_name_old,
                         @RequestParam(value = "new", required = false) String cohort_name_new,
                         @RequestParam(value = "desc", required = false) String description) {

        /* Update Cohort Name */
        if(cohort_name_new != null && cohort_name_old != null) {
            return this.cohortRepository.updateCohortName(cohort_name_old, cohort_name_new);
        }

        /* Update Parent ID */
        if(cohort_name != null && parent_name != null) {
            return this.cohortRepository.updateParentID(cohort_name, parent_name);
        }

        /* Update Cohort Description */
        if(description != null && cohort_name != null) {
            return this.cohortRepository.updateCohortDescription(cohort_name, description);
        } else { return "You have to specify parameters"; }
    }

    /* REMOVALS */

    @RequestMapping(value = {"/remove", "/delete"}, produces = "application/json")
    public String remove(@RequestParam(value = "cname", required = false) String cohort_name) {
        return this.cohortRepository.removeCohortByName(cohort_name);
    }
}
