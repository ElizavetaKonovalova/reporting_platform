package application.controllers;

import application.models.Organisations;
import application.repositories.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("client")
public class OrganisationController {

    @Autowired
    private OrganisationRepository organisationRepository;


    /* GETTERS */

    /* Main selector method */
    @RequestMapping(value = "get", produces = "application/json")
    public List<Organisations> get(@RequestParam(value = "find", required = false) String find,
                                   @RequestParam(value = "target", required = false) String target) {

        target = (target == null) ? target = "": target;

        switch (target) {

            /* Select an organisation by its Org Name */
            case "orgname": return this.organisationRepository.getOrgByOrgName(find);

            /* Select an organisation by its Client Id */
            case "cid": return this.organisationRepository.getOrgByClientID(find);

            /* Select an organisation by its Client Name */
            case "cname": return this.organisationRepository.getOrgByClientName(find);

            /* Select all Clients */
            default: return this.organisationRepository.getAll();
        }
    }


    /* REMOVALS */


    /* Main removal method */
    @RequestMapping(value = "remove", produces = "application/json")
    public void remove(@RequestParam("find") String find, @RequestParam("target") String target) {

        switch(target) {

            /* Remove an organisation by its Org Name */
            case "orgname": this.organisationRepository.removeOrgByOrgName(find);
            break;

            /* Remove an organisation by its Client Name */
            case "cname": this.organisationRepository.removeOrgByClientName(find);
            break;

            /* Remove an organisation by its Client Id */
            case "cid": this.organisationRepository.removeOrgByClientID(find);
            break;
        }
    }



    /* UPDATERS */


    /* CREATORS */

    /* Create an organisation */
    @RequestMapping(value = "create", produces = "application/json")
    public String create(@RequestParam("orgname") String org_name, @RequestParam("cname") String client_name) throws Exception {

        if(this.organisationRepository.checkCombinationClientOrgExists(client_name, org_name)) {
            return "This organisation is already associated with this client!";
        } else {
            this.organisationRepository.create(org_name, client_name);
            return "Created";
        }
    }
}
