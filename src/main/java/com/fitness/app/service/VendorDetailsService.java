package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.fitness.app.entity.VendorDetailsClass;
import com.fitness.app.model.DetailsModel;
import com.fitness.app.repository.VendorDetailsRepository;

@Service
public class VendorDetailsService {

    @Autowired
    private VendorDetailsRepository vendordetailsRepository;

    @Autowired
    private UserRepository userRepository;

    // register new vendor service function.
    public VendorDetailsClass addVendorDetails(DetailsModel vendorDetails) {

        UserClass user = userRepository.findByEmail(vendorDetails.getEmail());

        if (user != null && user.getActivated()) {
        	VendorDetailsClass details=new VendorDetailsClass();
        	details.setVendorEmail(vendorDetails.getEmail());
        	details.setVendorGender(vendorDetails.getGender());
        	details.setVendorFullAddress(vendorDetails.getFullAddress());
        	details.setVendorCity(vendorDetails.getCity());
        	details.setVendorPostal(vendorDetails.getPostal());
        	
        	
             vendordetailsRepository.save(details);
             return details;
        }
        return null;
    }

    public VendorDetailsClass getVendorDetails(String email) {

        return vendordetailsRepository.findByVendorEmail(email);
    }
}
