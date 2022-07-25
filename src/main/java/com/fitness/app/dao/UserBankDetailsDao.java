package com.fitness.app.dao;

import com.fitness.app.entity.UserBankDetails;
import com.fitness.app.model.UserBankDetailsModel;

import java.util.List;

public interface UserBankDetailsDao {

    UserBankDetails addBankDetails(UserBankDetailsModel bankDetailsModel);
    List<UserBankDetails> getAllDetails(int pageNo, int pageSize);
    UserBankDetails getBankDetails(String email);
}
