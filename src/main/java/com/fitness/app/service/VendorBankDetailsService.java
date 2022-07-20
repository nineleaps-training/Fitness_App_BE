package com.fitness.app.service;

import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.VendorBankDetailsClass;
import com.fitness.app.dto.UserBankModel;

import java.util.List;

public interface VendorBankDetailsService {

    ApiResponse addDetails(UserBankModel bankDetails);
    List<VendorBankDetailsClass> getDetails();
    VendorBankDetailsClass getBankDetails(String email);

}
