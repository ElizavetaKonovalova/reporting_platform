package application.controllers;

import application.models.ClientsStructuralMaps;
import application.repositories.ClientStructuralMapsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cwu")
public class ClientStructuralMapController {

    @Autowired
    ClientStructuralMapsRepository clientStructuralMapsRepository;

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

    @RequestMapping(value = "gwuid", produces = "application/json")
    public ClientsStructuralMaps getByWUID(@RequestParam("id") String wu_id) {
        return this.clientStructuralMapsRepository.getWorkUnitByWUID(wu_id);
    }
}
