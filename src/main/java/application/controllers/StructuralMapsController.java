package application.controllers;

import application.models.JobStructuralMaps;
import application.repositories.StructuralMapsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("wu")
public class StructuralMapsController {

    @Autowired
    StructuralMapsRepository structuralMapsRepository;

    /* Select a work unit by its Name */
    @RequestMapping(value = "gname", produces = "application/json")
    public List<JobStructuralMaps> getJobStructuralMapsByName(@RequestParam("name") String wuname) {
        return this.structuralMapsRepository.getJobStructuralMapsByName(wuname);
    }


    /* Select a work unit by its Work Unit ID */
    @RequestMapping(value = "gwuid", produces = "application/json")
    public JobStructuralMaps getWorkUnitByWUID(@RequestParam("wuid") String wuid) {
        return this.structuralMapsRepository.getWorkUnitByWUID(wuid);
    }

    /* Select a work unit by its Work Unit Code */
    @RequestMapping(value = "gcode", produces = "application/json")
    public JobStructuralMaps getWorkUnitByWUCode(@RequestParam("code") String workunitcode) {
        Integer workunitcodeint = Integer.parseInt(workunitcode);
        return this.structuralMapsRepository.getWorkUnitByWUCode(workunitcodeint);
    }

    /* Select a work unit by its Database ID */
    @RequestMapping(value = "gid", produces = "application/json")
    public JobStructuralMaps getWorkUnitByID(@RequestParam("id") String workunitdbid) {
        Long workunitdbidL = Long.parseLong(workunitdbid);
        return this.structuralMapsRepository.getWorkUnitByID(workunitdbidL);
    }

    /* Select work units by their Cohorts */
    @RequestMapping(value = "gcohort", produces = "application/json")
    public List<JobStructuralMaps> getJobStructuralMapsByCohort(@RequestParam("cohort") String workunitcohort) {
        return this.structuralMapsRepository.getJobStructuralMapsByCohort(workunitcohort);
    }

    /* Select work units by Levels */
    @RequestMapping(value = "glvl", produces = "application/json")
    public List<JobStructuralMaps> getJobStructuralMapsByLevel(@RequestParam("lvl") String level) {
        Short levelshort = Short.parseShort(level);
        return this.structuralMapsRepository.getJobStructuralMapsByLevel(levelshort);
    }

    /* Select work units by their Matrix */
    @RequestMapping(value = "gm", produces = "application/json")
    public List<JobStructuralMaps> getJobStructuralMapsByMatrix(@RequestParam("mtx") String matrix) {
        return this.structuralMapsRepository.getJobStructuralMapsByMatrix(matrix);
    }

    /* Create a work unit [FULL]
    *
    * Template URL:
    * */
    @RequestMapping(value = "createf", produces = "application/json")
    public String createFullWorkUnit(@RequestParam("cohort") String cohort, @RequestParam("den") String denominator,
                                     @RequestParam("lvl") String level, @RequestParam("mtx1") String matrixone,
                                     @RequestParam("mtx2") String matrixtwo, @RequestParam("mtx3") String matrixthree,
                                     @RequestParam("mtx4") String matrixfour, @RequestParam("mtx5") String matrixfive,
                                     @RequestParam("wun") String wuname, @RequestParam("wucode") String workunitcode,
                                     @RequestParam("wui") String workunitid) {
        JobStructuralMaps JobStructuralMaps = this.structuralMapsRepository.getWorkUnitByWUID(workunitid);
        if(JobStructuralMaps.getNAME() != null) {
            return "This work unit already exists";
        } else {
            this.structuralMapsRepository.create(cohort, denominator, level, matrixone, matrixtwo, matrixthree,
                    matrixfour, matrixfive, wuname, workunitcode, workunitid);
            return "Created";
        }
    }

    /* Create a work unit [PARTIAL] */
    @RequestMapping(value = "createp", produces = "application/json")
    public String createPartialWorkUnit(@RequestParam("lvl") String level, @RequestParam("wun") String wuname,
                                        @RequestParam("wui") String workunitid) {
        JobStructuralMaps JobStructuralMaps = this.structuralMapsRepository.getWorkUnitByWUID(workunitid);
        if(JobStructuralMaps.getNAME() != null) {
            return "This work unit already exists";
        } else {
            this.structuralMapsRepository.create("", "0", level,
                    "", "", "",
                    "", "", wuname, "0", workunitid);
            return "Created";
        }
    }
}