package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.UserDetailsClass;
import com.fitness.app.dto.request.DetailsModel;
import com.fitness.app.repository.UserDetailsRepository;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.service.dao.DetailsDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Component
public class DetailsDaoImpl implements DetailsDao {


    final private UserDetailsRepository userDetailsRepository;
    final private UserRepository userRepository;

    @Override
    public int addUserDetails(DetailsModel userDetails) {
        UserClass user = userRepository.findByEmail(userDetails.getEmail());
        if (user != null && user.getActivated()) {
            UserDetailsClass details = new UserDetailsClass();
            details.setUserEmail(userDetails.getEmail());
            details.setUserGender(userDetails.getGender());
            details.setUserFullAddress(userDetails.getFullAddress());
            details.setUserCity(userDetails.getCity());
            details.setUseroPostal(userDetails.getPostal());
            userDetailsRepository.save(details);
            return 200;
        }
        return 100;
    }

    @Override
    public UserDetailsClass getUserDetails(String email) {
        return userDetailsRepository.findByUserEmail(email);
    }
}
