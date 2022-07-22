package com.fitness.app.controller;

import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.dto.request.UserModel;
import com.fitness.app.dto.response.ApiResponse;
import com.fitness.app.service.UserDaoImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {


    private final UserDaoImpl userServiceImpl;

    /**
     * Register user api response.
     *
     * @param user the user
     * @return the api response
     */

    @PostMapping("/public/register/user")
    @ApiOperation(value = "Add new user", notes = "User can onboard in application.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Added or Successful", response = String.class),
    })
    @Validated
    public ApiResponse registerUser(@RequestBody @Valid UserModel user) {
        return userServiceImpl.registerUser(user);
    }

    /**
     * Verify the user api response.
     *
     * @param email the email
     * @param otp   the otp
     * @return the api response
     * @throws Exception the exception
     */
//Verify User
    @PutMapping("/public/verify/user")
    @ApiOperation(value = "Verify new added user", notes = "User Should verify via otp sent on mail.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Added or Successful", response = String.class),
    })
    @Validated
    @Email
    public ApiResponse verifyTheUser(@RequestParam @Valid String email, @RequestParam String otp) throws Exception {
        return userServiceImpl.verifyUser(email, otp);
    }


    /**
     * Authenticate user api response.
     *
     * @param authCredential the auth credential
     * @return the api response
     * @throws Exception the exception
     */
//Log in user
    @PostMapping("/public/login/user")
    @ApiOperation(value = "Sign in User", notes = "User Login or Sign up method.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Token for accessing application", response = String.class),
            @io.swagger.annotations.ApiResponse(code = 203, message = "Invalid credentials", response = String.class)
    })
    @Validated
    public ApiResponse authenticateUser(@Valid @RequestBody Authenticate authCredential) throws Exception {
        return userServiceImpl.loginUser(authCredential);

    }

    /**
     * Google sign in user api response.
     *
     * @param user the user
     * @return the api response
     * @throws Exception the exception
     */
    @PutMapping("/public/google/sign/in/user")
    @ApiOperation(value = "Sign in User via 3rd party", notes = "User Login or Sign up method for google account.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Token for accessing application", response = String.class),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Something went Wrong", response = String.class)
    })
    @Validated
    public ApiResponse googleSignInUser(@Valid @RequestBody UserModel user) throws Exception {
        return userServiceImpl.googleSignInMethod(user);
    }
}


