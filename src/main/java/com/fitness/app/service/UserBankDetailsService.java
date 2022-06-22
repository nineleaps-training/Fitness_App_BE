package com.fitness.app.service;

import com.fitness.app.entity.UserBankDetails;
import com.fitness.app.entity.UserClass;
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

    public UserBankDetails addBankDetails(UserBankDetails bankDetails) {

        UserClass user = userRepository.findByEmail(bankDetails.getUserEmail());

        if (user != null && user.getActivated()) {
            return repository.save(bankDetails);
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
