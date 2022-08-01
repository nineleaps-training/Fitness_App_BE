package com.fitness.app.service;

import com.fitness.app.dao.UserBankDetailsDAO;
import com.fitness.app.entity.UserBankDetails;
import com.fitness.app.entity.UserClass;
import com.fitness.app.exception.DataNotFoundException;
import com.fitness.app.model.UserBankDetailsRequestModel;
import com.fitness.app.repository.UserBankDetailsRepo;
import com.fitness.app.repository.UserRepo;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.fitness.app.components.Constants.*;

@Slf4j
@Service
public class UserBankDetailsService implements UserBankDetailsDAO {

    private UserBankDetailsRepo repository;

    private UserRepo userRepository;

    // Initializing constructor
    /**
     * This constructor is used to initialize the repositories
     * 
     * @param userBankDetailsRepo - User Bank Details Repository
     * @param userRepository2     - User Repository
     */

    public UserBankDetailsService(UserBankDetailsRepo userBankDetailsRepo, UserRepo userRepository2) {
        this.repository = userBankDetailsRepo;
        this.userRepository = userRepository2;
    }

    /**
     * This function is used to add the bank details of the user
     * 
     * @param bankDetails - Bank Details of the user
     * @return - Bank Details
     */
    public UserBankDetails addBankDetails(UserBankDetailsRequestModel bankDetails) {

        log.info("UserBankDetailsService >> addBankDetails >> Initiated");
        UserClass user = userRepository.findByEmail(bankDetails.getEmail());

        if (user != null && user.getActivated()) {

            UserBankDetails userBankDetails = new UserBankDetails();
            userBankDetails.setUEmail(bankDetails.getEmail()); // Adding Bank Details of User
            userBankDetails.setUName(bankDetails.getBankName());
            userBankDetails.setUAccountNumber(bankDetails.getAccountNumber());
            userBankDetails.setUBankIFSC(bankDetails.getBankIFSC());
            userBankDetails.setUBankName(bankDetails.getBankName());
            userBankDetails.setUBranchName(bankDetails.getBranchName());
            log.info("UserBankDetailsService >> addBankDetails >> Terminated");
            return repository.save(userBankDetails);
        }
        log.warn(NULL_RETURNED);
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
        log.info("UserBankDetailsService >> getBankDetails >> Initiated");
        Optional<UserBankDetails> optional = repository.findById(email);
        if (optional.isPresent()) {
            return optional.get(); // Fetching Bank Details of a particular User
        } else {
            log.error("UserBankDetailsService >> getBankDetails >> Error thrown");
            throw new DataNotFoundException(NO_USER_BANK_DETAILS_FOUND);
        }
    }
}
