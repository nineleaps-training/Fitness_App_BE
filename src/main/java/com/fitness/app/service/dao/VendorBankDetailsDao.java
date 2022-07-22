package com.fitness.app.service.dao;

import com.fitness.app.dto.response.ApiResponse;
import com.fitness.app.entity.VendorBankDetailsClass;
import com.fitness.app.dto.request.UserBankModel;

import java.util.List;

public interface VendorBankDetailsDao {

    ApiResponse addDetails(UserBankModel bankDetails);
    List<VendorBankDetailsClass> getDetails();
    VendorBankDetailsClass getBankDetails(String email);

}
