package com.fitness.app.controller;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.dao.UserForgetPasswordDao;
import com.fitness.app.model.UserForgot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@RestController
@Validated
public class UserForgetPasswordController {
    @Autowired
    private UserForgetPasswordDao userForgetPasswordDao;

    //Fetching and verifying user
    @GetMapping("/forget/user/{email}")
    public UserForgot userForgot(@NotNull @NotBlank @NotEmpty @Email @PathVariable String email) {
        return userForgetPasswordDao.userForgot(email);
    }

    //Setting the new password for the user
    @PutMapping("/user/set-password")
    @Validated
    public boolean setPassword(@Valid @RequestBody Authenticate user) {
        return userForgetPasswordDao.setPassword(user);
    }

}
    
