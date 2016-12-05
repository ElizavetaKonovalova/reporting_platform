package application.service.WorkUnits;

import application.models.WorkUnits;
import application.repositories.WorkUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ekonovalova on 12/5/2016.
 */
public class WorkUnitsDAOImpl implements WorkUnitsDAO {

    @Autowired
    WorkUnitRepository workUnitRepository;

    @Override
    public Iterable<WorkUnits> listAllWorkUnits() {
        return this.workUnitRepository.findAll();
    }

    @Override
    public WorkUnits save(WorkUnits workUnits) {
        return this.workUnitRepository.save(workUnits);
    }

    @Override
    public WorkUnits getByID(Long id) {
        return this.workUnitRepository.findOne(id);
    }
}
