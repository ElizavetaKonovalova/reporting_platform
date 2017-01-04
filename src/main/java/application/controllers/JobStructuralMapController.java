package application.controllers;

import application.models.JobStructuralMaps;
import application.repositories.JobStructuralMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("jwu")
public class JobStructuralMapController {

    @Autowired
    private JobStructuralMapRepository jobStructuralMapRepository;



    /* CREATORS */

    /* Create a work unit */
    @RequestMapping(value = {"create","new"}, produces = "application/json")
    public String create(@RequestParam(value = "coh", required = false) String cohort,
                         @RequestParam(value = "den", required = false) String denominator,
                         @RequestParam(value = "lvl0", required = false) String level_zero,
                         @RequestParam(value = "lvl1", required = false) String level_one,
                         @RequestParam("wun") String work_unit_name, @RequestParam("wucode") String work_unit_code,
                         @RequestParam("wuid") String work_unit_id, @RequestParam("jcode") String job_code) throws Exception {

        return this.jobStructuralMapRepository.create(cohort, denominator, level_zero, level_one,
                work_unit_name, work_unit_code, work_unit_id, job_code);
    }

    /* Create a new table for a particular job */
    @RequestMapping(value = {"createtable", "newtable"}, produces = "application/json")
    public String createTable(@RequestParam("jcode") String job_code, @RequestParam("cname") String client_name) throws Exception {
        return this.jobStructuralMapRepository.createTable(job_code, client_name);
    }



    /* GETTERS */

    @RequestMapping(value = {"get", "find"}, produces = "application/json")
    public List<JobStructuralMaps> get(@RequestParam(value = "find", required = false) String find,
                                       @RequestParam("jcode") String job_code,
                                       @RequestParam(value = "target", required = false) String target) {

        target = (target == null) ? "": target;
        find = (find == null) ? "": find;

        switch (target) {

            /* Select a Work Unit by its Name */
            case "wuname": return this.jobStructuralMapRepository.getJobStructuralMapsByName(find, job_code);

            /* Select Work Units by their Work Unit ID */
            case "wuid": return this.jobStructuralMapRepository.getWorkUnitByWUID(find, job_code);

            /* Select Work Units by their Work Unit Code */
            case "wucode": return this.jobStructuralMapRepository.getWorkUnitByWUCode(find, job_code);

            /* Select Work Units by their Database ID */
            case "dbid": return this.jobStructuralMapRepository.getWorkUnitByID(find, job_code);

            /* Select Work Units by their Cohorts */
            case "coh": return this.jobStructuralMapRepository.getJobStructuralMapsByCohort(find, job_code);

            /* Select Work Units by Levels */
            case "lvl": return this.jobStructuralMapRepository.getJobStructuralMapsByLevel(find, job_code);

            /* Select Work Units by their Denominator */
            case "den": return this.jobStructuralMapRepository.getJobByDenominator(find, job_code);

            /* Select All Work Units */
            default: return this.jobStructuralMapRepository.getAll(job_code);
        }
    }


    /* UPDATERS */


    /* REMOVALS */

    @RequestMapping(value = {"remove", "delete"}, produces = "application/json")
    public void remove(@RequestParam(value = "find", required = false) String find,
                                       @RequestParam("jcode") String job_code,
                                       @RequestParam(value = "target", required = false) String target) {

        target = (target == null) ? "": target;
        find = (find == null) ? "": find;

        switch (target) {

            /* Remove Work Units by their Work Unit Code */
            case "wucode": this.jobStructuralMapRepository.removeByWUCode(find, job_code);break;

            /* Remove Work Units by their Work Unit ID */
            case "wuid": this.jobStructuralMapRepository.removeByWUID(find, job_code);break;

            /* Remove Work Units by their Level One */
            case "lvl1": this.jobStructuralMapRepository.removeByLevelOne(find, job_code);break;

            /* Remove Work Units by their Level Zero */
            case "lvl0": this.jobStructuralMapRepository.removeByLevelZero(find, job_code);break;

            /* Remove Work Units by their database ID */
            case "dbid": this.jobStructuralMapRepository.removeByDBID(find, job_code);break;

            /* Remove Work Units by their Cohort */
            case "coh": this.jobStructuralMapRepository.removeByCohort(find, job_code);break;

            /* Remove Work Units by their Denominator */
            case "den": this.jobStructuralMapRepository.removeByDenominator(find, job_code);break;

            /* Remove an ENTIRE table */
            case "table": this.jobStructuralMapRepository.removeTable(job_code);break;

            default: break;
        }
    }


    /* NULLERS */
}