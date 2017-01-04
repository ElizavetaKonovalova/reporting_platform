package application.controllers;

import application.models.Programs;
import application.repositories.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("program")
public class ProgramController {

    @Autowired
    private ProgramRepository programRepository;

    /* GETTERS */

    @RequestMapping(value = "get", produces = "application/json")
    public List<Programs> get(@RequestParam("value") String value, @RequestParam("target") String target) {

        target = (target == null) ? target = "": target;

        switch (target) {
            case "prog" : return this.programRepository.getProgramByProgName(value);
            case "mod" : return this.programRepository.getProgramByModName(value);
            case "id": return this.programRepository.getProgramByID(value);
            default: return this.programRepository.getAll();
        }
    }


    /* REMOVALS */


    @RequestMapping(value = {"/remove","/delete"}, produces = "application/json")
    public void remove(@RequestParam("pname") String program_name, @RequestParam("mname") String module_name) {
        this.programRepository.removeProgramByNames(program_name, module_name);
    }


    /* CREATORS */

    @RequestMapping(value = "create", produces = "application/json")
    public String create(@RequestParam("pname") String program_name, @RequestParam("mname") String module_name) throws Exception {
        return this.programRepository.create(program_name, module_name);
    }
}
