package application.controllers;

import application.models.ClientsStructuralMaps;
import application.repositories.ClientStructuralMapsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("cwu")
public class ClientStructuralMapController {

    @Autowired
    ClientStructuralMapsRepository clientStructuralMapsRepository;

    /* GETTERS */

    /* Select a Work Unit by its Name */
    @RequestMapping(value = "gname", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByName(@RequestParam("name") String wu_name) {
        return this.clientStructuralMapsRepository.getClientsStructuralMapsByName(wu_name);
    }

    /* Select a Work Unit by its Work Unit ID */
    @RequestMapping(value = "gwui", produces = "application/json")
    public ClientsStructuralMaps getClientStructuralMapsByWUID(@RequestParam("id") String wu_id) {
        return this.clientStructuralMapsRepository.getWorkUnitByWUID(wu_id);
    }

    /* Select a Work Unit by its database ID */
    @RequestMapping(value = "gdb", produces = "application/json")
    public ClientsStructuralMaps getClientStructuralMapsByDBID(@RequestParam("id") String db_id) {
        return this.clientStructuralMapsRepository.getWorkUnitByDBID(db_id);
    }

    /* Select a Work Unit by its Level */
    @RequestMapping(value = "glvl", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByLevel(@RequestParam("name") String level) {
        return this.clientStructuralMapsRepository.getClientsStructuralMapsByLevel(level);
    }

    /* Select a Work Unit by its Matrix */
    @RequestMapping(value = "gmtx", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByMatrix(@RequestParam("name") String matrix) {
        return this.clientStructuralMapsRepository.getClientsStructuralMapsByMatrix(matrix);
    }

    /* Select a Work Unit by its Location */
    @RequestMapping(value = "gloc", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByLocation(@RequestParam("name") String location) {
        return this.clientStructuralMapsRepository.getClientsStructuralMapsByLocation(location);
    }

    /* Select a Work Unit by its Cohort */
    @RequestMapping(value = "gcohort", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByCohort(@RequestParam("name") String cohort) {
        return this.clientStructuralMapsRepository.getClientsStructuralMapsByCohort(cohort);
    }

    /* Select a Work Unit by its Niche */
    @RequestMapping(value = "gniche", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByNiche(@RequestParam("name") String niche) {
        return this.clientStructuralMapsRepository.getClientsStructuralMapsByNiche(niche);
    }

    /* Select a Work Unit by its Sector */
    @RequestMapping(value = "gsector", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsBySector(@RequestParam("name") String sector) {
        return this.clientStructuralMapsRepository.getClientsStructuralMapsBySector(sector);
    }

    /* Select Work Units by their Client ID */
    @RequestMapping(value = "gclient", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByClientID(@RequestParam("id") String client_id) {
        return this.clientStructuralMapsRepository.getClientsStructuralMapsByClientID(client_id);
    }


    /* REMOVALS */

    /* Remove Work Units by their Name */
    @RequestMapping(value = "rwun", produces = "application/json")
    public void removeWUByName(@RequestParam("name") String wu_name) {
        this.clientStructuralMapsRepository.removeWUByName(wu_name);
    }

    /* Remove Work Units by their Work Unit ID */
    @RequestMapping(value = "rwui", produces = "application/json")
    public void removeWUByWUID(@RequestParam("id") String wu_id) {
        this.clientStructuralMapsRepository.removeWUByWUID(wu_id);
    }

    /* Remove Work Units by their database ID */
    @RequestMapping(value = "rdb", produces = "application/json")
    public void removeWUByDBID(@RequestParam("id") String db_id) {
        this.clientStructuralMapsRepository.removeWUByDBID(db_id);
    }

    /* Remove Work Units by their Client ID */
    @RequestMapping(value = "rclient", produces = "application/json")
    public void removeWUByClientID(@RequestParam("id") String client_id) {
        this.clientStructuralMapsRepository.removeWUByClientID(client_id);
    }

    /* Remove Work Units by their Location */
    @RequestMapping(value = "rloc", produces = "application/json")
    public void removeWUByLocation(@RequestParam("name") String location) {
        this.clientStructuralMapsRepository.removeWUByLocation(location);
    }

    /* Remove Work Units by their Cohort */
    @RequestMapping(value = "rcohort", produces = "application/json")
    public void removeWUByCohort(@RequestParam("name") String cohort) {
        this.clientStructuralMapsRepository.removeWUByCohort(cohort);
    }

    /* Remove Work Units by their Sector */
    @RequestMapping(value = "rsector", produces = "application/json")
    public void removeWUBySector(@RequestParam("name") String sector) {
        this.clientStructuralMapsRepository.removeWUBySector(sector);
    }

    /* Remove Work Units by their Niche */
    @RequestMapping(value = "rsector", produces = "application/json")
    public void removeWUByNiche(@RequestParam("name") String niche) {
        this.clientStructuralMapsRepository.removeWUByNiche(niche);
    }



    /* CREATORS */
    @RequestMapping(value = "create", produces = "application/json")
    public void create(@RequestParam("cohort") String cohort, @RequestParam("loc") String location, @RequestParam("niche") String niche,
                       @RequestParam("m1") String matrixone, @RequestParam("m2") String matrixtwo,
                       @RequestParam("m3") String matrixthree, @RequestParam("m4") String matrixfour,
                       @RequestParam("m5") String matrixfive, @RequestParam("wun") String wu_name, @RequestParam("wuid") String wu_id,
                       @RequestParam("sector") String sector, @RequestParam("lvl0") String wu_level_zero,
                       @RequestParam("lvl1") String wu_level_one, @RequestParam("lvl2") String wu_level_two,
                       @RequestParam("lvl3") String wu_level_three, @RequestParam("lvl4") String wu_level_four,
                       @RequestParam("lvl5") String wu_level_five, @RequestParam("cid") String client_id) throws Exception {

        this.clientStructuralMapsRepository.create(cohort, location, niche, matrixone, matrixtwo, matrixthree,
                matrixfour, matrixfive, wu_name, wu_id, sector, wu_level_five, wu_level_four, wu_level_one, wu_level_three,
                wu_level_two, wu_level_zero, client_id);
    }
}
