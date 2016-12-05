package application.service.WorkUnits;

import application.models.WorkUnits;

/**
 * Created by ekonovalova on 12/5/2016.
 */
public interface WorkUnitsDAO {

    Iterable<WorkUnits> listAllWorkUnits();
    WorkUnits save(WorkUnits workUnits);
    WorkUnits getByID(Long id);

}
