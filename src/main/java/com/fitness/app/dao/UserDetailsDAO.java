package com.fitness.app.dao;

import org.springframework.stereotype.Component;

import com.fitness.app.entity.UserDetails;
import com.fitness.app.model.UserDetailsRequestModel;

@Component
public interface UserDetailsDAO {

    public UserDetails addUserDetails(UserDetailsRequestModel userDetails);

    public UserDetails getUserDetails(String email);
    
}
