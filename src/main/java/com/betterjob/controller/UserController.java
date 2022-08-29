package com.betterjob.controller;

import com.betterjob.model.Job;
import com.betterjob.model.User;
import com.betterjob.model.UserJob;
import com.betterjob.model.payloads.ApplyOrSaveJobForUserPayload;
import com.betterjob.service.JobService;
import com.betterjob.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/user")
public class UserController {

    private final Path cvsPath = Paths.get("src/main/resources/images/CVs");

    private final UserService userService;
    private final JobService jobService;

    @Autowired
    public UserController(UserService userService, JobService jobService) {
        this.userService = userService;
        this.jobService = jobService;
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        User user = userService.findUserByEmail(email);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.findUserById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User createdUser = userService.addUser(user);

        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/job/all")
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();

        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<Job> getJobById(@PathVariable("jobId") Long jobId) {
        Job job = jobService.getJobById(jobId);

        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    /**
     * Add a relation between a user and a job (saved/applied)
     * @param payload the payload containing the userId, jobId and the save/applied flag
     * @return the list of jobs updated with the modification
     */
    @PostMapping("/job/addJobToUser")
    public ResponseEntity<List<Job>> addJobToUser(@RequestBody ApplyOrSaveJobForUserPayload payload) {
        if (payload.isApply()) {
            UserJob userJob = userService.applyToJobForUser(payload.getUserId(), payload.getJobId());
            List<Job> appliedJobs = this.userService.getAppliedJobs(payload.getUserId());

            return new ResponseEntity<>(appliedJobs, HttpStatus.OK);
        }

        UserJob userJob = userService.saveJobForUser(payload.getUserId(), payload.getJobId());
        List<Job> savedJobs = this.userService.getSavedJobs(payload.getUserId());

        return new ResponseEntity<>(savedJobs, HttpStatus.OK);
    }

    @GetMapping("/jobs/saved/{userId}")
    public ResponseEntity<List<Job>> getSavedJobs(@PathVariable("userId") Long userId) {
        List<Job> savedJobs = this.userService.getSavedJobs(userId);

        return new ResponseEntity<>(savedJobs, HttpStatus.OK);
    }

    @GetMapping("/jobs/applied/{userId}")
    public ResponseEntity<List<Job>> getAppliedJobs(@PathVariable("userId") Long userId) {
        List<Job> appliedJobs = this.userService.getAppliedJobs(userId);

        return new ResponseEntity<>(appliedJobs, HttpStatus.OK);
    }

    @GetMapping("/job/applied/userId/{userId}/jobId/{jobId}")
    public ResponseEntity<Boolean> checkIfUserAppliedToJob(@PathVariable("userId") Long userId, @PathVariable("jobId") Long jobId) {
        Boolean appliedToJob = this.userService.checkIfUserAppliedToJob(jobId, userId);

        return new ResponseEntity<>(appliedToJob, HttpStatus.OK);
    }

    @GetMapping("/job/saved/userId/{userId}/jobId/{jobId}")
    public ResponseEntity<Boolean> checkIfUserSavedJob(@PathVariable("userId") Long userId, @PathVariable("jobId") Long jobId) {
        Boolean appliedToJob = this.userService.checkIfUserSavedJob(jobId, userId);

        return new ResponseEntity<>(appliedToJob, HttpStatus.OK);
    }

    @PostMapping("/cv")
    public ResponseEntity<String> uploadCv(@RequestBody MultipartFile file) {
        String message;
        try {
            try {
                Files.copy(file.getInputStream(), this.cvsPath.resolve(file.getOriginalFilename() + ".pdf"),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                throw new RuntimeException("Failed to upload file. Error: " + e.getMessage());
            }
            message = "Successfully uploaded file " + file.getName() + "!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            message = "Failed to upload file " + file.getName() + "! Error: " + e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.EXPECTATION_FAILED);
        }
    }

}
