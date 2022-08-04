package com.betterjob.service;

import com.betterjob.model.Job;
import com.betterjob.model.User;
import com.betterjob.model.UserJob;
import com.betterjob.repository.IJobRepository;
import com.betterjob.repository.IUserJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Job> findAllJobsByRecruiterId(Long recruiterId) {
        return jobRepository.findJobByRecruiterId(recruiterId);
    }

    public Job findJobById(Long jobId) { return jobRepository.findJobById(jobId); }

    public List<Job> getAllJobs() { return jobRepository.findAll(); }

    public void deleteJob(Long jobId) {
        jobRepository.deleteById(jobId);
    }
}
