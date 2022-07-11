package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.fitness.app.model.VendorDetailsRequestModel;
import com.fitness.app.repository.VendorDetailsRepository;

@Service
public class VendorDetailsService {

    @Autowired
    private VendorDetailsRepository vendordetailsRepository;

    @Autowired
    private UserRepository userRepository;

    public VendorDetailsService(UserRepository userRepository2, VendorDetailsRepository vendorDetailsRepository2) {
        this.userRepository=userRepository2;
        this.vendordetailsRepository=vendorDetailsRepository2;
    }

    // register new vendor service function.
    public VendorDetailsRequestModel addVendorDetails(VendorDetailsRequestModel vendorDetails) {

        UserClass user = userRepository.findByEmail(vendorDetails.getVEmail());

        if (user != null && user.getActivated()) {
            return vendordetailsRepository.save(vendorDetails);
        }
        return null;
    }

    public VendorDetailsRequestModel getVendorDetails(String email) {

        return vendordetailsRepository.findByVEmail(email);
    }
}
