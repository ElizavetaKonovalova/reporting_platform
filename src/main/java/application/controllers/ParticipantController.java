package application.controllers;

import application.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("part")
public class ParticipantController {

    @Autowired
    private ParticipantRepository participantRepository;

    @RequestMapping(value = "create", produces = "application/json")
    public String create(@RequestParam("name") String participant_name, @RequestParam("jc") String job_code,
                         @RequestParam("pas") String password, @RequestParam("stat") String status,
                         @RequestParam("email") String participant_email, @RequestParam("wuc") String wu_code) throws Exception {
        return this.participantRepository.create(participant_email, participant_name, job_code, password, status, wu_code);
    }
}
