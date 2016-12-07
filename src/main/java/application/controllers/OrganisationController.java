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
    @RequestMapping(value = "gname", produces = "application/json")
    public Organisations getOrgByName(@RequestParam("name") String orgname) {
        return this.organisationRepository.getOrgByName(orgname);
    }

    /* Select an organisation by its id */
    @RequestMapping(value = "gid", produces = "application/json")
    public Organisations getOrgByID(@RequestParam("id") String orgid) {
        return this.organisationRepository.getOrgByID(orgid);
    }

    /* Select an organisation by its location */
    @RequestMapping(value = "gloc", produces = "application/json")
    public Organisations getOrgByLocation(@RequestParam("loc") String location) {
        return this.organisationRepository.getOrgByLocation(location);
    }

    /* Create an organisation */
    @RequestMapping(value = "create", produces = "application/json")
    public String create(@RequestParam("orgname") String orgname, @RequestParam("cname") String clientname) {
        Organisations organisations = this.organisationRepository.getOrgByName(clientname);

        if(organisations.getCLIENTNAME() != null) { return "This organisation already exists"; }
        else {
            this.organisationRepository.create(orgname, clientname);
            return "Created";
        }
    }

    /* Remove an organisation by its name */
    @RequestMapping(value = "rname", produces = "application/json")
    public void removeByName(@RequestParam("name") String orgname) {
        this.organisationRepository.removeOrgByName(orgname);
    }

    /* Remove an organisation by its Client Id */
    @RequestMapping(value = "rcid", produces = "application/json")
    public void removeByID(@RequestParam("id") String clientid) {
        Long id = Long.parseLong(clientid);
        this.organisationRepository.removeOrgByClientID(id);
    }
}
