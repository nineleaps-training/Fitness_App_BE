package com.fitness.app.service;

import com.fitness.app.entity.UserBankDetails;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserBankDetailsModel;
import com.fitness.app.repository.UserBankDetailsRepo;
import com.fitness.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBankDetailsService {

    @Autowired
    private UserBankDetailsRepo repository;

    @Autowired
    private UserRepository userRepository;

    public UserBankDetails addBankDetails(UserBankDetailsModel bankDetailsModel) {

        UserClass user = userRepository.findByEmail(bankDetailsModel.getUserEmail());

        if (user != null && user.getActivated()) {
            UserBankDetails userBankDetails = new UserBankDetails();
            userBankDetails.setUserEmail(bankDetailsModel.getUserEmail());
            userBankDetails.setUserName(bankDetailsModel.getUserName());
            userBankDetails.setUserBankName(bankDetailsModel.getUserBankName());
            userBankDetails.setUserBranchName(bankDetailsModel.getUserBranchName());
            userBankDetails.setUserAccountNumber(bankDetailsModel.getUserAccountNumber());
            userBankDetails.setUserBankIFSC(bankDetailsModel.getUserBankIFSC());

            repository.save(userBankDetails);
            return userBankDetails;
        }

        return null;
    }

    public List<UserBankDetails> getAllDetails() {

        return repository.findAll();
    }

    public UserBankDetails getBankDetails(String email) {

        return repository.findByUserEmail(email);
    }
}
