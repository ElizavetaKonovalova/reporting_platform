package application.controllers;

import application.models.TextData;
import application.repositories.TextDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("td")
public class TextDataController {

    @Autowired
    private TextDataRepository textDataRepository;

    /* CREATORS */

    /* Create a new text row */
    @RequestMapping(value = "create", produces = "application/json")
    public String create(@RequestParam("redfs") String red_flag_status, @RequestParam("rv") String response_value,
                         @RequestParam("shads") String shadow_status, @RequestParam("fname") String text_field_name,
                         @RequestParam("pemail") String participant_email) throws Exception {
        return this.textDataRepository.createTextData(red_flag_status, response_value, shadow_status, text_field_name, participant_email);
    }

    /* GETTERS */

    /* Find text data by a Field Name */
    @RequestMapping(value = "gfname", produces = "application/json")
    public List<TextData> getFieldByName(@RequestParam("name") String field_name) {
        return this.textDataRepository.getDataByFieldName(field_name);
    }

    /* Find text data by Response Values */
    @RequestMapping(value = "grv", produces = "application/json")
    public List<TextData> getFieldByResponseValue(@RequestParam("value") String response_value) {
        return this.textDataRepository.getDataByResponseValue(response_value);
    }

    /* Find text data by Red Flag Statuses */
    @RequestMapping(value = "gredfs", produces = "application/json")
    public List<TextData> getFieldByRedFlagStat(@RequestParam("status") String redflag_status) {
        return this.textDataRepository.getDataByRedFlagStat(redflag_status);
    }

    /* Find text data by Shadow Statuses */
    @RequestMapping(value = "gshads", produces = "application/json")
    public List<TextData> getFieldByShadowStat(@RequestParam("status") String shadow_status) {
        return this.textDataRepository.getDataByShadowStat(shadow_status);
    }

    /* Find text data by a Field ID */
    @RequestMapping(value = "gfid", produces = "application/json")
    public List<TextData> getFieldByFieldID(@RequestParam("id") String field_id) {
        return this.textDataRepository.getDataByFieldID(field_id);
    }

    /* Find text data by a Participant ID */
    @RequestMapping(value = "gpid", produces = "application/json")
    public List<TextData> getFieldByParticipantID(@RequestParam("id") String participant_id) {
        return this.textDataRepository.getDataByParticipantID(participant_id);
    }

    /* Find text data by a Date Modified */
    @RequestMapping(value = "gdm", produces = "application/json")
    public List<TextData> getFieldByDate(@RequestParam("date") String date_modified) throws Exception {
        return this.textDataRepository.getDataByDate(date_modified);
    }

    /* Find text data by a database ID */
    @RequestMapping(value = "gdb", produces = "application/json")
    public List<TextData> getFieldByDBID(@RequestParam("id") String db_id) {
        return this.textDataRepository.getDataByDBID(db_id);
    }

    /* Find text data by a Participant Email */
    @RequestMapping(value = "gpemail", produces = "application/json")
    public List<TextData> getFieldByPartEmail(@RequestParam("email") String participant_email) {
        return this.textDataRepository.getDataByParticipantEmail(participant_email);
    }


    /* NULLERS */

    /* Set the Red Flag Status to Null for a particular Field ID */
    public void nullRedFlagStat(String field_id) {
        this.textDataRepository.nullRedFlagStatus(field_id);
    }

    /* Set the Shadow Status to Null for a particular Field ID */
    public void nullShadowStat(String field_id) {
        this.textDataRepository.nullShadowStatus(field_id);
    }


    /* REMOVALS */
}
