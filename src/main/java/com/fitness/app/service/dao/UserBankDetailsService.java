package com.fitness.app.service.dao;

import com.fitness.app.entity.UserBankDetailsClass;

import com.fitness.app.dto.requestDtos.UserBankModel;

import java.util.List;

public interface UserBankDetailsService {

     UserBankDetailsClass addBankDetails(UserBankModel bankModel);
     List<UserBankDetailsClass> getAllDetails();
     UserBankDetailsClass getBankDetails(String email);

}
