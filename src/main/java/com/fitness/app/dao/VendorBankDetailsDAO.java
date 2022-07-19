package com.fitness.app.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fitness.app.entity.VendorBankDetails;
import com.fitness.app.model.UserBankDetailsRequestModel;

@Component
public interface VendorBankDetailsDAO {

    public VendorBankDetails addDetails(UserBankDetailsRequestModel bankDetails);

    public List<VendorBankDetails> getDetails();

    public VendorBankDetails getBankDetails(String email);
    
}
