package com.fitness.app.service;

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

    public UserBankDetailsModel addBankDetails(UserBankDetailsModel bankDetails) {

        UserClass user = userRepository.findByEmail(bankDetails.getUserEmail());

        if (user != null && user.getActivated()) {
            repository.save(bankDetails);
            return bankDetails;
        }

        return null;
    }

    public List<UserBankDetailsModel> getAllDetails() {

        return repository.findAll();
    }

    public UserBankDetailsModel getBankDetails(String email) {

        return repository.findByUserEmail(email);
    }
}
