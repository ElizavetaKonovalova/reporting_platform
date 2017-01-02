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

    /* Main selector method */
    @RequestMapping(value = "get", produces = "application/json")
    public List<ClientsStructuralMaps> get(@RequestParam("value") String value, @RequestParam("target") String target) {
        return this.clientStructuralMapRepository.getClientsStructuralMapsByName(value, target);
    }

    /* Select a Work Unit by its Name */
    @RequestMapping(value = "gname", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByName(@RequestParam("wname") String wu_name,
                                                                     @RequestParam("cname") String client_name) {
        return this.clientStructuralMapRepository.getClientsStructuralMapsByName(wu_name, client_name);
    }

    /* Select a Work Unit by its Work Unit ID */
    @RequestMapping(value = "gwui", produces = "application/json")
    public ClientsStructuralMaps getClientStructuralMapsByWUID(@RequestParam("id") String wu_id,
                                                               @RequestParam("cname") String client_name) {
        return this.clientStructuralMapRepository.getWorkUnitByWUID(wu_id, client_name);
    }

    /* Select a Work Unit by its database ID */
    @RequestMapping(value = "gdb", produces = "application/json")
    public ClientsStructuralMaps getClientStructuralMapsByDBID(@RequestParam("id") String db_id,
                                                               @RequestParam("cname") String client_name) {
        return this.clientStructuralMapRepository.getWorkUnitByDBID(db_id,client_name);
    }

    /* Select a Work Unit by its Level */
    @RequestMapping(value = "glvl", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByLevel(@RequestParam("name") String level,
                                                                      @RequestParam("cname") String client_name) {
        return this.clientStructuralMapRepository.getClientsStructuralMapsByLevel(level,client_name);
    }

    /* Select a Work Unit by its Matrix */
    @RequestMapping(value = "gmtx", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByMatrix(@RequestParam("name") String matrix,
                                                                       @RequestParam("cname") String client_name) {
        return this.clientStructuralMapRepository.getClientsStructuralMapsByMatrix(matrix,client_name);
    }

    /* Select a Work Unit by its Location */
    @RequestMapping(value = "gloc", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByLocation(@RequestParam("name") String location,
                                                                         @RequestParam("cname") String client_name) {
        return this.clientStructuralMapRepository.getClientsStructuralMapsByLocation(location,client_name);
    }

    /* Select a Work Unit by its Cohort */
    @RequestMapping(value = "gcohort", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByCohort(@RequestParam("name") String cohort,
                                                                       @RequestParam("cname") String client_name) {
        return this.clientStructuralMapRepository.getClientsStructuralMapsByCohort(cohort,client_name);
    }

    /* Select a Work Unit by its Niche */
    @RequestMapping(value = "gniche", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByNiche(@RequestParam("name") String niche,
                                                                      @RequestParam("cname") String client_name) {
        return this.clientStructuralMapRepository.getClientsStructuralMapsByNiche(niche,client_name);
    }

    /* Select a Work Unit by its Sector */
    @RequestMapping(value = "gsector", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsBySector(@RequestParam("name") String sector,
                                                                       @RequestParam("cname") String client_name) {
        return this.clientStructuralMapRepository.getClientsStructuralMapsBySector(sector,client_name);
    }

    /* Select Work Units by their Client ID */
    @RequestMapping(value = "gclient", produces = "application/json")
    public List<ClientsStructuralMaps> getClientStructuralMapsByClientID(@RequestParam("id") String client_id,
                                                                         @RequestParam("cname") String client_name) {
        return this.clientStructuralMapRepository.getClientsStructuralMapsByClientID(client_id,client_name);
    }


    /* REMOVALS */

    /* Remove Work Units by their Name */
    @RequestMapping(value = "rwun", produces = "application/json")
    public void removeWUByName(@RequestParam("name") String wu_name, @RequestParam("cname") String client_name) {
        this.clientStructuralMapRepository.removeWUByName(wu_name, client_name);
    }

    /* Remove Work Units by their Work Unit ID */
    @RequestMapping(value = "rwui", produces = "application/json")
    public void removeWUByWUID(@RequestParam("id") String wu_id, @RequestParam("cname") String client_name) {
        this.clientStructuralMapRepository.removeWUByWUID(wu_id, client_name);
    }

    /* Remove Work Units by their database ID */
    @RequestMapping(value = "rdb", produces = "application/json")
    public void removeWUByDBID(@RequestParam("id") String db_id, @RequestParam("cname") String client_name) {
        this.clientStructuralMapRepository.removeWUByDBID(db_id, client_name);
    }

    /* Remove Work Units by their Client ID */
    @RequestMapping(value = "rwuclient", produces = "application/json")
    public void removeWUByClientID(@RequestParam("id") String client_id, @RequestParam("cname") String client_name) {
        this.clientStructuralMapRepository.removeWUByClientID(client_id, client_name);
    }

    /* Remove Work Units by their Location */
    @RequestMapping(value = "rwuloc", produces = "application/json")
    public void removeWUByLocation(@RequestParam("name") String location, @RequestParam("cname") String client_name) {
        this.clientStructuralMapRepository.removeWUByLocation(location, client_name);
    }

    /* Remove Work Units by their Cohort */
    @RequestMapping(value = "rwucohort", produces = "application/json")
    public void removeWUByCohort(@RequestParam("name") String cohort, @RequestParam("cname") String client_name) {
        this.clientStructuralMapRepository.removeWUByCohort(cohort, client_name);
    }

    /* Remove Work Units by their Sector */
    @RequestMapping(value = "rwusector", produces = "application/json")
    public void removeWUBySector(@RequestParam("name") String sector, @RequestParam("cname") String client_name) {
        this.clientStructuralMapRepository.removeWUBySector(sector, client_name);
    }

    /* Remove Work Units by their Niche */
    @RequestMapping(value = "rwuniche", produces = "application/json")
    public void removeWUByNiche(@RequestParam("name") String niche, @RequestParam("cname") String client_name) {
        this.clientStructuralMapRepository.removeWUByNiche(niche, client_name);
    }

    /* Remove Matrix One by its Name */
    @RequestMapping(value = "rwumtx1", produces = "application/json")
    public void removeMatrixOne(@RequestParam("name") String matrix_one, @RequestParam("cname") String client_name) throws Exception {
        this.clientStructuralMapRepository.nullMatrixOne(matrix_one, client_name);
    }

    /* Remove Matrix Two by its Name */
    @RequestMapping(value = "rwumtx2", produces = "application/json")
    public void removeMatrixTwo(@RequestParam("name") String matrix_two, @RequestParam("cname") String client_name) throws Exception {
        this.clientStructuralMapRepository.nullMatrixTwo(matrix_two, client_name);
    }

    /* Remove Matrix Three by its Name */
    @RequestMapping(value = "rwumtx3", produces = "application/json")
    public void removeMatrixThree(@RequestParam("name") String matrix_three, @RequestParam("cname") String client_name) throws Exception {
        this.clientStructuralMapRepository.nullMatrixThree(matrix_three, client_name);
    }

    /* Remove Matrix Four by its Name */
    @RequestMapping(value = "rwumtx4", produces = "application/json")
    public void removeMatrixFour(@RequestParam("name") String matrix, @RequestParam("cname") String client_name) throws Exception {
        this.clientStructuralMapRepository.nullMatrixFour(matrix, client_name);
    }

    /* Remove Matrix Five by its Name */
    @RequestMapping(value = "rwumtx5", produces = "application/json")
    public void removeMatrixFive(@RequestParam("name") String matrix, @RequestParam("cname") String client_name) throws Exception {
        this.clientStructuralMapRepository.nullMatrixFive(matrix, client_name);
    }

    /* Remove Level Zero by its Name */
    @RequestMapping(value = "rwulvl0", produces = "application/json")
    public void removeLevelZero(@RequestParam("name") String level, @RequestParam("cname") String client_name) throws Exception {
        this.clientStructuralMapRepository.nullLevelZero(level, client_name);
    }

    /* Remove Level One by its Name */
    @RequestMapping(value = "rwulvl1", produces = "application/json")
    public void removeLevelOne(@RequestParam("name") String level, @RequestParam("cname") String client_name) throws Exception {
        this.clientStructuralMapRepository.nullLevelOne(level, client_name);
    }

    /* Remove Level Two by its Name */
    @RequestMapping(value = "rwulvl2", produces = "application/json")
    public void removeLevelTwo(@RequestParam("name") String level, @RequestParam("cname") String client_name) throws Exception {
        this.clientStructuralMapRepository.nullLevelTwo(level, client_name);
    }

    /* Remove Level Three by its Name */
    @RequestMapping(value = "rwulvl3", produces = "application/json")
    public void removeLevelThree(@RequestParam("name") String level, @RequestParam("cname") String client_name) throws Exception {
        this.clientStructuralMapRepository.nullLevelThree(level, client_name);
    }

    /* Remove Level Two by its Name */
    @RequestMapping(value = "rwulvl4", produces = "application/json")
    public void removeLevelFour(@RequestParam("name") String level, @RequestParam("cname") String client_name) throws Exception {
        this.clientStructuralMapRepository.nullLevelFour(level, client_name);
    }

    /* Remove Level Two by its Name */
    @RequestMapping(value = "rwulvl5", produces = "application/json")
    public void removeLevelFive(@RequestParam("name") String level, @RequestParam("cname") String client_name) throws Exception {
        this.clientStructuralMapRepository.nullLevelFive(level, client_name);
    }

    /* Remove Niche */
    @RequestMapping(value = "rniche", produces = "application/json")
    public void removeNiche(@RequestParam("name") String niche, @RequestParam("cname") String client_name) throws Exception {
        this.clientStructuralMapRepository.nullNiche(niche, client_name);
    }

    /* Remove Cohort */
    @RequestMapping(value = "rcohort", produces = "application/json")
    public void removeCohort(@RequestParam("name") String cohort, @RequestParam("cname") String client_name) throws Exception {
        this.clientStructuralMapRepository.nullCohort(cohort, client_name);
    }

    /* Remove Sector */
    @RequestMapping(value = "rsector", produces = "application/json")
    public void removeSector(@RequestParam("name") String sector, @RequestParam("cname") String client_name) throws Exception {
        this.clientStructuralMapRepository.nullSector(sector, client_name);
    }

    /* Remove Location */
    @RequestMapping(value = "rloc", produces = "application/json")
    public void removeLocation(@RequestParam("name") String location, @RequestParam("cname") String client_name) throws Exception {
        this.clientStructuralMapRepository.nullLocation(location, client_name);
    }



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
