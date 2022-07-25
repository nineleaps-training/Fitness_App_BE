package com.fitness.app.dao;

import com.fitness.app.entity.VendorBankDetails;
import com.fitness.app.model.VendorBankDetailsModel;

import java.util.List;

public interface VendorBankDetailsDao {

    VendorBankDetails addDetails(VendorBankDetailsModel bankDetailsModel);
    List<VendorBankDetails> getDetails(int pageNo, int pageSize);
    VendorBankDetails getBankDetails(String email);
}
