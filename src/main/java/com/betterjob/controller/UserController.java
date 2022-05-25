package com.betterjob.controller;

import com.betterjob.domain.User;
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

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        User user = userService.findUserByEmail(email);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PutMapping("/add")
    public ResponseEntity<User> add(@RequestBody User user) {
        User updatedUser = userService.addUser(user);
        System.out.println(user);

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
}
