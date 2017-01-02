package application.controllers;

import application.models.ClientsStructuralMaps;
import application.repositories.ClientStructuralMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("cwu")
public class ClientStructuralMapController {

    @Autowired
    private ClientStructuralMapRepository clientStructuralMapRepository;

    /* GETTERS */

    /* Select a Work Unit by its Name */
    @RequestMapping(value = "gname", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByName(@RequestParam("name") String wu_name) {
        return this.clientStructuralMapRepository.getClientsStructuralMapsByName(wu_name);
    }

    /* Select a Work Unit by its Work Unit ID */
    @RequestMapping(value = "gwui", produces = "application/json")
    public ClientsStructuralMaps getClientStructuralMapsByWUID(@RequestParam("id") String wu_id) {
        return this.clientStructuralMapRepository.getWorkUnitByWUID(wu_id);
    }

    /* Select a Work Unit by its database ID */
    @RequestMapping(value = "gdb", produces = "application/json")
    public ClientsStructuralMaps getClientStructuralMapsByDBID(@RequestParam("id") String db_id) {
        return this.clientStructuralMapRepository.getWorkUnitByDBID(db_id);
    }

    /* Select a Work Unit by its Level */
    @RequestMapping(value = "glvl", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByLevel(@RequestParam("name") String level) {
        return this.clientStructuralMapRepository.getClientsStructuralMapsByLevel(level);
    }

    /* Select a Work Unit by its Matrix */
    @RequestMapping(value = "gmtx", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByMatrix(@RequestParam("name") String matrix) {
        return this.clientStructuralMapRepository.getClientsStructuralMapsByMatrix(matrix);
    }

    /* Select a Work Unit by its Location */
    @RequestMapping(value = "gloc", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByLocation(@RequestParam("name") String location) {
        return this.clientStructuralMapRepository.getClientsStructuralMapsByLocation(location);
    }

    /* Select a Work Unit by its Cohort */
    @RequestMapping(value = "gcohort", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByCohort(@RequestParam("name") String cohort) {
        return this.clientStructuralMapRepository.getClientsStructuralMapsByCohort(cohort);
    }

    /* Select a Work Unit by its Niche */
    @RequestMapping(value = "gniche", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByNiche(@RequestParam("name") String niche) {
        return this.clientStructuralMapRepository.getClientsStructuralMapsByNiche(niche);
    }

    /* Select a Work Unit by its Sector */
    @RequestMapping(value = "gsector", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsBySector(@RequestParam("name") String sector) {
        return this.clientStructuralMapRepository.getClientsStructuralMapsBySector(sector);
    }

    /* Select Work Units by their Client ID */
    @RequestMapping(value = "gclient", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByClientID(@RequestParam("id") String client_id) {
        return this.clientStructuralMapRepository.getClientsStructuralMapsByClientID(client_id);
    }


    /* REMOVALS */

    /* Remove Work Units by their Name */
    @RequestMapping(value = "rwun", produces = "application/json")
    public void removeWUByName(@RequestParam("name") String wu_name) {
        this.clientStructuralMapRepository.removeWUByName(wu_name);
    }

    /* Remove Work Units by their Work Unit ID */
    @RequestMapping(value = "rwui", produces = "application/json")
    public void removeWUByWUID(@RequestParam("id") String wu_id) {
        this.clientStructuralMapRepository.removeWUByWUID(wu_id);
    }

    /* Remove Work Units by their database ID */
    @RequestMapping(value = "rdb", produces = "application/json")
    public void removeWUByDBID(@RequestParam("id") String db_id) {
        this.clientStructuralMapRepository.removeWUByDBID(db_id);
    }

    /* Remove Work Units by their Client ID */
    @RequestMapping(value = "rwuclient", produces = "application/json")
    public void removeWUByClientID(@RequestParam("id") String client_id) {
        this.clientStructuralMapRepository.removeWUByClientID(client_id);
    }

    /* Remove Work Units by their Location */
    @RequestMapping(value = "rwuloc", produces = "application/json")
    public void removeWUByLocation(@RequestParam("name") String location) {
        this.clientStructuralMapRepository.removeWUByLocation(location);
    }

    /* Remove Work Units by their Cohort */
    @RequestMapping(value = "rwucohort", produces = "application/json")
    public void removeWUByCohort(@RequestParam("name") String cohort) {
        this.clientStructuralMapRepository.removeWUByCohort(cohort);
    }

    /* Remove Work Units by their Sector */
    @RequestMapping(value = "rwusector", produces = "application/json")
    public void removeWUBySector(@RequestParam("name") String sector) {
        this.clientStructuralMapRepository.removeWUBySector(sector);
    }

    /* Remove Work Units by their Niche */
    @RequestMapping(value = "rwuniche", produces = "application/json")
    public void removeWUByNiche(@RequestParam("name") String niche) {
        this.clientStructuralMapRepository.removeWUByNiche(niche);
    }

    /* Remove Matrix One by its Name */
    @RequestMapping(value = "rwumtx1", produces = "application/json")
    public void removeMatrixOne(@RequestParam("name") String matrix_one) throws Exception {
        this.clientStructuralMapRepository.nullMatrixOne(matrix_one);
    }

    /* Remove Matrix Two by its Name */
    @RequestMapping(value = "rwumtx2", produces = "application/json")
    public void removeMatrixTwo(@RequestParam("name") String matrix_two) throws Exception {
        this.clientStructuralMapRepository.nullMatrixTwo(matrix_two);
    }

    /* Remove Matrix Three by its Name */
    @RequestMapping(value = "rwumtx3", produces = "application/json")
    public void removeMatrixThree(@RequestParam("name") String matrix_three) throws Exception {
        this.clientStructuralMapRepository.nullMatrixThree(matrix_three);
    }

    /* Remove Matrix Four by its Name */
    @RequestMapping(value = "rwumtx4", produces = "application/json")
    public void removeMatrixFour(@RequestParam("name") String matrix) throws Exception {
        this.clientStructuralMapRepository.nullMatrixFour(matrix);
    }

    /* Remove Matrix Five by its Name */
    @RequestMapping(value = "rwumtx5", produces = "application/json")
    public void removeMatrixFive(@RequestParam("name") String matrix) throws Exception {
        this.clientStructuralMapRepository.nullMatrixFive(matrix);
    }

    /* Remove Level Zero by its Name */
    @RequestMapping(value = "rwulvl0", produces = "application/json")
    public void removeLevelZero(@RequestParam("name") String level) throws Exception {
        this.clientStructuralMapRepository.nullLevelZero(level);
    }

    /* Remove Level One by its Name */
    @RequestMapping(value = "rwulvl1", produces = "application/json")
    public void removeLevelOne(@RequestParam("name") String level) throws Exception {
        this.clientStructuralMapRepository.nullLevelOne(level);
    }

    /* Remove Level Two by its Name */
    @RequestMapping(value = "rwulvl2", produces = "application/json")
    public void removeLevelTwo(@RequestParam("name") String level) throws Exception {
        this.clientStructuralMapRepository.nullLevelTwo(level);
    }

    /* Remove Level Three by its Name */
    @RequestMapping(value = "rwulvl3", produces = "application/json")
    public void removeLevelThree(@RequestParam("name") String level) throws Exception {
        this.clientStructuralMapRepository.nullLevelThree(level);
    }

    /* Remove Level Two by its Name */
    @RequestMapping(value = "rwulvl4", produces = "application/json")
    public void removeLevelFour(@RequestParam("name") String level) throws Exception {
        this.clientStructuralMapRepository.nullLevelFour(level);
    }

    /* Remove Level Two by its Name */
    @RequestMapping(value = "rwulvl5", produces = "application/json")
    public void removeLevelFive(@RequestParam("name") String level) throws Exception {
        this.clientStructuralMapRepository.nullLevelFive(level);
    }

    /* Remove Niche */
    @RequestMapping(value = "rniche", produces = "application/json")
    public void removeNiche(@RequestParam("name") String niche) throws Exception {
        this.clientStructuralMapRepository.nullNiche(niche);
    }

    /* Remove Cohort */
    @RequestMapping(value = "rcohort", produces = "application/json")
    public void removeCohort(@RequestParam("name") String cohort) throws Exception {
        this.clientStructuralMapRepository.nullCohort(cohort);
    }

    /* Remove Sector */
    @RequestMapping(value = "rsector", produces = "application/json")
    public void removeSector(@RequestParam("name") String sector) throws Exception {
        this.clientStructuralMapRepository.nullSector(sector);
    }

    /* Remove Location */
    @RequestMapping(value = "rloc", produces = "application/json")
    public void removeLocation(@RequestParam("name") String location) throws Exception {
        this.clientStructuralMapRepository.nullLocation(location);
    }



    /* CREATORS */


    /* Create a new Client Table */
    @RequestMapping(value = "createtable", produces = "application/json")
    public String createTable(@RequestParam("cname") String client_name) {
        return this.clientStructuralMapRepository.createAClientTable(client_name);
    }

    /* Create a new Work Unit */
    @RequestMapping(value = "create", produces = "application/json")
    public String createWU(@RequestParam("cohort") String cohort, @RequestParam("loc") String location, @RequestParam("niche") String niche,
                       @RequestParam("m1") String matrixone, @RequestParam("m2") String matrixtwo,
                       @RequestParam("m3") String matrixthree, @RequestParam("m4") String matrixfour,
                       @RequestParam("m5") String matrixfive, @RequestParam("wun") String wu_name, @RequestParam("wuid") String wu_id,
                       @RequestParam("sector") String sector, @RequestParam("lvl0") String wu_level_zero,
                       @RequestParam("lvl1") String wu_level_one, @RequestParam("lvl2") String wu_level_two,
                       @RequestParam("lvl3") String wu_level_three, @RequestParam("lvl4") String wu_level_four,
                       @RequestParam("lvl5") String wu_level_five, @RequestParam("cname") String client_name) throws Exception {

        return this.clientStructuralMapRepository.create(cohort, location, niche, matrixone, matrixtwo, matrixthree,
                matrixfour, matrixfive, wu_name, wu_id, sector, wu_level_five, wu_level_four, wu_level_one, wu_level_three,
                wu_level_two, wu_level_zero, client_name);
    }
}
