package com.fitness.app.controller;

import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.dto.response.ApiResponse;
import com.fitness.app.dto.response.UserForgot;
import com.fitness.app.service.dao.ForgetPassDao;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;


/**
 * The type User forget password controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reset")
public class UserForgetPasswordController {


    private final ForgetPassDao forgetPassDao;


    /**
     * User forgot user forgot.
     *
     * @param email the email
     * @return the user forgot
     */

    @GetMapping("/forget/user/password/{email}")
    @ApiOperation(value = "Forget Password", notes = "User can reset password.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Otp Verification", response = UserForgot.class),
            @io.swagger.annotations.ApiResponse(code = 401, message = "No user found or Bad request", response = String.class)
    })
    @Validated
    public UserForgot userForgot(@PathVariable @Email @Valid String email) {
        return forgetPassDao.userForgot(email);
    }

    /**
     * Sets password.
     *
     * @param user the user
     * @return the password
     */

    @PutMapping("/user/set/password")
    @ApiOperation(value = "Set New Password", notes = "User can reset password after verification.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "successful", response = ApiResponse.class),
            @io.swagger.annotations.ApiResponse(code = 401, message = "No user found or Bad request", response = String.class)
    })
    @Validated
    public ApiResponse setPassword(@RequestBody @Valid Authenticate user) {
        return new ApiResponse(HttpStatus.OK, forgetPassDao.setPassword(user));
    }

}
    
