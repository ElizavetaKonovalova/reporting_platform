package application.controllers;

import application.models.FieldRegistry;
import application.repositories.FieldRegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fr")
public class FieldRegistryController {

    @Autowired
    private FieldRegistryRepository fieldRegistryRepository;

    @RequestMapping(value = "gname", produces = "application/json")
    public FieldRegistry getFieldByName(@RequestParam("name") String field_name) {
        return this.fieldRegistryRepository.getFieldByName(field_name);
    }

    @RequestMapping(value = "gid", produces = "application/json")
    public FieldRegistry getFieldByID(@RequestParam("id") String field_id) {
        return this.fieldRegistryRepository.getFieldByID(field_id);
    }

    @RequestMapping(value = "create", produces = "application/json")
    public String create(@RequestParam("fdone") String field_desc_one, @RequestParam("fdtwo") String field_desc_two,
                                @RequestParam("fdthr") String field_desc_three, @RequestParam("fname") String field_name,
                                @RequestParam("pname") String program_name, @RequestParam("mname") String module_name, @RequestParam("t") String type) {
        return this.fieldRegistryRepository.create(field_desc_one, field_desc_two, field_desc_three, field_name, program_name, module_name, type);
    }
}
