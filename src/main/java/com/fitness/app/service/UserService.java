package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserModel;

public interface UserService {

    UserClass registerUser(UserModel userModel);
    UserClass verifyUser(String email);
    void loginUser(String email);
    UserClass googleSignInMethod(UserModel userModel);
    String randomPass();

}
