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
    public VendorDetails addVendorDetails(VendorDetailsModel vendorDetailsModel) {

        UserClass user = userRepository.findByEmail(vendorDetailsModel.getEmail());

        if (user != null && user.getActivated()) {
            VendorDetails vendorDetails = new VendorDetails();
            vendorDetails.setEmail(vendorDetailsModel.getEmail());
            vendorDetails.setGender(vendorDetailsModel.getGender());
            vendorDetails.setFullAddress(vendorDetailsModel.getFullAddress());
            vendorDetails.setCity(vendorDetailsModel.getCity());
            vendorDetails.setPostal(vendorDetailsModel.getPostal());

            vendordetailsRepository.save(vendorDetails);
            return vendorDetails;
        }
        return null;
    }

    public VendorDetails getVendorDetails(String email) {

        return vendordetailsRepository.findByEmail(email);
    }
}
