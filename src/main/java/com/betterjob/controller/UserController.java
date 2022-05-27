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

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/user")
public class UserController {

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


    @PostMapping("/add")
    public ResponseEntity<User> add(@RequestBody User user) {
        User updatedUser = userService.addUser(user);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody User user) {
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

    @PostMapping("/job/addJobToUser")
    public ResponseEntity<UserJob> addJobToUser(@RequestBody ApplyOrSaveJobForUserPayload payload) {
        if (payload.isApply()) {
            UserJob userJob = userService.applyToJobForUser(payload.getUserId(), payload.getJobId());
            return new ResponseEntity<>(userJob, HttpStatus.OK);
        }

        UserJob userJob = userService.saveJobForUser(payload.getUserId(), payload.getJobId());
        return new ResponseEntity<>(userJob, HttpStatus.OK);
    }
}
