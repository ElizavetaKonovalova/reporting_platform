package application.controllers;

import application.models.JobStructuralMaps;
import application.repositories.JobStructuralMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("jobwu")
public class JobStructuralMapController {

    @Autowired
    private JobStructuralMapRepository jobStructuralMapRepository;

    /* Select a work unit by its Name */
    @RequestMapping(value = "gname", produces = "application/json")
    public List<JobStructuralMaps> getJobStructuralMapsByName(@RequestParam("name") String wuname) {
        return this.jobStructuralMapRepository.getJobStructuralMapsByName(wuname);
    }

    /* Select a work unit by its Work Unit ID */
    @RequestMapping(value = "gwuid", produces = "application/json")
    public JobStructuralMaps getWorkUnitByWUID(@RequestParam("wuid") String wuid) {
        return this.jobStructuralMapRepository.getWorkUnitByWUID(wuid);
    }

    /* Select a work unit by its Work Unit Code */
    @RequestMapping(value = "gcode", produces = "application/json")
    public JobStructuralMaps getWorkUnitByWUCode(@RequestParam("code") String workunitcode) {
        Integer workunitcodeint = Integer.parseInt(workunitcode);
        return this.jobStructuralMapRepository.getWorkUnitByWUCode(workunitcodeint);
    }

    /* Select a work unit by its Database ID */
    @RequestMapping(value = "gid", produces = "application/json")
    public JobStructuralMaps getWorkUnitByID(@RequestParam("id") String workunitdbid) {
        Long workunitdbidL = Long.parseLong(workunitdbid);
        return this.jobStructuralMapRepository.getWorkUnitByID(workunitdbidL);
    }

    /* Select work units by their Cohorts */
    @RequestMapping(value = "gcohort", produces = "application/json")
    public List<JobStructuralMaps> getJobStructuralMapsByCohort(@RequestParam("cohort") String workunitcohort) {
        return this.jobStructuralMapRepository.getJobStructuralMapsByCohort(workunitcohort);
    }

    /* Select work units by Levels */
    @RequestMapping(value = "glvl", produces = "application/json")
    public List<JobStructuralMaps> getJobStructuralMapsByLevel(@RequestParam("lvl") String level) {
        return this.jobStructuralMapRepository.getJobStructuralMapsByLevel(level);
    }

    /* Select work units by their Job ID */
    @RequestMapping(value = "gjob", produces = "application/json")
    public List<JobStructuralMaps> getWUByJobID(@RequestParam("id") String job_id) {
        return this.jobStructuralMapRepository.getWUByJobID(job_id);
    }

    /* Select work units by their Denominator */
    @RequestMapping(value = "gd", produces = "application/json")
    public List<JobStructuralMaps> getWUByDenominator(@RequestParam("den") String denominator) {
        return this.jobStructuralMapRepository.getJobByDenominator(denominator);
    }

    /* Remove Work Units by their Work Unit Code */
    @RequestMapping(value = "rwu", produces = "application/json")
    public void removeWUByWUCode(@RequestParam("code") String wu_code) {
        this.jobStructuralMapRepository.removeByWUCode(wu_code);
    }

    /* Remove Work Units by their Work Unit Name */
    @RequestMapping(value = "rwn", produces = "application/json")
    public void removeWUByWUName(@RequestParam("name") String wu_name) {
        this.jobStructuralMapRepository.removeByWUName(wu_name);
    }

    /* Remove Work Units by their Work Unit ID */
    @RequestMapping(value = "rwid", produces = "application/json")
    public void removeWUByWUID(@RequestParam("id") String wu_id) {
        this.jobStructuralMapRepository.removeByWUID(wu_id);
    }

    /* Remove Work Units by their Level One */
    @RequestMapping(value = "rlvl1", produces = "application/json")
    public void removeWUByWULvlOne(@RequestParam("lvl") String level_one) {
        this.jobStructuralMapRepository.removeByLevelOne(level_one);
    }

    /* Remove Work Units by their Level Zero */
    @RequestMapping(value = "rlvl0", produces = "application/json")
    public void removeWUByLevelZero(@RequestParam("lvl") String level_zero) {
        this.jobStructuralMapRepository.removeByLevelZero(level_zero);
    }

    /* Remove Work Units by their database ID */
    @RequestMapping(value = "rdb", produces = "application/json")
    public void removeWUByDBID(@RequestParam("id") String db_id) {
        this.jobStructuralMapRepository.removeByDBID(db_id);
    }

    /* Remove Work Units by their Cohort */
    @RequestMapping(value = "rc", produces = "application/json")
    public void removeWUByCohort(@RequestParam("cohort") String cohort) {
        this.jobStructuralMapRepository.removeByCohort(cohort);
    }

    /* Remove Work Units by their Job ID */
    @RequestMapping(value = "rj", produces = "application/json")
    public void removeWUByJobID(@RequestParam("id") String job_id) {
        this.jobStructuralMapRepository.removeByJobID(job_id);
    }

    /* Remove Work Units by their Denominator */
    @RequestMapping(value = "rden", produces = "application/json")
    public void removeWUByDenominator(@RequestParam("den") String denominator) {
        this.jobStructuralMapRepository.removeByDenominator(denominator);
    }

    /* Create a work unit
    *
    * Template URL: /jobwu/create?cohort=&den=0&lvl0=Zero&lvl1=One&jid=1&wun=Name&wucode=238&wui=(one you've created before)
    * */
    @RequestMapping(value = "create", produces = "application/json")
    public String createFullWorkUnit(@RequestParam("cohort") String cohort, @RequestParam("den") String denominator,
                                     @RequestParam("lvl0") String level_zero, @RequestParam("lvl1") String level_one,
                                     @RequestParam("jid") String job_id, @RequestParam("wun") String wuname,
                                     @RequestParam("wucode") String workunitcode, @RequestParam("wui") String workunitid) throws Exception {
        JobStructuralMaps JobStructuralMaps = this.jobStructuralMapRepository.getWorkUnitByWUID(workunitid);
        if(JobStructuralMaps.getNAME() != null) {
            return "This work unit already exists";
        } else {
            this.jobStructuralMapRepository.create(cohort, denominator, job_id, level_zero, level_one, wuname, workunitcode, workunitid);
            return "Created";
        }
    }
}