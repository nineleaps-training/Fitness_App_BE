package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorDetails;
import com.fitness.app.exception.DataNotFoundException;
import com.fitness.app.repository.UserRepo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.fitness.app.model.UserDetailsRequestModel;
import com.fitness.app.repository.VendorDetailsRepo;

@Service
public class VendorDetailsService {

    @Autowired
    private VendorDetailsRepo vendordetailsRepository;

    @Autowired
    private UserRepo userRepository;

    // Initializing constructor
    /**
     * This constructor is used to initialize the repositories
     * 
     * @param userRepository2          - User Repository
     * @param vendorDetailsRepository2 - Vendor Details Repository
     */
    public VendorDetailsService(UserRepo userRepository2, VendorDetailsRepo vendorDetailsRepository2) {
        this.userRepository = userRepository2;
        this.vendordetailsRepository = vendorDetailsRepository2;
    }

    /**
     * This function is used to register new vendor
     * 
     * @param vendorDetails - Details of the vendor
     * @return - Vendor Details
     */
    public VendorDetails addVendorDetails(UserDetailsRequestModel vendorDetails) {

        VendorDetails vDetails = new VendorDetails();
        vDetails.setVCity(vendorDetails.getCity());
        vDetails.setVEmail(vendorDetails.getEmail());
        vDetails.setVFulladdress(vendorDetails.getFullAddress());
        vDetails.setVPostal(vendorDetails.getPostal());
        vDetails.setVGender(vendorDetails.getGender());

        UserClass user = userRepository.findByEmail(vDetails.getVEmail());

        if (user != null && user.getActivated()) {
            return vendordetailsRepository.save(vDetails); // Register new vendor.
        }
        return null;
    }

    /**
     * This function is used to fetch vendor details from his email
     * 
     * @param email - Email id of the vendor
     * @return - Details of the vendor
     * @throws DataNotFoundException
     */
    public VendorDetails getVendorDetails(String email) {

        Optional<VendorDetails> optional = vendordetailsRepository.findById(email);
        if (optional.isPresent()) {
            return optional.get(); // Fetching Vendor Details from email
        } else {
            throw new DataNotFoundException("No Vendor Details Found");
        }
    }
}
