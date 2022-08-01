package com.fitness.app.dao;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fitness.app.entity.UserClass;
import com.fitness.app.model.SignUpResponseModel;
import com.fitness.app.model.UserModel;

@Component
public interface UserDAO {

    public SignUpResponseModel registerNewUser(UserModel user);

    public ResponseEntity<SignUpResponseModel> logInFunctionality(String email, String password);

    public UserClass registerUser(UserModel user);

    public UserClass verifyUser(String email);

    public UserClass googleSignInMethod(UserModel user);

    public String randomPass();
}
