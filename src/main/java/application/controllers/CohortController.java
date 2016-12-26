package application.controllers;

import application.models.Cohorts;
import application.repositories.CohortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("cohort")
public class CohortController {

    @Autowired
    CohortRepository cohortRepository;

    /* CREATORS */

    @RequestMapping(value = "/create",  produces = "application/json")
    public String create(@RequestParam("cname") String cohort_name, @RequestParam(value = "desc", required = false) String description,
                         @RequestParam(value = "pname", required = false) String parent_name) {
        return this.cohortRepository.create(cohort_name, description, parent_name);
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
    public String remove(@RequestParam(value = "cname", required = false) String cohort_name,
                         @RequestParam(value = "pname", required = false) String parent_name) {

        /* Remove by Cohort Name */
        if(cohort_name != null) {
            return this.cohortRepository.removeCohortByName(cohort_name);
        }

        /* Remove by Parent ID */
        if(parent_name != null) {
            return this.cohortRepository.removeCohortByParentID(parent_name);
        } else { return "You must specify the parameters"; }
    }

    @RequestMapping(value = "null", produces = "application/json")
    public String nuller(@RequestParam(value = "cname") String cohort_name,
                         @RequestParam(value = "target") String target) {

        switch (target) {
            case "parent": return this.cohortRepository.nullParentID(cohort_name);
            case "desc": return this.cohortRepository.nullDescription(cohort_name);
            default: return "Please, use the correct target name: name, parent, or desc";
        }
    }

    @RequestMapping(value = "get", produces = "application/json")
    public List<Cohorts> get(@RequestParam(value = "text") String variable,
                             @RequestParam(value = "target") String target) {

        switch (target) {
            case "name": return this.cohortRepository.getCohortByName(variable);
            case "parent": return this.cohortRepository.getCohortByParentName(variable);
            case "desc": return this.cohortRepository.getCohortByDescription(variable);
            case "id": return this.cohortRepository.getCohortByDBID(variable);
            default: return new ArrayList<>();
        }
    }
}
