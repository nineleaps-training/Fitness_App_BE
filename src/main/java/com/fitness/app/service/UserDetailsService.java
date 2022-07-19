package com.fitness.app.service;

import com.fitness.app.entity.UserDetailsClass;
import com.fitness.app.model.DetailsModel;

public interface UserDetailsService {

    UserDetailsClass addUserDetails(DetailsModel userDetails);
    UserDetailsClass getUserDetails(String email);


}
