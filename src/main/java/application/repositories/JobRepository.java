package application.repositories;

import application.models.Jobs;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ekonovalova on 12/5/2016.
 */
public interface JobRepository extends CrudRepository<Jobs, Long> {
}
