package com.fitness.app.service.dao;

import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.dto.requestDtos.UserModel;
import com.fitness.app.dto.responceDtos.ApiResponse;

public interface UserService {

    ApiResponse registerUser(UserModel userModel);

    ApiResponse verifyUser(String email, String otp);

    ApiResponse loginUser(Authenticate authenticate);

    ApiResponse googleSignInMethod(UserModel userModel);

    String randomPass();

}
