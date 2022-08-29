package com.betterjob.service;

import com.betterjob.model.Job;
import com.betterjob.repository.IJobRepository;
import com.betterjob.repository.IUserJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class JobService {
    private final IJobRepository jobRepository;

    private final IUserJobRepository userJobRepository;

    @Autowired
    public JobService(IJobRepository jobRepository, IUserJobRepository userJobRepository) {
        this.jobRepository = jobRepository;
        this.userJobRepository = userJobRepository;
    }

    public Job addJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> findAllJobsByRecruiterId(Long recruiterId) {
        return jobRepository.findJobByRecruiterId(recruiterId);
    }

    public Job findJobById(Long jobId) { return jobRepository.findJobById(jobId); }

    public List<Job> getAllJobs() { return jobRepository.findAll(); }

    public Job getJobById(Long jobId) {
        return jobRepository.findJobById(jobId);
    }

    @Transactional
    public void deleteJob(Long jobId) {
        jobRepository.deleteJobById(jobId);
        userJobRepository.deleteAllByJobId(jobId);
    }
}
