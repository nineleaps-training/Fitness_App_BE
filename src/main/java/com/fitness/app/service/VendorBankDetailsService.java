package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.model.VendorBankDetailsRequestModel;
import com.fitness.app.repository.BankDetailsRepository;
import com.fitness.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VendorBankDetailsService {

    @Autowired
    private BankDetailsRepository repository;

    @Autowired
    private UserRepository userRepository;

    public VendorBankDetailsService(BankDetailsRepository bankDetailsRepository, UserRepository userRepository2) {
        this.repository=bankDetailsRepository;
        this.userRepository=userRepository2;
    }

    public VendorBankDetailsRequestModel addDetails(VendorBankDetailsRequestModel bankDetails) {

        UserClass vendor = userRepository.findByEmail(bankDetails.getEmail());

        if(vendor!=null&& vendor.getActivated()){
            return repository.save(bankDetails);
        }

        return null;
    }

    public List<VendorBankDetailsRequestModel> getDetails() {

        return repository.findAll();
    }

    public VendorBankDetailsRequestModel getBankDetails(String email) {

        return repository.findByEmail(email);
    }
}
