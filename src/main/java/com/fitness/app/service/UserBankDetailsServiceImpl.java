package com.fitness.app.service;

import com.fitness.app.entity.UserBankDetailsClass;
import com.fitness.app.entity.UserClass;
import com.fitness.app.dto.UserBankModel;
import com.fitness.app.repository.UserBankDetailsRepository;
import com.fitness.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserBankDetailsServiceImpl implements UserBankDetailsService {


    final private UserBankDetailsRepository repository;
    final private UserRepository userRepository;

    @Override
    public UserBankDetailsClass addBankDetails(UserBankModel bankDetails) {

        UserClass user = userRepository.findByEmail(bankDetails.getEmail());
        UserBankDetailsClass bank = new UserBankDetailsClass();
        bank.setEmail(bankDetails.getEmail());
        bank.setName(bankDetails.getName());
        bank.setBankName(bankDetails.getBankName());
        bank.setAccountNumber(bankDetails.getAccountNumber());
        bank.setBankIFSC(bankDetails.getBankIFSC());
        bank.setBranchName(bankDetails.getBranchName());
        if (user != null && user.getActivated()) {
            repository.save(bank);
            return bank;
        }
        return null;
    }

    @Override
    public List<UserBankDetailsClass> getAllDetails() {
        return repository.findAll();
    }

    @Override
    public UserBankDetailsClass getBankDetails(String email) {
        return repository.findByEmail(email);
    }
}
