package com.fitness.app.service;

import com.fitness.app.dao.VendorBankDetailsDao;
import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorBankDetails;
import com.fitness.app.model.VendorBankDetailsModel;
import com.fitness.app.repository.BankDetailsRepository;
import com.fitness.app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class VendorBankDetailsService implements VendorBankDetailsDao {

    private BankDetailsRepository repository;

    private UserRepository userRepository;

    @Autowired
    public VendorBankDetailsService(BankDetailsRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public VendorBankDetails addDetails(VendorBankDetailsModel bankDetailsModel) {
        log.info("VendorBankDetailsService >> addDetails >> Initiated");

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
        log.warn("VendorBankDetailsService >> addDetails >> Returns null");
        return null;
    }

    public List<VendorBankDetails> getDetails(int pageNo, int pageSize) {
        log.info("VendorBankDetailsService >> getDetails >> Initiated");
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<VendorBankDetails> page = repository.findAll(pageable);
        return page.getContent();
    }

    public VendorBankDetails getBankDetails(String email) {
        log.info("VendorBankDetailsService >> getBankDetails >> Initiated");
        return repository.findByEmail(email);
    }
}
