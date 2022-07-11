package com.fitness.app.service;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserBankDetailsRequestModel;
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

    public UserBankDetailsService(UserBankDetailsRepo userBankDetailsRepo, UserRepository userRepository2) {
        this.repository=userBankDetailsRepo;
        this.userRepository=userRepository2;
    }

    public UserBankDetailsRequestModel addBankDetails(UserBankDetailsRequestModel bankDetails) {

        UserClass user = userRepository.findByEmail(bankDetails.getUEmail());

        if (user != null && user.getActivated()) {
            return repository.save(bankDetails);
        }

        return null;
    }

    public List<UserBankDetailsRequestModel> getAllDetails() {

        return repository.findAll();
    }

    public UserBankDetailsRequestModel getBankDetails(String email) {

        return repository.findByUEmail(email);
    }
}
