package com.betterjob.service;

import com.betterjob.domain.Job;
import com.betterjob.repository.IJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    private final IJobRepository jobRepository;

    @Autowired
    public JobService(IJobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job addJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> findAllJobsByUserId(Long userId) {
        return jobRepository.findJobByUserId(userId);
    }

    public void deleteJob(Long jobId) {
        jobRepository.deleteById(jobId);
    }
}
