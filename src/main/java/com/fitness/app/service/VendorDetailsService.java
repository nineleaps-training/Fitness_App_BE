package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.model.VendorDetailsModel;
import com.fitness.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.fitness.app.entity.VendorDetails;
import com.fitness.app.repository.VendorDetailsRepository;

@Service
public class VendorDetailsService {

    @Autowired
    private VendorDetailsRepository vendordetailsRepository;

    @Autowired
    private UserRepository userRepository;

    // register new vendor service function.
    public VendorDetailsModel addVendorDetails(VendorDetailsModel vendorDetails) {

        UserClass user = userRepository.findByEmail(vendorDetails.getEmail());

        if (user != null && user.getActivated()) {
            vendordetailsRepository.save(vendorDetails);
            return vendorDetails;
        }
        return null;
    }

    public VendorDetailsModel getVendorDetails(String email) {

        return vendordetailsRepository.findByEmail(email);
    }
}
