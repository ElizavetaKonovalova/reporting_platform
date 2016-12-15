package application.controllers;

import application.models.Organisations;
import application.repositories.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("org")
public class OrganisationController {

    @Autowired
    private OrganisationRepository organisationRepository;

    /* Select an organisation by its Org Name */
    @RequestMapping(value = "gorg", produces = "application/json")
    public Organisations getOrgByOrgName(@RequestParam("name") String orgname) {
        return this.organisationRepository.getOrgByOrgName(orgname);
    }

    /* Select an organisation by its Client Id */
    @RequestMapping(value = "gid", produces = "application/json")
    public Organisations getOrgByID(@RequestParam("id") String clientid) {
        return this.organisationRepository.getOrgByClientID(clientid);
    }

    /* Select an organisation by its Client Name */
    @RequestMapping(value = "gclient", produces = "application/json")
    public Organisations getOrgByClientName(@RequestParam("name") String clientname) {
        return this.organisationRepository.getOrgByClientName(clientname);
    }

    /* Create an organisation */
    @RequestMapping(value = "create", produces = "application/json")
    public String create(@RequestParam("orgname") String orgname, @RequestParam("cname") String clientname) {
        Organisations organisations = this.organisationRepository.getOrgByClientName(clientname);

        if(organisations.getCLIENT_NAME() != null) { return "This organisation already exists"; }
        else {
            this.organisationRepository.create(orgname, clientname);
            return "Created";
        }
    }

    /* Remove an organisation by its Org Name */
    @RequestMapping(value = "rorg", produces = "application/json")
    public void removeByOrgName(@RequestParam("name") String orgname) {
        this.organisationRepository.removeOrgByOrgName(orgname);
    }

    /* Remove an organisation by its Org Name */
    @RequestMapping(value = "rclient", produces = "application/json")
    public void removeByClientName(@RequestParam("name") String clientname) {
        this.organisationRepository.removeOrgByClientName(clientname);
    }

    /* Remove an organisation by its Client Id */
    @RequestMapping(value = "rid", produces = "application/json")
    public void removeByID(@RequestParam("id") String clientid) {
        this.organisationRepository.removeOrgByClientID(clientid);
    }
}
