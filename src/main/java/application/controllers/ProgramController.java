package application.controllers;

import application.models.Programs;
import application.repositories.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("program")
public class ProgramController {

    @Autowired
    private ProgramRepository programRepository;

    @RequestMapping(value = "gname", produces = "application/json")
    public Programs getProgramByName(@RequestParam("name") String program_name) {
        return this.programRepository.getProgramByName(program_name);
    }

    @RequestMapping(value = "gid", produces = "application/json")
    public Programs getProgramByID(@RequestParam("id") String program_id) {
        return this.programRepository.getProgramByID(program_id);
    }

    @RequestMapping(value = "create", produces = "application/json")
    public String create(@RequestParam("pname") String program_name, @RequestParam("mname") String module_name) throws Exception {
        return this.programRepository.create(program_name, module_name);
    }
}
