package application.controllers;

import application.models.WorkUnits;
import application.repositories.WorkUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ekonovalova on 12/6/2016.
 */
@RestController
@RequestMapping("wu")
public class WorkUnitController {

    @Autowired
    WorkUnitRepository workUnitRepository;

    /* Select a work unit by its name */
    @RequestMapping(value = "getwun", produces = "application/json")
    public List<WorkUnits> getOrgByName(@RequestParam("wun") String wuname) {
        return this.workUnitRepository.getWorkUnitsByName(wuname);
    }

    /* Select a work unit by its Work Unit ID */
    @RequestMapping(value = "getwuid", produces = "application/json")
    public WorkUnits getWorkUnitByWUID(@RequestParam("wui") String wuid) {
        Integer wuidint = Integer.parseInt(wuid);
        return this.workUnitRepository.getWorkUnitByWUID(wuidint);
    }

    /* Select a work unit by its Work Unit Code */
    @RequestMapping(value = "getwuc", produces = "application/json")
    public WorkUnits getWorkUnitByWUCode(@RequestParam("wuc") String workunitcode) {
        Integer workunitcodeint = Integer.parseInt(workunitcode);
        return this.workUnitRepository.getWorkUnitByWUCode(workunitcodeint);
    }

    /* Select a work unit by its database ID */
    @RequestMapping(value = "getwudbid", produces = "application/json")
    public WorkUnits getWorkUnitByID(@RequestParam("wudbid") String workunitdbid) {
        Long workunitdbidL = Long.parseLong(workunitdbid);
        return this.workUnitRepository.getWorkUnitByID(workunitdbidL);
    }

    /* Create a work unit */
    @RequestMapping(value = "createf", produces = "application/json")
    public String createFullWorkUnit(@RequestParam("cohort") String cohort, @RequestParam("den") String denominator,
                                     @RequestParam("lvl") String level, @RequestParam("mtx") String matrix, @RequestParam("wun")
                                     String wuname, @RequestParam("wucode") String workunitcode, @RequestParam("wui") String
                                     workunitid) {
        Integer wuid = Integer.parseInt(workunitid);
        WorkUnits workUnits = this.workUnitRepository.getWorkUnitByWUID(wuid);
        if(workUnits.getNAME() != null) {
            return "This work unit already exists";
        } else {
            Integer denominatorint = Integer.parseInt(denominator);
            Integer wucodeint = Integer.parseInt(workunitcode);
            Short levelshort = Short.parseShort(level);
            this.workUnitRepository.create(cohort, denominatorint, levelshort, matrix, wuname, wucodeint, wuid);
            return "Created";
        }
    }
}
