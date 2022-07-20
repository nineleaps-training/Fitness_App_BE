package com.fitness.app.service;

import com.fitness.app.entity.UserDetailsClass;
import com.fitness.app.dto.DetailsModel;

public interface DetailsService {

    int addUserDetails(DetailsModel userDetails);
    UserDetailsClass getUserDetails(String email);


}
