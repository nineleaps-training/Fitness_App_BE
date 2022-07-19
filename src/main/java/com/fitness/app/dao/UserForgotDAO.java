package com.fitness.app.dao;

import org.springframework.stereotype.Component;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.model.UserForgotModel;

@Component
public interface UserForgotDAO {

    public UserForgotModel userForgot(String email);

    public boolean setPassword(Authenticate user);
    
}
