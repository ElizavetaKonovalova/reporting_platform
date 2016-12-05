package application.repositories;

import application.models.Organisations;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ekonovalova on 12/5/2016.
 */
public interface OrganisationRepository extends CrudRepository<Organisations, Long>{
}
