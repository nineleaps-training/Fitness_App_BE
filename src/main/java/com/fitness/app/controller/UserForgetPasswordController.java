package com.fitness.app.controller;

import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.dto.responceDtos.UserForgot;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.service.ForgetPassServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * The type User forget password controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reset")
public class UserForgetPasswordController {


    private final ForgetPassServiceImpl forgetPassServiceImpl;


    /**
     * User forgot user forgot.
     *
     * @param email the email
     * @return the user forgot
     */
//Fetching and verifying user
    @GetMapping("/forget-user-password/{email}")
    public UserForgot userForgot(@PathVariable String email) {
        return forgetPassServiceImpl.userForgot(email);
    }

    /**
     * Sets password.
     *
     * @param user the user
     * @return the password
     */
//Setting the new password for the user
    @PutMapping("/user-set-password")
    public ApiResponse setPassword(@RequestBody Authenticate user) {
        return new ApiResponse(HttpStatus.OK, forgetPassServiceImpl.setPassword(user));
    }

}
    
