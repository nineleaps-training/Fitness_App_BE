package com.fitness.app.service.dao;

import com.fitness.app.entity.UserBankDetailsClass;

import com.fitness.app.dto.request.UserBankModel;

import java.util.List;

public interface UserBankDetailsDao {

     UserBankDetailsClass addBankDetails(UserBankModel bankModel);
     List<UserBankDetailsClass> getAllDetails();
     UserBankDetailsClass getBankDetails(String email);

}
