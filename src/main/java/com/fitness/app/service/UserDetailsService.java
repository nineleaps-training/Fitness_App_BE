package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.UserDetails;
import com.fitness.app.exception.DataNotFoundException;
import com.fitness.app.model.UserDetailsRequestModel;
import com.fitness.app.repository.UserDetailsRepo;
import com.fitness.app.repository.UserRepo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    @Autowired
    private UserDetailsRepo userDetailsRepository;

    @Autowired
    private UserRepo userRepository;

    // Initializing constructor
    /**
     * This constructor is used to initialize the repositories
     * 
     * @param userRepository2        - User Repository
     * @param userDetailsRepository2 - User Details Repository
     */
    public UserDetailsService(UserRepo userRepository2, UserDetailsRepo userDetailsRepository2) {
        this.userRepository = userRepository2;
        this.userDetailsRepository = userDetailsRepository2;
    }

    /**
     * This function is used to add the details of the user
     * 
     * @param userDetails - User Details
     * @return - Details of the user
     */
    public UserDetails addUserDetails(UserDetailsRequestModel userDetails) {

        UserClass user = userRepository.findByEmail(userDetails.getEmail());

        if (user != null && user.getActivated()) {
            UserDetails userDetails2 = new UserDetails();
            userDetails2.setUEmail(userDetails.getEmail()); // Adding User Details
            userDetails2.setUFulladdress(userDetails.getFullAddress());
            userDetails2.setUGender(userDetails.getGender());
            userDetails2.setUCity(userDetails.getCity());
            userDetails2.setUPostal(userDetails.getPostal());
            return userDetailsRepository.save(userDetails2);
        }

        return null;
    }

    /**
     * This function is used to fetch the user details from his email
     * 
     * @param email - Email id of the user
     * @return - Details of the user
     * @throws DataNotFoundException
     */
    public UserDetails getUserDetails(String email) {

        Optional<UserDetails> optional = userDetailsRepository.findById(email);
        if (optional.isPresent()) {
            return optional.get(); // Fetching User Details from email
        } else {
            throw new DataNotFoundException("No User Details Found");
        }
    }
}
