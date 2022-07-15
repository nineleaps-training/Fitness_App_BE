package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.UserDetails;
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

    public UserDetails addUserDetails(UserDetailsModel userDetailsModel) {

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

        return null;
    }

    public UserDetails getUserDetails(String email) {

        return userDetailsRepository.findByUserEmail(email);
    }
}
