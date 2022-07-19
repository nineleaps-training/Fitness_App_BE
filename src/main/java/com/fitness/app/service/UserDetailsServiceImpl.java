package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.UserDetailsClass;
import com.fitness.app.model.DetailsModel;
import com.fitness.app.repository.UserDetailsRepository;
import com.fitness.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {


    final private UserDetailsRepository userDetailsRepository;
    final private UserRepository userRepository;

    @Override
    public UserDetailsClass addUserDetails(DetailsModel userDetails) {
        UserClass user = userRepository.findByEmail(userDetails.getEmail());
        if (user != null && user.getActivated()) {
            UserDetailsClass details = new UserDetailsClass();
            details.setUserEmail(userDetails.getEmail());
            details.setUserGender(userDetails.getGender());
            details.setUserFullAddress(userDetails.getFullAddress());
            details.setUserCity(userDetails.getCity());
            details.setUseroPostal(userDetails.getPostal());
            userDetailsRepository.save(details);
            return details;
        }
        return null;
    }

    @Override
    public UserDetailsClass getUserDetails(String email) {
        return userDetailsRepository.findByUserEmail(email);
    }
}
