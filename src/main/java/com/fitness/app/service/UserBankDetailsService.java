package com.fitness.app.service;

import com.fitness.app.entity.UserBankDetailsClass;

import com.fitness.app.model.UserBankModel;

import java.util.List;

public interface UserBankDetailsService {

     UserBankDetailsClass addBankDetails(UserBankModel bankModel);
     List<UserBankDetailsClass> getAllDetails();
     UserBankDetailsClass getBankDetails(String email);

}
