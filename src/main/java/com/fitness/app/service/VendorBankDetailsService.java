package com.fitness.app.service;

import com.fitness.app.entity.VendorBankDetailsClass;
import com.fitness.app.model.UserBankModel;

import java.util.List;

public interface VendorBankDetailsService {

    VendorBankDetailsClass addDetails(UserBankModel bankDetails);
    List<VendorBankDetailsClass> getDetails();
    VendorBankDetailsClass getBankDetails(String email);

}
