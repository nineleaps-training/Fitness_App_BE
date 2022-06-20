package com.fitness.app.controller;

import com.fitness.app.entity.UserDetails;
import com.fitness.app.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;
    //Adding User Details
    @PutMapping("/add/user-details")
    public ResponseEntity<ArrayList<UserDetails>> addUserDetails(@RequestBody UserDetails userDetails) {
        UserDetails userDetails1 = userDetailsService.addUserDetails(userDetails);

        ArrayList<UserDetails> user  = new ArrayList<>();
        user.add(userDetails1);
        Assert.notNull(userDetails, "userDetails is null");
        return new ResponseEntity<ArrayList<UserDetails>>(user, HttpStatus.OK);


    }
    //Fetching details of user by email
    @GetMapping("/user-details/{email}")
    public UserDetails getUserDetails(@PathVariable String email) {
        return userDetailsService.getUserDetails(email);
    }

}
