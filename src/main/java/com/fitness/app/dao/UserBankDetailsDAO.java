package com.fitness.app.dao;

import org.springframework.stereotype.Component;

import com.fitness.app.entity.UserBankDetails;
import com.fitness.app.model.UserBankDetailsRequestModel;

@Component
public interface UserBankDetailsDAO {

    public UserBankDetails addBankDetails(UserBankDetailsRequestModel bankDetails);

    public UserBankDetails getBankDetails(String email);

}
