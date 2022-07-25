package com.fitness.app.service;

import com.fitness.app.dao.UserDetailsDao;
import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.UserDetails;
import com.fitness.app.model.UserDetailsModel;
import com.fitness.app.repository.UserDetailsRepository;
import com.fitness.app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserDetailsService implements UserDetailsDao {

    private UserDetailsRepository userDetailsRepository;

    private UserRepository userRepository;

    @Autowired
    public UserDetailsService(UserDetailsRepository userDetailsRepository, UserRepository userRepository) {
        this.userDetailsRepository = userDetailsRepository;
        this.userRepository = userRepository;
    }

    public UserDetails addUserDetails(UserDetailsModel userDetailsModel) {
        log.info("UserDetailsService >> addUserDetails >> Initiated");

        UserClass user = userRepository.findByEmail(userDetailsModel.getUserEmail());

        if (user != null && user.getActivated()) {
            UserDetails userDetails = new UserDetails();
            userDetails.setUserEmail(userDetailsModel.getUserEmail());
            userDetails.setUserGender(userDetailsModel.getUserGender());
            userDetails.setUserFullAddress(userDetailsModel.getUserFullAddress());
            userDetails.setUserCity(userDetailsModel.getUserCity());
            userDetails.setUserPostal(userDetailsModel.getUserPostal());

            userDetailsRepository.save(userDetails);
            return userDetails;
        }
        log.warn("UserDetailsService >> addUserDetails >> returns null");
        return null;
    }

    public UserDetails getUserDetails(String email) {
        log.info("UserDetailsService >> getUserDetails >> Initiated");
        return userDetailsRepository.findByUserEmail(email);
    }
}
