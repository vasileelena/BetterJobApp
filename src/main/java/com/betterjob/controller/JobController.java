package com.betterjob.controller;

import com.betterjob.model.Job;
import com.betterjob.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/recruiter/job")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/recruiterId/{recruiterId}")
    public ResponseEntity<List<Job>> getJobsByRecruiterId(@PathVariable("recruiterId") Long recruiterId) {
        List<Job> jobs = jobService.findAllJobsByRecruiterId(recruiterId);

        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Job> addJob(@RequestBody Job job) {
        jobService.addJob(job);

        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @DeleteMapping("/jobId/{jobId}")
    public ResponseEntity<?> deleteJobById(@PathVariable("jobId") Long jobId) {
        jobService.deleteJob(jobId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
