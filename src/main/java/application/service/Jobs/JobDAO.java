package application.service.Jobs;

import application.models.Jobs;

/**
 * Created by ekonovalova on 12/5/2016.
 */
public interface JobDAO {

    Iterable<Jobs> listAllJobs();
    Jobs getByJobCode(String jobcode);
    Jobs getByID( Long id);
    Jobs save(Jobs job);
}
