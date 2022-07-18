package com.fitness.app.service;

import com.fitness.app.entity.UserBankDetails;
import com.fitness.app.entity.UserClass;
import com.fitness.app.exception.DataNotFoundException;
import com.fitness.app.model.UserBankDetailsRequestModel;
import com.fitness.app.repository.UserBankDetailsRepo;
import com.fitness.app.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserBankDetailsService {

    @Autowired
    private UserBankDetailsRepo repository;

    @Autowired
    private UserRepo userRepository;

    // Initializing constructor
    /**
     * This constructor is used to initialize the repositories
     * 
     * @param userBankDetailsRepo - User Bank Details Respository
     * @param userRepository2     - User Repository
     */
    public UserBankDetailsService(UserBankDetailsRepo userBankDetailsRepo, UserRepo userRepository2) {
        this.repository = userBankDetailsRepo;
        this.userRepository = userRepository2;
    }

    /**
     * This function is used to add the bank details of the user
     * 
     * @param bankDetails - Bank Detals of the user
     * @return - Bank Details
     */
    public UserBankDetails addBankDetails(UserBankDetailsRequestModel bankDetails) {

        UserClass user = userRepository.findByEmail(bankDetails.getEmail());

        if (user != null && user.getActivated()) {

            UserBankDetails userBankDetails = new UserBankDetails();
            userBankDetails.setUEmail(bankDetails.getEmail()); // Adding Bank Details of User
            userBankDetails.setUName(bankDetails.getBankName());
            userBankDetails.setUaccountNumber(bankDetails.getAccountNumber());
            userBankDetails.setUbankIFSC(bankDetails.getBankIFSC());
            userBankDetails.setUbankName(bankDetails.getBankName());
            userBankDetails.setUbranchName(bankDetails.getBranchName());
            return repository.save(userBankDetails);
        }

        return null;
    }

    /**
     * This function is used to fetch the bank details of the user
     * 
     * @param email - Email id of the user
     * @return - Bank Details of the user
     * @throws DataNotFoundException
     */
    public UserBankDetails getBankDetails(String email) {
        Optional<UserBankDetails> optional = repository.findById(email);
        if (optional.isPresent()) {
            return optional.get(); // Fetching Bank Details of a particular User
        } else {
            throw new DataNotFoundException("No User Bank Details Found");
        }
    }
}
