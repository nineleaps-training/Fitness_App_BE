package com.fitness.app.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserBankDetails;
import com.fitness.app.entity.UserClass;

@Component
public interface PagingDAO {

    public List<GymClass> getallGyms(int pageNo, int pageSize);

    public List<UserClass> getallVendors(int pageNo, int pageSize);

    public List<UserClass> getallUsers(int pageNo, int pageSize);

    public List<UserBankDetails> getallDetails(int pageNo, int pageSize);
    
}
