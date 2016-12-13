package application.controllers;

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
                         @RequestParam("mtxo") String matrixone, @RequestParam("mtxtw") String matrixtwo,
                         @RequestParam("mtxth") String matrixthree, @RequestParam("mtxfo") String matrixfour,
                         @RequestParam("mtxfi") String matrixfive, @RequestParam("wun") String wu_name, @RequestParam("wuid") String wu_id,
                         @RequestParam("sector") String sector, @RequestParam("wulvlfi") String wu_level_five,
                         @RequestParam("wulvlfo") String wu_level_four, @RequestParam("wulvlo") String wu_level_one,
                         @RequestParam("wulvlth") String wu_level_three, @RequestParam("wulvltw") String wu_level_two,
                         @RequestParam("wulvlz") String wu_level_zero, @RequestParam("cid") String client_id) throws Exception {
        this.clientStructuralMapsRepository.create(cohort, location, niche, matrixone, matrixtwo, matrixthree,
                matrixfour, matrixfive, wu_name, wu_id, sector, wu_level_five, wu_level_four, wu_level_one, wu_level_three,
                wu_level_two, wu_level_zero, client_id);
    }
}
