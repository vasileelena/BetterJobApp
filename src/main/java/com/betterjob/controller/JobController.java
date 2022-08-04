package com.betterjob.controller;

import com.betterjob.model.Job;
import com.betterjob.model.User;
import com.betterjob.service.JobService;
import com.betterjob.service.UserService;
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
    private final UserService userService;

    @Autowired
    public JobController(JobService jobService, UserService userService) {
        this.jobService = jobService;
        this.userService = userService;
    }

    @GetMapping("/recruiterId/{recruiterId}")
    public ResponseEntity<List<Job>> getJobsByRecruiterId(@PathVariable("recruiterId") Long recruiterId) {
        List<Job> jobs = jobService.findAllJobsByRecruiterId(recruiterId);

        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    /**
     * Get the applicants for a job
     * @param jobId the job id
     * @return the list of users that applied to the specified job
     */
    @GetMapping("/jobId/{jobId}/applicants")
    public ResponseEntity<List<User>> getApplicantsForJob(@PathVariable("jobId") Long jobId) {
        List<User> applicants = userService.getApplicantsForJobByJobId(jobId);

        return new ResponseEntity<>(applicants, HttpStatus.OK);
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
