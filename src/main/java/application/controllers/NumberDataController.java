package application.controllers;

import application.models.NumberData;
import application.repositories.NumberDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("nd")
public class NumberDataController {

    @Autowired
    private NumberDataRepository numberDataRepository;

    /* Create a new Number row */
    @RequestMapping(value = "create", produces = "application/json")
    private String create(@RequestParam("fname") String field_name, @RequestParam("pemail") String participant_email,
                          @RequestParam("posit") String positivity_result, @RequestParam("respon") String response_value) throws Exception {
        return this.numberDataRepository.create(field_name, participant_email, positivity_result, response_value);
    }

    /* Find results by Participant IDs */
    @RequestMapping(value = "gpart", produces = "application/json")
    public List<NumberData> getFieldByName(@RequestParam("id") String participant_id) {
        return this.numberDataRepository.getNumberDataByParticipantID(participant_id);
    }
}
