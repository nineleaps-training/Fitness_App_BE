package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorBankDetails;
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

    public VendorBankDetails addDetails(VendorBankDetails bankDetails) {

        UserClass vendor = userRepository.findByEmail(bankDetails.getEmail());

        if(vendor!=null&& vendor.getActivated()){
            return repository.save(bankDetails);
        }

        return null;
    }

    public List<VendorBankDetails> getDetails() {

        return repository.findAll();
    }

    public VendorBankDetails getBankDetails(String email) {

        return repository.findByEmail(email);
    }
}
