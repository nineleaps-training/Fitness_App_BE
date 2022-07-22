package com.fitness.app.controller;

import com.fitness.app.dto.request.DetailsModel;
import com.fitness.app.dto.response.ApiResponse;
import com.fitness.app.entity.UserDetailsClass;
import com.fitness.app.service.DetailsDaoImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

/**
 * The type User details controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/details")
public class UserDetailsController {


    private final DetailsDaoImpl userDetailsServiceImpl;

    /**
     * Add user details api response.
     *
     * @param userDetails the user details
     * @return the api response
     */
//Adding User Details
    @PutMapping("/add/user/details")
    @ApiOperation(value = "Add/ update user Details", notes = "User can add new details or update the older one.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Successful", response = String.class),
            @io.swagger.annotations.ApiResponse(code = 401, message = "No user found or Bad request", response = String.class)
    })
    @Validated
    public ApiResponse addUserDetails(@RequestBody @Valid DetailsModel userDetails) {

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
    @GetMapping("/user/details/{email}")
    @ApiOperation(value = "User Details", notes = "Get User details.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "User Details", response = UserDetailsClass.class),
            @io.swagger.annotations.ApiResponse(code = 401, message = "No user found or Bad request", response = String.class)
    })
    @Validated
    public ApiResponse getUserDetails(@Valid @Email @PathVariable String email) {

        return new ApiResponse(HttpStatus.OK, userDetailsServiceImpl.getUserDetails(email));

    }

}
