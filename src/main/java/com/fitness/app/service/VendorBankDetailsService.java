package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorBankDetailsClass;
import com.fitness.app.model.UserBankModel;
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

    public VendorBankDetailsClass addDetails(UserBankModel bankDetails) {

        UserClass vendor = userRepository.findByEmail(bankDetails.getEmail());

        if(vendor!=null&& vendor.getActivated()){
        	VendorBankDetailsClass bank=new VendorBankDetailsClass();
        	bank.setVendorEmail(bankDetails.getEmail());
        	bank.setVendorName(vendor.getFullName());
        	bank.setVendorBankName(bankDetails.getBankName());
        	bank.setVendorAccountNumber(bankDetails.getAccountNumber());
        	bank.setVendorBranchName(bankDetails.getBranchName());
        	bank.setVendorBankIFSC(bankDetails.getBankIFSC());
        	bank.setPaymentSchedule(bankDetails.getSchedule());
            repository.save(bank);
            return bank;
        }

        return null;
    }

    public List<VendorBankDetailsClass> getDetails() {

        return repository.findAll();
    }

    public VendorBankDetailsClass getBankDetails(String email) {

        return repository.findByVendorEmail(email);
    }
}
