package com.betterjob.controller;

import com.betterjob.domain.User;
import com.betterjob.domain.UserLoginPayload;
import com.betterjob.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/")
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.addUser(user);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public User login(@RequestBody UserLoginPayload payload){
        return userService.verifyLogin(payload);
    }

}
