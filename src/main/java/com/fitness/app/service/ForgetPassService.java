package com.fitness.app.service;

import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.dto.UserForgot;

public interface ForgetPassService {
    UserForgot userForgot(String email);

    Boolean setPassword(Authenticate user);
}
