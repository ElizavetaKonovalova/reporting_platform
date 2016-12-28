package application.controllers;

import application.models.FieldRegistry;
import application.repositories.FieldRegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("register")
public class FieldRegistryController {

    @Autowired
    private FieldRegistryRepository fieldRegistryRepository;

    /* GETTERS */


    @RequestMapping(value = "get", produces = "application/json")
    public List<FieldRegistry> get(@RequestParam(value = "value") String value,
                                   @RequestParam(value = "module", required = false) String module,
                                   @RequestParam(value = "target") String target) {
        switch (target) {
            case "name": return this.fieldRegistryRepository.getFieldByID(value);
            case "id": return this.fieldRegistryRepository.getFieldByName(value);
            case "pid": return this.fieldRegistryRepository.getFieldByProgramID(value);
            case "pname": return this.fieldRegistryRepository.getFieldByProgramName(value, module);
            case "desc": return this.fieldRegistryRepository.getFieldByDescriptions(value);
            case "type": return this.fieldRegistryRepository.getFieldByType(value);
            default: return new ArrayList<>();
        }
    }


    /* UPDATERS */


    @RequestMapping(value = "update", produces = "application/json")
    public void update(@RequestParam("find") String find_parameter,
                       @RequestParam("new") String new_parameter,
                       @RequestParam(value = "module", required = false) String module,
                       @RequestParam("target") String target) {

        switch (target) {
            case "idname": this.fieldRegistryRepository.updateFieldNameByID(find_parameter, new_parameter);
            break;
            case "namename": this.fieldRegistryRepository.updateFieldNameByName(find_parameter, new_parameter);
            break;
            case "d1name": this.fieldRegistryRepository.updateFieldDescriptionOneByName(find_parameter, new_parameter);
            break;
            case "d2name": this.fieldRegistryRepository.updateFieldDescriptionTwoByName(find_parameter, new_parameter);
            break;
            case "d3name": this.fieldRegistryRepository.updateFieldDescriptionThreeByName(find_parameter, new_parameter);
            break;
            case "typename": this.fieldRegistryRepository.updateFieldTypeByName(find_parameter, new_parameter);
            break;
            case "progname": this.fieldRegistryRepository.updateProgramIDByName(find_parameter, new_parameter, module);
            break;
            default: break;
        }
    }



    /* REMOVALS */

    @RequestMapping(value = {"/remove", "/delete"}, produces = "application/json")
    public void remove(@RequestParam("value") String value, @RequestParam("target") String target,
                       @RequestParam(value = "module", required = false) String module) {

        switch (target) {
            case "id": this.fieldRegistryRepository.removeByFieldID(value); break;
            case "name": this.fieldRegistryRepository.removeByFieldName(value); break;
            case "type": this.fieldRegistryRepository.removeByFieldType(value); break;
            case "program": this.fieldRegistryRepository.removeByFieldProgram(value, module); break;
            default: break;
        }
    }

    /* NULLERS */

    @RequestMapping(value = "null", produces = "application/json")
    public void nuller(@RequestParam("value") String value, @RequestParam("target") String target) {

        switch (target) {
            case "d1": this.fieldRegistryRepository.nullDescriptionOne(value); break;
            case "d2": this.fieldRegistryRepository.nullDescriptionTwo(value); break;
            case "d3": this.fieldRegistryRepository.nullDescriptionThree(value); break;
            case "program": this.fieldRegistryRepository.nullProgram(value); break;
            case "type": this.fieldRegistryRepository.nullType(value); break;
            default: break;
        }
    }

    /* CREATORS */
    @RequestMapping(value = "create", produces = "application/json")
    public String create(@RequestParam(value = "fdone", required = false) String field_desc_one,
                         @RequestParam(value = "fdtwo", required = false) String field_desc_two,
                         @RequestParam(value = "fdthr", required = false) String field_desc_three,
                         @RequestParam("fname") String field_name, @RequestParam(value = "pname") String program_name,
                         @RequestParam(value = "mname") String module_name,@RequestParam(value = "type") String type) {
        return this.fieldRegistryRepository.create(field_desc_one, field_desc_two, field_desc_three, field_name, program_name,
                module_name, type);
    }
}
