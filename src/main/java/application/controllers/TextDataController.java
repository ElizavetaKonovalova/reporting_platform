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
    @RequestMapping(value = "nredfs", produces = "application/json")
    public void nullRedFlagStat(@RequestParam("status") String field_id) {
        this.textDataRepository.nullRedFlagStatus(field_id);
    }

    /* Set the Shadow Status to Null for a particular Field ID */
    @RequestMapping(value = "nshads", produces = "application/json")
    public void nullShadowStat(@RequestParam("status") String field_id) {
        this.textDataRepository.nullShadowStatus(field_id);
    }


    /* REMOVALS */

    /* Remove data by a database ID */
    @RequestMapping(value = "rdb", produces = "application/json")
    public void removeTextDataByDBID(@RequestParam("id") String db_id) {
        this.textDataRepository.removeTextDataByDBID(db_id);
    }

    /* Remove data by a Participant ID */
    @RequestMapping(value = "rpid", produces = "application/json")
    public void removeTextDataByParticipantID(@RequestParam("id") String participant_id) {
        this.textDataRepository.removeTextDataByPartID(participant_id);
    }

    /* Remove data by a Date Modified */
    @RequestMapping(value = "rdm", produces = "application/json")
    public void removeTextDataByDate(@RequestParam("date") String date_modified) throws Exception {
        this.textDataRepository.removeTextDataByDateModif(date_modified);
    }

    /* Remove data by a Red Flag Status */
    @RequestMapping(value = "rredfs", produces = "application/json")
    public void removeTextDataByRedFlagStat(@RequestParam("status") String redflag_stat) {
        this.textDataRepository.removeTextDataByRedFlagSt(redflag_stat);
    }

    /* Remove data by a Shadow Status */
    @RequestMapping(value = "rshads", produces = "application/json")
    public void removeTextDataByShadowStat(@RequestParam("status") String shadow_stat) {
        this.textDataRepository.removeTextDataByShadowStat(shadow_stat);
    }

    /* Remove data by a Response Value */
    @RequestMapping(value = "rrv", produces = "application/json")
    public void removeTextDataByResponseValue(@RequestParam("value") String response_value) {
        this.textDataRepository.removeTextDataByResponse(response_value);
    }

    /* Remove data by a Field ID */
    @RequestMapping(value = "rfid", produces = "application/json")
    public void removeTextDataByFieldID(@RequestParam("id") String field_id) {
        this.textDataRepository.removeTextDataByFieldID(field_id);
    }

    /* Remove data by a Participant Email */
    @RequestMapping(value = "rpemail", produces = "application/json")
    public void removeTextDataByParticipantEmail(@RequestParam("email") String participant_email) {
        this.textDataRepository.removeTextDataByParticipantEmail(participant_email);
    }
    /* Remove data by a Field Name */
    @RequestMapping(value = "rfname", produces = "application/json")
    public void removeTextDataByFieldName(@RequestParam("name") String field_name) {
        this.textDataRepository.removeTextDataByFieldName(field_name);
    }

}
