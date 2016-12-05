package application.service.Jobs;

import application.models.Jobs;
import application.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ekonovalova on 12/5/2016.
 */
public class JobDAOImpl implements JobDAO{

    private JobRepository jobRepository;

    @Autowired
    public void setJobRepository(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public Iterable<Jobs> listAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public Jobs getByID(Long id) {
        return jobRepository.findOne(id);
    }

    @Override
    public Iterable<Jobs> listAllJobsForClient() {
        return null;
    }

    @Override
    public Jobs save(Jobs job) {
        return jobRepository.save(job);
    }
}
