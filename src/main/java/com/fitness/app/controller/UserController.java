package com.fitness.app.controller;

import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.dto.UserModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * The type User controller.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {


    private final UserServiceImpl userServiceImpl;

    /**
     * Register user api response.
     *
     * @param user the user
     * @return the api response
     */
//Register a new user by custom option.
    @PostMapping("/public/register-user")
    public ApiResponse registerUser(@RequestBody UserModel user) {
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
    @PutMapping("/public/verify-user")
    public ApiResponse verifyTheUser(@RequestParam String email, @RequestParam String otp) throws Exception {
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
    @PostMapping("/public/login-user")
    public ApiResponse authenticateUser(@RequestBody Authenticate authCredential) throws Exception {
        return userServiceImpl.loginUser(authCredential);

    }

    /**
     * Google sign in user api response.
     *
     * @param user the user
     * @return the api response
     * @throws Exception the exception
     */
    @PutMapping("/public/google-sign-in/user")
    public ApiResponse googleSignInUser(@RequestBody UserModel user) throws Exception {
        return userServiceImpl.googleSignInMethod(user);
    }
}


