package application.controllers;

import application.models.Organisations;
import application.repositories.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ekonovalova on 12/6/2016.
 */
@RestController
@RequestMapping("org")
public class OrganisationController {

    @Autowired
    OrganisationRepository organisationRepository;

    /* Select an organisation by its name */
    @RequestMapping(value = "geton", produces = "application/json")
    public Organisations getOrgByName(@RequestParam("on") String orgname) {
        return this.organisationRepository.getOrgByName(orgname);
    }

    /* Select an organisation by its id */
    @RequestMapping(value = "getoi", produces = "application/json")
    public Organisations getOrgByID(@RequestParam("oi") String orgid) {
        Long id = Long.parseLong(orgid);
        return this.organisationRepository.getOrgByID(id);
    }

    /* Select an organisation by its location */
    @RequestMapping(value = "getol", produces = "application/json")
    public Organisations getOrgByLocation(@RequestParam("ol") String location) {
        return this.organisationRepository.getOrgByLocation(location);
    }

    /* Create an organisation */
    @RequestMapping(value = "create", produces = "application/json")
    public String create(@RequestParam("on") String orgname, @RequestParam("loc") String location) {
        Organisations organisations = this.organisationRepository.getOrgByName(orgname);

        if(organisations.getORGNAME() != null) { return "This organisation already exists"; }
        else {
            this.organisationRepository.create(orgname, location);
            return "Created";
        }
    }

    /* Remove an organisation by its name */
    @RequestMapping(value = "remon", produces = "application/json")
    public void removeByName(@RequestParam("on") String orgname) {
        this.organisationRepository.removeOrgByName(orgname);
    }

    /* Remove an organisation by its id */
    @RequestMapping(value = "remoi", produces = "application/json")
    public void removeByID(@RequestParam("oi") String orgid) {
        Long id = Long.parseLong(orgid);
        this.organisationRepository.removeOrgByID(id);
    }
}
