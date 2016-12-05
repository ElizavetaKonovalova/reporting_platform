package application.repositories;

import application.models.WorkUnits;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ekonovalova on 12/5/2016.
 */
public interface WorkUnitRepository extends CrudRepository<WorkUnits, Long> {
}
