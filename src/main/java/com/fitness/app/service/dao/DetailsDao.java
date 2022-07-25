package com.fitness.app.service.dao;

import com.fitness.app.entity.UserDetailsClass;
import com.fitness.app.dto.request.DetailsModel;

public interface DetailsDao {

    int addUserDetails(DetailsModel userDetails);
    UserDetailsClass getUserDetails(String email);


}
