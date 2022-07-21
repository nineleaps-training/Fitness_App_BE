package com.fitness.app.service.dao;

import com.fitness.app.entity.UserDetailsClass;
import com.fitness.app.dto.requestDtos.DetailsModel;

public interface DetailsService {

    int addUserDetails(DetailsModel userDetails);
    UserDetailsClass getUserDetails(String email);


}
