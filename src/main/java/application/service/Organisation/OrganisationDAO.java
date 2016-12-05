package application.service.Organisation;

import application.models.Organisations;

/**
 * Created by ekonovalova on 12/5/2016.
 */
public interface OrganisationDAO {

    Iterable<Organisations> listAllOrganisations();
    Organisations getByID(Long id);
    Organisations save(Organisations organisations);
}
