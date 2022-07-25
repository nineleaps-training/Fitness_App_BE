package com.fitness.app.service;

import com.fitness.app.dao.UserBankDetailsDao;
import com.fitness.app.entity.UserBankDetails;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserBankDetailsModel;
import com.fitness.app.repository.UserBankDetailsRepository;
import com.fitness.app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserBankDetailsService implements UserBankDetailsDao {

    private UserBankDetailsRepository repository;

    private UserRepository userRepository;

    @Autowired
    public UserBankDetailsService(UserBankDetailsRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public UserBankDetails addBankDetails(UserBankDetailsModel bankDetailsModel) {
        log.info("UserBankDetailsService >> addBankDetails >> Initiated");

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
        log.warn("UserBankDetailsService >> addBankDetails >> Returns null");
        return null;
    }

    public List<UserBankDetails> getAllDetails(int pageNo, int pageSize) {
        log.info("UserBankDetailsService >> getDetails >> Initiated");
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UserBankDetails> page = repository.findAll(pageable);
        return page.getContent();
    }

    public UserBankDetails getBankDetails(String email) {
        log.info("UserBankDetailsService >> getBankDetails >> Initiated");
        return repository.findByUserEmail(email);
    }
}
