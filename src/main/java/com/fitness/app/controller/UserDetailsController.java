package com.fitness.app.controller;

import com.fitness.app.entity.UserDetailsClass;
import com.fitness.app.model.DetailsModel;
<<<<<<< HEAD
import com.fitness.app.service.UserDetailsService;
=======
import com.fitness.app.service.UserDetailsServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class UserDetailsController {

    @Autowired
<<<<<<< HEAD
    private UserDetailsService userDetailsService;
    //Adding User Details
    @PutMapping("/add/user-details")
    public ResponseEntity<ArrayList<UserDetailsClass>> addUserDetails(@RequestBody DetailsModel userDetails) {
        UserDetailsClass userDetailsClass1 = userDetailsService.addUserDetails(userDetails);
=======
    private UserDetailsServiceImpl userDetailsServiceImpl;
    //Adding User Details
    @PutMapping("/add/user-details")
    public ResponseEntity<ArrayList<UserDetailsClass>> addUserDetails(@RequestBody DetailsModel userDetails) {
        UserDetailsClass userDetailsClass1 = userDetailsServiceImpl.addUserDetails(userDetails);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48

        ArrayList<UserDetailsClass> user  = new ArrayList<>();
        user.add(userDetailsClass1);
        Assert.notNull(userDetails, "userDetails is null");
        return new ResponseEntity<ArrayList<UserDetailsClass>>(user, HttpStatus.OK);


    }
    //Fetching details of user by email
    @GetMapping("/user-details/{email}")
    public UserDetailsClass getUserDetails(@PathVariable String email) {
<<<<<<< HEAD
        return userDetailsService.getUserDetails(email);
=======
        return userDetailsServiceImpl.getUserDetails(email);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
    }

}
