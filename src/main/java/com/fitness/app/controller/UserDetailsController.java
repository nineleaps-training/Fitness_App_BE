package com.fitness.app.controller;

import com.fitness.app.entity.UserDetailsClass;
import com.fitness.app.model.DetailsModel;
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
    public ResponseEntity<ArrayList<UserDetailsClass>> addUserDetails(@RequestBody DetailsModel userDetails) {
        UserDetailsClass userDetailsClass1 = userDetailsService.addUserDetails(userDetails);

        ArrayList<UserDetailsClass> user  = new ArrayList<>();
        user.add(userDetailsClass1);
        Assert.notNull(userDetails, "userDetails is null");
        return new ResponseEntity<ArrayList<UserDetailsClass>>(user, HttpStatus.OK);


    }
    //Fetching details of user by email
    @GetMapping("/user-details/{email}")
    public UserDetailsClass getUserDetails(@PathVariable String email) {
        return userDetailsService.getUserDetails(email);
    }

}
