package com.betterjob.service;

import com.betterjob.model.Job;
import com.betterjob.model.User;
import com.betterjob.model.UserJob;
import com.betterjob.model.payloads.UserLoginPayload;
import com.betterjob.exception.UserNotFoundException;
import com.betterjob.repository.IJobRepository;
import com.betterjob.repository.IUserJobRepository;
import com.betterjob.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final IUserRepository userRepository;

    private final IUserJobRepository userJobRepository;

    private final IJobRepository jobRepository;

    @Autowired
    public UserService(IUserRepository userRepository, IUserJobRepository userJobRepository, IJobRepository jobRepository) {
        this.userRepository = userRepository;
        this.userJobRepository = userJobRepository;
        this.jobRepository = jobRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User findUserById(Long id) { return userRepository.findUserById(id); }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User verifyLogin(UserLoginPayload payload) throws UserNotFoundException{
        User user = userRepository.findUserByEmail(payload.getEmail());

        /* Check if password provided in the login form is equal to the encrypted password from the db */
        if (passwordEncoder.matches(payload.getPassword(), user.getPassword())) {
            return user;
        }
        else {
            throw new UserNotFoundException("The user doesn't exist or the credentials are wrong!");
        }
    }

    /* Method used to apply to a job listing */
    public UserJob applyToJobForUser(Long userId, Long jobId) {
        try {
            UserJob userJob = this.userJobRepository.findByJobIdAndUserId(jobId, userId);
            userJob.setApplied(true);

            return userJobRepository.save(userJob);
        } catch (Exception e){
            System.out.println(e.getStackTrace());
        }

        UserJob userJob = new UserJob();

        userJob.setUserId(userId);
        userJob.setJobId(jobId);
        userJob.setSaved(false);
        userJob.setApplied(true);

        return userJobRepository.save(userJob);
    }

    /* Method used to save a job listing */
    public UserJob saveJobForUser(Long userId, Long jobId) {
        try {
            UserJob userJob = this.userJobRepository.findByJobIdAndUserId(jobId, userId);
            userJob.setSaved(true);

            return userJobRepository.save(userJob);
        } catch (Exception e){
            System.out.println(e.getStackTrace());
        }

        UserJob userJob = new UserJob();

        userJob.setUserId(userId);
        userJob.setJobId(jobId);
        userJob.setSaved(true);
        userJob.setApplied(false);

        return userJobRepository.save(userJob);
    }

    public List<Job> getSavedJobs(Long userId) {
        List<UserJob> userJobList = this.userJobRepository.findBySavedAndUserId(true, userId);
        List<Job> savedJobs = new ArrayList<>();

        for(UserJob userJob : userJobList) {
            Job job = this.jobRepository.findJobById(userJob.getJobId());
            savedJobs.add(job);
        }

        return savedJobs;
    }

    public List<Job> getAppliedJobs(Long userId) {
        List<UserJob> userJobList = this.userJobRepository.findByAppliedAndUserId(true, userId);
        List<Job> appliedJobs = new ArrayList<>();

        for(UserJob userJob : userJobList) {
            Job job = this.jobRepository.findJobById(userJob.getJobId());
            appliedJobs.add(job);
        }

        return appliedJobs;
    }

    /**
     * Check if a user applied to a specified job
     * @param jobId the job id
     * @param userId the user id
     * @return true if the user applied to the job, false otherwise
     */
    public boolean checkIfUserAppliedToJob(Long jobId, Long userId) {
        Optional<UserJob> userJob = Optional.ofNullable(this.userJobRepository.findByJobIdAndUserIdAndApplied(jobId, userId, true));
        return userJob.isPresent();
    }

    /**
     * Check if a user saved a specified job
     * @param jobId the job id
     * @param userId the user id
     * @return true if the user saved the job, false otherwise
     */
    public boolean checkIfUserSavedJob(Long jobId, Long userId) {
        Optional<UserJob> userJob = Optional.ofNullable(this.userJobRepository.findByJobIdAndUserIdAndSaved(jobId, userId, true));
        return userJob.isPresent();
    }


    /**
     * Get the user applicants for a job
     * @param jobId the id of the job
     * @return the list of applicants users
     */
    public List<User> getApplicantsForJobByJobId (Long jobId) {
        List <UserJob> userJobList = userJobRepository.findByAppliedAndJobId(true, jobId);
        List<User> applicants = new ArrayList<>();

        for (UserJob userJob: userJobList) {
            User user = userRepository.findUserById(userJob.getUserId());
            applicants.add(user);
        }
        return applicants;
    }
}
