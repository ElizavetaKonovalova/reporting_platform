package application.controllers;

import application.models.SurveyTypes;
import application.repositories.SurveyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("stype")
public class SurveyTypeController {

    @Autowired
    private SurveyTypeRepository surveyTypeRepository;

    /* CREATORS */

    @RequestMapping(value = "create", produces = "application/json")
    public String create(@RequestParam("type") String type_name, @RequestParam("sub") String sub_type_name) throws Exception {
        return this.surveyTypeRepository.create(type_name, sub_type_name);
    }

    /* GETTERS */

    @RequestMapping(value = {"get","find"}, produces = "application/json")
    public List<SurveyTypes> get(@RequestParam("find") String find, @RequestParam("target") String target) {

        target = (target == null) ? target = "": target;

        switch (target) {

            /* Select a a list of Survey Subtypes by their SubType Name */
            case "sub": return this.surveyTypeRepository.getBySubTypeName(find);

            /* Select a a list of Survey Subtypes by their Type Name */
            case "type": return this.surveyTypeRepository.getByTypeName(find);

            /* Select a a list of Survey Subtypes by their ID */
            case "id": return this.surveyTypeRepository.getByID(find);

            /* Select All Types by default */
            default: return this.surveyTypeRepository.getAll();
        }
    }


    /* UPDATERS */



    /* REMOVALS */

    @RequestMapping(value = {"remove","delete"}, produces = "application/json")
    public void remove(@RequestParam("find") String find,@RequestParam("target") String target) {
        switch (target) {

            /* Remove a survey type by its ID */
            case "id": this.surveyTypeRepository.removeSubTypeByID(find);break;

            /* Remove a survey type by its Survey SubType Name */
            case "sub": this.surveyTypeRepository.removeSubTypeBySubName(find);break;

            /* Remove a survey type by its Survey Type Name */
            case "type": this.surveyTypeRepository.removeSubTypeByTypeName(find);break;
            default: break;
        }
    }
}
