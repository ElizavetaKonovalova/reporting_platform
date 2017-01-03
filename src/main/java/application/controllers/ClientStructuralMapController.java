package application.controllers;

import application.models.ClientsStructuralMaps;
import application.repositories.ClientStructuralMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("cwu")
public class ClientStructuralMapController {

    @Autowired
    private ClientStructuralMapRepository clientStructuralMapRepository;

    /* GETTERS */

    @RequestMapping(value = "get", produces = "application/json")
    public List<ClientsStructuralMaps> get(@RequestParam(value = "find", required = false) String find, @RequestParam("client") String client_name,
                                           @RequestParam(value = "target", required = false) String target) {

        target = (target == null) ? target = "": target;

        switch (target) {

            /* Select a Work Unit by its Name */
            case "wuname": return this.clientStructuralMapRepository.getClientsStructuralMapsByName(find, client_name);

            /* Select a Work Unit by its Work Unit ID */
            case "wuid": return this.clientStructuralMapRepository.getWorkUnitByWUID(find, client_name);

            /* Select a Work Unit by its database ID */
            case "dbid": this.clientStructuralMapRepository.getWorkUnitByDBID(find,client_name);

            /* Select a Work Unit by its Level */
            case "lvl": return this.clientStructuralMapRepository.getClientsStructuralMapsByLevel(find,client_name);

            /* Select a Work Unit by its Matrix */
            case "mtx": return this.clientStructuralMapRepository.getClientsStructuralMapsByMatrix(find,client_name);

            /* Select a Work Unit by its Location */
            case "loc": return this.clientStructuralMapRepository.getClientsStructuralMapsByLocation(find,client_name);

            /* Select a Work Unit by its Cohort */
            case "coh": return this.clientStructuralMapRepository.getClientsStructuralMapsByCohort(find,client_name);

            /* Select a Work Unit by its Niche */
            case "niche": return this.clientStructuralMapRepository.getClientsStructuralMapsByNiche(find,client_name);

            /* Select a Work Unit by its Sector */
            case "sector": return this.clientStructuralMapRepository.getClientsStructuralMapsBySector(find,client_name);

            /* Select Work Units by their Client ID */
            case "cid": return this.clientStructuralMapRepository.getClientsStructuralMapsByClientID(find,client_name);

            /* Select ALL Work Units in a Client table */
            default: return this.clientStructuralMapRepository.getAll(client_name);
        }
    }


    /* REMOVALS */


    @RequestMapping(value = {"remove", "delete"}, produces = "application/json")
    public void remove(@RequestParam("find") String find, @RequestParam("cname") String client_name, @RequestParam("target") String target) {

        switch (target) {

            /* Remove Work Units by their Name */
            case "wuname": this.clientStructuralMapRepository.removeWUByName(find, client_name);break;

            /* Remove Work Units by their Work Unit ID */
            case "wuid": this.clientStructuralMapRepository.removeWUByWUID(find, client_name);break;

            /* Remove Work Units by their database ID */
            case "dbid": this.clientStructuralMapRepository.removeWUByDBID(find, client_name);break;

            /* Remove Work Units by their Client ID */
            case "cid": this.clientStructuralMapRepository.removeWUByClientID(find, client_name);break;

            /* Remove Work Units by their Location */
            case "loc": this.clientStructuralMapRepository.removeWUByLocation(find, client_name);break;

            /* Remove Work Units by their Cohort */
            case "coh": this.clientStructuralMapRepository.removeWUByCohort(find, client_name);break;

            /* Remove Work Units by their Sector */
            case "sector": this.clientStructuralMapRepository.removeWUBySector(find, client_name);break;

            /* Remove Work Units by their Niche */
            case "niche": this.clientStructuralMapRepository.removeWUByNiche(find, client_name);break;
            default: break;
        }
    }


    /* NULLERS */


