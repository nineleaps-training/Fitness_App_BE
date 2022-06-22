package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.UserDetails;
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

    public UserDetails addUserDetails(UserDetails userDetails) {

        UserClass user = userRepository.findByEmail(userDetails.getUserEmail());

        if (user != null && user.getActivated()) {
            return userDetailsRepository.save(userDetails);
        }

        return null;
    }

    public UserDetails getUserDetails(String email) {

        return userDetailsRepository.findByUserEmail(email);
    }
}
