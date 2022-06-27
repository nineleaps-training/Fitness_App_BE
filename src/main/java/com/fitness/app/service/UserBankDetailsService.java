package com.fitness.app.service;

import com.fitness.app.entity.UserBankDetails;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserBankModel;
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

    public UserBankDetails addBankDetails(UserBankModel bankDetails) {

        UserClass user = userRepository.findByEmail(bankDetails.getEmail());
        UserBankDetails bank=new UserBankDetails();
        bank.setEmail(bankDetails.getEmail());
        bank.setName(bankDetails.getName());
        bank.setBankName(bankDetails.getBankName());
        bank.setAccountNumber(bankDetails.getAccountNumber());
        bank.setBankIFSC(bankDetails.getBankIFSC());
        bank.setBranchName(bankDetails.getBranchName());
       
        if (user != null && user.getActivated()) {
            return repository.save(bank);
        }

        return null;
    }

    public List<UserBankDetails> getAllDetails() {

        return repository.findAll();
    }

    public UserBankDetails getBankDetails(String email) {

        return repository.findByEmail(email);
    }
}
