package com.betterjob.service;

import com.betterjob.domain.User;
import com.betterjob.domain.UserLoginPayload;
import com.betterjob.exception.UserNotFoundException;
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

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
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
}
