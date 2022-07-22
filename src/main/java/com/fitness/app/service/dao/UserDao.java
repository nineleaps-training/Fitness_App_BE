package com.fitness.app.service.dao;

import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.dto.request.UserModel;
import com.fitness.app.dto.response.ApiResponse;
import org.springframework.stereotype.Component;

@Component
public interface UserDao {

    ApiResponse registerUser(UserModel userModel);

    ApiResponse verifyUser(String email, String otp);

    ApiResponse loginUser(Authenticate authenticate);

    ApiResponse googleSignInMethod(UserModel userModel);

    String randomPass();

}
