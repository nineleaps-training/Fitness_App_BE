package com.fitness.app.service;

import com.fitness.app.dto.requestDtos.UserBankModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorBankDetailsClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.repository.BankDetailsRepository;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.service.dao.VendorBankDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VendorBankDetailsServiceImpl implements VendorBankDetailsService {


    final private BankDetailsRepository bankDetailsRepo;
    final private UserRepository userRepo;

    @Override
    public ApiResponse addDetails(UserBankModel bankDetails) {

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
            return new ApiResponse(HttpStatus.OK, "Successful");
        }

        return new ApiResponse(HttpStatus.NO_CONTENT, "Failed");
    }

    @Override
    public List<VendorBankDetailsClass> getDetails() {
        return bankDetailsRepo.findAll();
    }

    @Override
    public VendorBankDetailsClass getBankDetails(String email) throws DataNotFoundException {

        try {
            return bankDetailsRepo.findByVendorEmail(email);
        } catch (Exception e) {
            log.error("VendorBankDetails ::-> getBankDetails :: Exception found due to: {}", e.getMessage());
            throw new DataNotFoundException("No vendor bank exist by this name: ");
        }
    }
}
