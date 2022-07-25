package com.fitness.app.controller;

import com.fitness.app.dao.UserDetailsDao;
import com.fitness.app.entity.UserDetails;
import com.fitness.app.model.UserDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@RestController
@Validated
public class UserDetailsController {

    @Autowired
    private UserDetailsDao userDetailsDao;

    //Adding User Details
    @PutMapping("/add/user-details")
    @Validated
    public ResponseEntity<ArrayList<UserDetails>> addUserDetails(@Valid @RequestBody UserDetailsModel userDetailsModel) {
        UserDetails userDetails = userDetailsDao.addUserDetails(userDetailsModel);

        ArrayList<UserDetails> user = new ArrayList<>();
        user.add(userDetails);

        if (userDetails != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //Fetching details of user by email
    @GetMapping("/user-details/{email}")
    public UserDetails getUserDetails(@NotNull @NotEmpty @NotBlank @Email @PathVariable String email) {
        return userDetailsDao.getUserDetails(email);
    }

}
