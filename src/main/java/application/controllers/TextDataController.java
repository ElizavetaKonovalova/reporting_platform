package application.controllers;

import application.models.TextData;
import application.repositories.TextDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("td")
public class TextDataController {

    @Autowired
    private TextDataRepository textDataRepository;

    @RequestMapping(value = "create", produces = "application/json")
    public String create(@RequestParam("redfstat") String red_flag_status, @RequestParam("rv") String response_value,
                         @RequestParam("shadow") String shadow_status, @RequestParam("fname") String text_field_name,
                         @RequestParam("pemail") String participant_email) throws Exception {
        return this.textDataRepository.createTextData(red_flag_status, response_value, shadow_status, text_field_name, participant_email);
    }

    @RequestMapping(value = "gfname", produces = "application/json")
    public TextData getFieldByName(@RequestParam("name") String field_name) {
        return this.textDataRepository.getDataByFieldName(field_name);
    }
}