    @RequestMapping(value = "null", produces = "application/json")
    public void nuller(@RequestParam("find") String find, @RequestParam("cname") String client_name,
                       @RequestParam("target") String target) throws Exception {

        switch (target) {

            /* Null Matrix One by its Name */
            case "mtx1": this.clientStructuralMapRepository.nullMatrixOne(find, client_name);break;

            /* Null Matrix Two by its Name */
            case "mtx2": this.clientStructuralMapRepository.nullMatrixTwo(find, client_name);break;

            /* Null Matrix Three by its Name */
            case "mtx3": this.clientStructuralMapRepository.nullMatrixThree(find, client_name);break;

            /* Null Matrix Four by its Name */
            case "mtx4": this.clientStructuralMapRepository.nullMatrixFour(find, client_name);break;

            /* Null Matrix Five by its Name */
            case "mtx5": this.clientStructuralMapRepository.nullMatrixFive(find, client_name);break;

            /* Null Level Zero by its Name */
            case "lvl0": this.clientStructuralMapRepository.nullLevelZero(find, client_name);break;

            /* Null Level One by its Name */
            case "lvl1": this.clientStructuralMapRepository.nullLevelOne(find, client_name);break;

            /* Null Level Two by its Name */
            case "lvl2": this.clientStructuralMapRepository.nullLevelTwo(find, client_name);break;

            /* Null Level Three by its Name */
            case "lvl3": this.clientStructuralMapRepository.nullLevelThree(find, client_name);break;

            /* Null Level Four by its Name */
            case "lvl4": this.clientStructuralMapRepository.nullLevelFour(find, client_name);break;

            /* Null Level Five by its Name */
            case "lvl5": this.clientStructuralMapRepository.nullLevelFive(find, client_name);break;

            /* Null Niche by its Name */
            case "niche": this.clientStructuralMapRepository.nullNiche(find, client_name);break;

            /* Null Cohort by its Name */
            case "coh": this.clientStructuralMapRepository.nullCohort(find, client_name);break;

            /* Null Sector by its Name */
            case "sector": this.clientStructuralMapRepository.nullSector(find, client_name);break;

            /* Null Cohort by its Name */
            case "loc": this.clientStructuralMapRepository.nullLocation(find, client_name);break;
            default: break;
        }
    }



    /* UPDATERS */



    /* CREATORS */


    /* Create a new Client Table */
    @RequestMapping(value = "createtable", produces = "application/json")
    public String createTable(@RequestParam("cname") String client_name) {
        return this.clientStructuralMapRepository.createAClientTable(client_name);
    }

    /* Create a new Work Unit */
    @RequestMapping(value = "create", produces = "application/json")
    public String createWU(@RequestParam(value = "cohort", required = false) String cohort, @RequestParam(value = "loc", required = false) String location,
                           @RequestParam(value = "niche", required = false) String niche, @RequestParam(value = "m1", required = false) String matrixone,
                           @RequestParam(value = "m2", required = false) String matrixtwo, @RequestParam(value = "m3", required = false) String matrixthree,
                           @RequestParam(value = "m4", required = false) String matrixfour, @RequestParam(value = "m5", required = false) String matrixfive,
                           @RequestParam("wun") String wu_name, @RequestParam("wuid") String wu_id,
                           @RequestParam(value = "sector", required = false) String sector, @RequestParam(value = "lvl0", required = false) String wu_level_zero,
                           @RequestParam(value = "lvl1", required = false) String wu_level_one, @RequestParam(value = "lvl2", required = false) String wu_level_two,
                           @RequestParam(value = "lvl3", required = false) String wu_level_three, @RequestParam(value = "lvl4", required = false) String wu_level_four,
                           @RequestParam(value = "lvl5", required = false) String wu_level_five, @RequestParam("cname") String client_name) throws Exception {

        return this.clientStructuralMapRepository.create(cohort, location, niche, matrixone, matrixtwo, matrixthree,
                matrixfour, matrixfive, wu_name, wu_id, sector, wu_level_five, wu_level_four, wu_level_one, wu_level_three,
                wu_level_two, wu_level_zero, client_name);
    }
}
