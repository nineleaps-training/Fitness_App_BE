package com.fitness.app.dao;

import com.fitness.app.entity.UserClass;
import com.fitness.app.model.SignUpResponse;
import com.fitness.app.model.UserModel;
import org.springframework.http.ResponseEntity;

public interface UserDao {

    SignUpResponse registerNewUser(UserModel user);
    UserClass registerUser(UserModel user);
    UserClass verifyUser(String email);
    void loginUser(String email);
    UserClass googleSignInMethod(UserModel user);
    String randomPass();
    ResponseEntity<SignUpResponse> logInFunctionality(String email, String password);
}
