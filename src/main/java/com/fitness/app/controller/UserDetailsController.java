package com.fitness.app.controller;

import com.fitness.app.dto.DetailsModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.service.DetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * The type User details controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/details")
public class UserDetailsController {


    private final DetailsServiceImpl userDetailsServiceImpl;

    /**
     * Add user details api response.
     *
     * @param userDetails the user details
     * @return the api response
     */
//Adding User Details
    @PutMapping("/private/add-user-details")
    public ApiResponse addUserDetails(@RequestBody DetailsModel userDetails) {

        int status = userDetailsServiceImpl.addUserDetails(userDetails);
        return (status == 200) ? new ApiResponse(HttpStatus.OK, "Successful") : new ApiResponse(HttpStatus.OK, "Successful");


    }

    /**
     * Gets user details.
     *
     * @param email the email
     * @return the user details
     */
//Fetching details of user by email
    @GetMapping("/user-details/{email}")
    public ApiResponse getUserDetails(@PathVariable String email) {

        return new ApiResponse(HttpStatus.OK,userDetailsServiceImpl.getUserDetails(email));

    }

}
