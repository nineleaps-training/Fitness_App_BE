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
    //Adding User Details
    @ApiOperation(value = "Add User Details", notes = "Adding the details of the user")
	@ApiResponses(value = { @ApiResponse(code=200, message = "User Details Added", response = ResponseEntity.class),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
    @PutMapping(value = "/v1/add/user-details", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public ResponseEntity<Object> addUserDetails(@Valid @RequestBody UserDetailsRequestModel userDetails) {
        UserDetailsRequestModel userDetails1 = userDetailsService.addUserDetails(userDetails);

        ArrayList<UserDetailsRequestModel> user  = new ArrayList<>();
        user.add(userDetails1);

        if (userDetails1 != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else
        {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //Fetching details of user by email
    @ApiOperation(value = "Getting user details", notes = "Fetching the details of the user from his email")
	@ApiResponses(value = {@ApiResponse(code=200, message = "User Fetched", response = UserDetails.class),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
    @GetMapping(value = "/v1/user-details/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserDetails getUserDetails(@PathVariable String email) {
        return userDetailsService.getUserDetails(email);
    }

}
