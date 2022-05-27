package com.betterjob.service;

import com.betterjob.model.User;
import com.betterjob.model.UserJob;
import com.betterjob.model.payloads.UserLoginPayload;
import com.betterjob.exception.UserNotFoundException;
import com.betterjob.repository.IUserJobRepository;
import com.betterjob.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IUserJobRepository userJobRepository;

    @Autowired
    public UserService(IUserRepository userRepository, IUserJobRepository userJobRepository) {
        this.userRepository = userRepository;
        this.userJobRepository = userJobRepository;
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

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User verifyLogin(UserLoginPayload payload){
        User user = userRepository.findUserByEmail(payload.getEmail());

        /* Check if password provided in the login form is equal to the encrypted password from the db */
        if (passwordEncoder.matches(payload.getPassword(), user.getPassword())) {
            return user;
        }
        else
            throw new UserNotFoundException("The user doesn't exist!");
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
}
