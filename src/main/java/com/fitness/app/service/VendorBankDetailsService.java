package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorBankDetails;
import com.fitness.app.exception.DataNotFoundException;
import com.fitness.app.model.UserBankDetailsRequestModel;
import com.fitness.app.repository.BankDetailsRepo;
import com.fitness.app.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorBankDetailsService {

    @Autowired
    private BankDetailsRepo repository;

    @Autowired
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

            return repository.save(vendorBankDetails); // Adding Vendor Bank Details
        }

        return null;
    }

    /**
     * This function is used to fetch bank details of all vendors
     * 
     * @return - List of all the bank details
     */
    public List<VendorBankDetails> getDetails() {

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

        VendorBankDetails vendorBankDetails = repository.findByEmail(email); // Fetching Vendor Bank Details from email
        if (vendorBankDetails != null) {
            return vendorBankDetails;
        } else {
            throw new DataNotFoundException("No vendor found with this email");
        }

    }
}
