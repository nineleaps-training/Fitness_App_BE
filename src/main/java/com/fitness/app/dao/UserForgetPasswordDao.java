package com.fitness.app.dao;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.model.UserForgot;

public interface UserForgetPasswordDao {

    UserForgot userForgot(String email);
    boolean setPassword(Authenticate user);
}
