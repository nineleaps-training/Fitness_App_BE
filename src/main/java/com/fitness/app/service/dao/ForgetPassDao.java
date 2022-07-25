package com.fitness.app.service.dao;

import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.dto.response.UserForgot;


public interface ForgetPassDao {
    UserForgot userForgot(String email);

    Boolean setPassword(Authenticate user);
}
