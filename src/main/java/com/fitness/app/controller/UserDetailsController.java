package com.fitness.app.controller;

import com.fitness.app.entity.UserDetails;
import com.fitness.app.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;
    //Adding User Details
    @PutMapping("/add/user-details")
    public ResponseEntity<?> addUserDetails(@RequestBody UserDetails userDetails) {
        UserDetails userDetails1 = userDetailsService.addUserDetails(userDetails);

        ArrayList<UserDetails> user  = new ArrayList<>();
        user.add(userDetails1);

        if (userDetails1 != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    //Fetching details of user by email
    @GetMapping("/user-details/{email}")
    public UserDetails getUserDetails(@PathVariable String email) {
        return userDetailsService.getUserDetails(email);
    }

}
