package com.fitness.app.service.dao;

import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.dto.responceDtos.UserForgot;

public interface ForgetPassService {
    UserForgot userForgot(String email);

    Boolean setPassword(Authenticate user);
}
