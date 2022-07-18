package com.fitness.app.controller;

import com.fitness.app.entity.UserDetails;
import com.fitness.app.model.UserDetailsRequestModel;
import com.fitness.app.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.validation.Valid;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * This controller is used for adding the user details
     * 
     * @param userDetails - Details of the user
     * @return - Response is created if success or else bad request
     */
    @ApiOperation(value = "Add User Details", notes = "Adding the details of the user")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "User Details Added", response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
    @PutMapping(value = "/v1/add/user-details", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Validated
    public ResponseEntity<Object> addUserDetails(@Valid @RequestBody UserDetailsRequestModel userDetails) {
        UserDetails userDetails1 = userDetailsService.addUserDetails(userDetails);

        ArrayList<UserDetails> user = new ArrayList<>();
        user.add(userDetails1);

        if (userDetails1 != null) {
            return new ResponseEntity<>(user, HttpStatus.CREATED); // Adding User Details
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This controller is used for fetching the user details by his email
     * 
     * @param email - Email id of the user
     * @return - Details of the user
     */
    @ApiOperation(value = "Getting user details", notes = "Fetching the details of the user from his email")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "User Fetched", response = UserDetails.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
    @GetMapping(value = "/v1/user-details/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserDetails getUserDetails(@PathVariable String email) {
        return userDetailsService.getUserDetails(email); // Fetching details of user by email
    }

}
