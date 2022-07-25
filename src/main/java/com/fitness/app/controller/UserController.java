package com.fitness.app.controller;

import com.fitness.app.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.SignUpResponse;
import com.fitness.app.model.UserModel;

import javax.validation.Valid;


@Slf4j
@RestController
@Validated
public class UserController {


    @Autowired
    private UserDao userDao;

    //Register a new user by custom option.
    @PostMapping("/register/user")
    @Validated
    public SignUpResponse registerUser(@Valid @RequestBody UserModel user) {
        return userDao.registerNewUser(user);
    }

    //Verify User
    @PutMapping("/verify/user")
    @Validated
    public ResponseEntity<SignUpResponse> verifyTheUser(@Valid @RequestBody Authenticate authCredential) {
        UserClass user = userDao.verifyUser(authCredential.getEmail());

        if (user != null) {

            return userDao.logInFunctionality(authCredential.getEmail(), authCredential.getPassword());
        }

        return ResponseEntity.ok(new SignUpResponse(null, null));
    }


    //Log in user
    @PostMapping("/login/user")
    @Validated
    public ResponseEntity<SignUpResponse> authenticateUser(@Valid @RequestBody Authenticate authCredential) {
        return userDao.logInFunctionality(authCredential.getEmail(), authCredential.getPassword());

    }


    @PutMapping("/google-sign-in/vendor")
    @Validated
    public ResponseEntity<SignUpResponse> googleSignInVendor(@Valid @RequestBody UserModel user) {
        String pass = userDao.randomPass();
        user.setPassword(pass);
        UserClass localUser = userDao.googleSignInMethod(user);
        if (localUser == null) {
            return ResponseEntity.ok(new SignUpResponse(null, "This email in use!"));
        } else if (localUser.getRole().equals("USER")) {
            return ResponseEntity.ok(new SignUpResponse(null, "This email already in use as USER! "));
        } else {
            return userDao.logInFunctionality(localUser.getEmail(), user.getPassword());
        }
    }


    @PutMapping("/google-sign-in/user")
    @Validated
    public ResponseEntity<SignUpResponse> googleSignInUser(@Valid @RequestBody UserModel user) {
        String pass = userDao.randomPass();
        user.setPassword(pass);
        UserClass localUser = userDao.googleSignInMethod(user);
        if (localUser == null) {
            return ResponseEntity.ok(new SignUpResponse(null, "This email in use!"));
        } else if (localUser.getRole().equals("VENDOR")) {
            return ResponseEntity.ok(new SignUpResponse(null, "This email already in use as VENDOR! "));
        } else {
            return userDao.logInFunctionality(localUser.getEmail(), user.getPassword());
        }
    }
}


