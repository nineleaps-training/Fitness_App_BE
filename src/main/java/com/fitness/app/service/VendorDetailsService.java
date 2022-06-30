package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.fitness.app.entity.VendorDetails;
import com.fitness.app.model.DetailsModel;
import com.fitness.app.repository.VendorDetailsRepository;

@Service
public class VendorDetailsService {

    @Autowired
    private VendorDetailsRepository vendordetailsRepository;

    @Autowired
    private UserRepository userRepository;

    // register new vendor service function.
    public VendorDetails addVendorDetails(DetailsModel vendorDetails) {

        UserClass user = userRepository.findByEmail(vendorDetails.getEmail());

        if (user != null && user.getActivated()) {
        	VendorDetails details=new VendorDetails();
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

    public VendorDetails getVendorDetails(String email) {

        return vendordetailsRepository.findByVendorEmail(email);
    }
}
