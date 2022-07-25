package com.fitness.app.service;

import com.fitness.app.dao.VendorDetailsDao;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.VendorDetailsModel;
import com.fitness.app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.fitness.app.entity.VendorDetails;
import com.fitness.app.repository.VendorDetailsRepository;

@Component
@Slf4j
public class VendorDetailsService implements VendorDetailsDao {

    private VendorDetailsRepository vendordetailsRepository;

    private UserRepository userRepository;

    @Autowired
    public VendorDetailsService(VendorDetailsRepository vendordetailsRepository, UserRepository userRepository) {
        this.vendordetailsRepository = vendordetailsRepository;
        this.userRepository = userRepository;
    }

    // register new vendor service function.
    public VendorDetails addVendorDetails(VendorDetailsModel vendorDetailsModel) {
        log.info("VendorDetailsService >> addVendorDetails >> Initiated");

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
        log.warn("VendorDetailsService >> addVendorDetails >> Returns null");
        return null;
    }

    public VendorDetails getVendorDetails(String email) {
        log.info("VendorDetailsService >> getVendorDetails >> Initiated");
        return vendordetailsRepository.findByEmail(email);
    }
}
