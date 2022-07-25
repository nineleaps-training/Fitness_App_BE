package com.fitness.app.dao;

import com.fitness.app.entity.UserDetails;
import com.fitness.app.model.UserDetailsModel;

public interface UserDetailsDao {

    UserDetails addUserDetails(UserDetailsModel userDetailsModel);
    UserDetails getUserDetails(String email);
}
