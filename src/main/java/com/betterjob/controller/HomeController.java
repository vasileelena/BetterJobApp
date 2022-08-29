package com.betterjob.controller;

import com.betterjob.exception.UserNotFoundException;
import com.betterjob.model.User;
import com.betterjob.model.payloads.UserLoginPayload;
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
    public ResponseEntity<?> login(@RequestBody UserLoginPayload payload) throws UserNotFoundException {
        User user = userService.verifyLogin(payload);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
