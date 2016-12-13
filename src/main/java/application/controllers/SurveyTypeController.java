package application.controllers;

import application.models.SurveyTypes;
import application.repositories.SurveyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stype")
public class SurveyTypeController {

    @Autowired
    SurveyTypeRepository surveyTypeRepository;

    /* Select a surveysubtype by its ID */
    @RequestMapping(value = "gid", produces = "application/json")
    public SurveyTypes getSurveyTypeByID(@RequestParam("id") String id) {
        return this.surveyTypeRepository.getByID(id);
    }

    /* Select a list of surveysubtypes by their Type Name */
    @RequestMapping(value = "gtype", produces = "application/json")
    public SurveyTypes getSurveyTypeByTypeName(@RequestParam("type") String typename) {
        return this.surveyTypeRepository.getByTypeName(typename);
    }

    /* Select a a list of surveysubtypes by their SubType Name */
    @RequestMapping(value = "gstype", produces = "application/json")
    public SurveyTypes getSurveyTypeBySubTypeName(@RequestParam("stype") String subtype) {
        return this.surveyTypeRepository.getBySubTypeName(subtype);
    }

    /* Create a work unit */
    @RequestMapping(value = "create", produces = "application/json")
    public String createFullWorkUnit(@RequestParam("type") String typename, @RequestParam("stype") String subtypename) {
        SurveyTypes surveyTypes = this.surveyTypeRepository.getBySubTypeName(subtypename);
        if(surveyTypes.getSUBTYPE_NAME() != null) {
            return "This work unit already exists";
        } else {
            this.surveyTypeRepository.create(subtypename, typename);
            return "Created";
        }
    }

    /* Remove a survey type by its ID */
    @RequestMapping(value = "rid", produces = "application/json")
    public void removeByName(@RequestParam("id") String id) {
        this.surveyTypeRepository.removeSubTypeByID(id);
    }

    /* Remove a survey type by its Survey SubType Name */
    @RequestMapping(value = "rstype", produces = "application/json")
    public void removeBySubTypeName(@RequestParam("stype") String stypename) {
        this.surveyTypeRepository.removeSubTypeBySubName(stypename);
    }

    /* Remove a survey type by its Survey Type Name */
    @RequestMapping(value = "rtype", produces = "application/json")
    public void removeByTypeName(@RequestParam("type") String typename) {
        this.surveyTypeRepository.removeSubTypeByTypeName(typename);
    }

}
