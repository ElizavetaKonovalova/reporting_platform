package application.service.Organisation;

import application.models.Organisations;
import application.repositories.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ekonovalova on 12/5/2016.
 */
public class OrganisationsImpl implements OrganisationDAO {

    @Autowired
    OrganisationRepository organisationRepository;

    @Override
    public Iterable<Organisations> listAllOrganisations() {
        return this.organisationRepository.findAll();
    }

    @Override
    public Organisations getByID(Long id) {
        return this.organisationRepository.findOne(id);
    }

    @Override
    public Organisations save(Organisations organisations) {
        return this.organisationRepository.save(organisations);
    }
}
