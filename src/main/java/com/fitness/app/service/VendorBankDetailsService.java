package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.model.VendorBankDetailsModel;
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

    public VendorBankDetailsModel addDetails(VendorBankDetailsModel bankDetails) {

        UserClass vendor = userRepository.findByEmail(bankDetails.getEmail());

        if (vendor != null && vendor.getActivated()) {
            repository.save(bankDetails);
            return bankDetails;
        }

        return null;
    }

    public List<VendorBankDetailsModel> getDetails() {

        return repository.findAll();
    }

    public VendorBankDetailsModel getBankDetails(String email) {

        return repository.findByEmail(email);
    }
}
