package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorBankDetailsClass;
import com.fitness.app.model.UserBankModel;
import com.fitness.app.repository.BankDetailsRepository;
import com.fitness.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorBankDetailsServiceImpl implements VendorBankDetailsService {


    final private BankDetailsRepository bankDetailsRepo;
    final private UserRepository userRepo;

    @Override
    public VendorBankDetailsClass addDetails(UserBankModel bankDetails) {

        UserClass vendor = userRepo.findByEmail(bankDetails.getEmail());

        if (vendor != null && vendor.getActivated()) {
            VendorBankDetailsClass bank = new VendorBankDetailsClass();
            bank.setVendorEmail(bankDetails.getEmail());
            bank.setVendorName(vendor.getFullName());
            bank.setVendorBankName(bankDetails.getBankName());
            bank.setVendorAccountNumber(bankDetails.getAccountNumber());
            bank.setVendorBranchName(bankDetails.getBranchName());
            bank.setVendorBankIFSC(bankDetails.getBankIFSC());
            bank.setPaymentSchedule(bankDetails.getSchedule());
            bankDetailsRepo.save(bank);
            return bank;
        }

        return null;
    }

    @Override
    public List<VendorBankDetailsClass> getDetails() {

        return bankDetailsRepo.findAll();
    }

    @Override
    public VendorBankDetailsClass getBankDetails(String email) {

        return bankDetailsRepo.findByVendorEmail(email);
    }
}
