package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorBankDetails;
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

    public VendorBankDetails addDetails(VendorBankDetailsModel bankDetailsModel) {

        UserClass vendor = userRepository.findByEmail(bankDetailsModel.getEmail());

        if (vendor != null && vendor.getActivated()) {
            VendorBankDetails vendorBankDetails = new VendorBankDetails();
            vendorBankDetails.setEmail(bankDetailsModel.getEmail());
            vendorBankDetails.setName(bankDetailsModel.getName());
            vendorBankDetails.setBankName(bankDetailsModel.getBankName());
            vendorBankDetails.setBranchName(bankDetailsModel.getBranchName());
            vendorBankDetails.setAccountNumber(bankDetailsModel.getAccountNumber());
            vendorBankDetails.setBankIFSC(bankDetailsModel.getBankIFSC());
            vendorBankDetails.setPaymentSchedule(bankDetailsModel.getPaymentSchedule());

            repository.save(vendorBankDetails);
            return vendorBankDetails;
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
