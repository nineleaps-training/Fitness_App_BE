package com.fitness.app.service;

import com.fitness.app.dao.VendorBankDetailsDAO;
import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorBankDetails;
import com.fitness.app.exception.DataNotFoundException;
import com.fitness.app.model.UserBankDetailsRequestModel;
import com.fitness.app.repository.BankDetailsRepo;
import com.fitness.app.repository.UserRepo;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

import static com.fitness.app.components.Constants.*;

@Slf4j
@Service
public class VendorBankDetailsService implements VendorBankDetailsDAO {

    private BankDetailsRepo repository;

    private UserRepo userRepository;

    // Initializing constructor
    /**
     * This constructor is used to initialize the repositories
     * 
     * @param bankDetailsRepository - Bank Details Repository
     * @param userRepository2       - User Repository
     */
    public VendorBankDetailsService(BankDetailsRepo bankDetailsRepository, UserRepo userRepository2) {
        this.repository = bankDetailsRepository;
        this.userRepository = userRepository2;
    }

    /**
     * This function is used to add the vendor bank details
     * 
     * @param bankDetails - Bank details of the vendor
     * @return - Vendor Bank Details
     */
    public VendorBankDetails addDetails(UserBankDetailsRequestModel bankDetails) {

        log.info("VendorBankDetailsService >> addDetails >> Initiated");
        UserClass vendor = userRepository.findByEmail(bankDetails.getEmail());

        if (vendor != null && vendor.getActivated()) {
            VendorBankDetails vendorBankDetails = new VendorBankDetails();
            vendorBankDetails.setEmail(bankDetails.getEmail());
            vendorBankDetails.setName(bankDetails.getEmail());
            vendorBankDetails.setAccountNumber(bankDetails.getAccountNumber());
            vendorBankDetails.setBankIFSC(bankDetails.getBankIFSC());
            vendorBankDetails.setBankName(bankDetails.getBankName());
            vendorBankDetails.setBranchName(bankDetails.getBranchName());
            vendorBankDetails.setPaymentSchedule(bankDetails.getPaymentSchedule());
            log.info("VendorBankDetailsService >> addDetails >> Terminated");
            return repository.save(vendorBankDetails); // Adding Vendor Bank Details
        }
        log.warn(NULL_RETURNED);
        return null;
    }

    /**
     * This function is used to fetch bank details of all vendors
     * 
     * @return - List of all the bank details
     */
    public List<VendorBankDetails> getDetails() {
        log.info("VendorBankDetailsService >> getDetails >> Initiated");
        return repository.findAll(); // Fetching all Vendor Bank Details
    }

    /**
     * This function is used to fetch bank details of vendor from his email
     * 
     * @param email - Email id of the vendor
     * @return - Bank details of the vendor
     * @throws DataNotFoundException
     */
    public VendorBankDetails getBankDetails(String email) {

        log.info("VendorBankDetailsService >> getBankDetails >> Initiated");
        VendorBankDetails vendorBankDetails = repository.findByEmail(email); // Fetching Vendor Bank Details from email
        if (vendorBankDetails != null) {
            return vendorBankDetails;
        } else {
            log.error("VendorBankDetailsService >> getBankDetails >> Exception thrown");
            throw new DataNotFoundException(VENDOR_NOT_FOUND);
        }

    }
}
