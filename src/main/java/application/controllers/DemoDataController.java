package application.controllers;

import application.repositories.DemoDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demodata")
public class DemoDataController {

    @Autowired
    DemoDataRepository demoDataRepository;

    /* CREATORS */

    /*Create a new record in the Demo Data table */
    @RequestMapping(value = {"/create", "/new"}, produces = "application/json")
    public String create(@RequestParam("fid") String field_id, @RequestParam("pid") String participant_id,
                         @RequestParam("response") String response_value) throws Exception {
        return this.demoDataRepository.create(field_id, participant_id, response_value);
    }


    /* UPDATERS */

    /* Update fields in the Demo Data table */
    @RequestMapping(value = "update", produces = "application/json")
    public String update(@RequestParam("pid") String participant_id, @RequestParam("fid") String field_id,
                         @RequestParam(value = "response", required = false) String response,
                         @RequestParam(value = "cname", required = false) String cohort_name) throws Exception {

        if(participant_id != null && field_id != null) {

            if(response != null) {
                return this.demoDataRepository.updateResponseValue(participant_id, response, field_id);
            } else if(cohort_name != null) {
                return this.demoDataRepository.updateCohortID(cohort_name, participant_id, field_id);
            } else { return "Parameters include only: cname [cohort name] and response [response value]."; }

        } else { return "Please, provide necessary parameters"; }
    }


    /* NULLERS */

    /* Set a Cohort ID to Null */
    @RequestMapping(value = "null", produces = "application/json")
    public void nullCohort(@RequestParam("pid") String participant_id, @RequestParam("fid") String field_id) throws Exception {
        this.demoDataRepository.nullCohortID(participant_id, field_id);
    }

    /* REMOVALS */

    /* Remove rows from the Demo Data table*/
    @RequestMapping(value = {"/remove", "/delete"}, produces = "application/json")
    public void remove(@RequestParam(value = "dbid", required = false) String db_id,
                       @RequestParam(value = "fid", required = false) String field_id,
                       @RequestParam(value = "pid", required = false) String participant_id,
                       @RequestParam(value = "date", required = false) String date_modified) throws Exception {

        /* Remove by a database ID */
        if(db_id != null) { this.demoDataRepository.removeDemoDataByDBID(db_id); }

        /* Remove by a field ID */
        if(field_id != null) { this.demoDataRepository.removeDemoDataByFieldID(field_id);}

        /* Remove by a participant ID */
        if(participant_id != null) { this.demoDataRepository.removeDemoDataByParticipantID(participant_id);}

        /* Remove by a date modified */
        if(date_modified != null) { this.demoDataRepository.removeDemoDataByDateModified(date_modified);}
    }
}
