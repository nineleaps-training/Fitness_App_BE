package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserDetailsModel;
import com.fitness.app.repository.UserDetailsRepository;
import com.fitness.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    public UserDetailsModel addUserDetails(UserDetailsModel userDetails) {

        UserClass user = userRepository.findByEmail(userDetails.getUserEmail());

        if (user != null && user.getActivated()) {
            userDetailsRepository.save(userDetails);
            return userDetails;
        }

        return null;
    }

    public UserDetailsModel getUserDetails(String email) {

        return userDetailsRepository.findByUserEmail(email);
    }
}
