package com.fitness.app.service;

import com.fitness.app.dao.VendorDetailsDAO;
import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorDetails;
import com.fitness.app.exception.DataNotFoundException;
import com.fitness.app.repository.UserRepo;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fitness.app.model.UserDetailsRequestModel;
import com.fitness.app.repository.VendorDetailsRepo;

import lombok.extern.slf4j.Slf4j;

import static com.fitness.app.components.Constants.NO_VENDOR_DETAILS_FOUND;

@Slf4j
@Service
public class VendorDetailsService implements VendorDetailsDAO {

    private VendorDetailsRepo vendorDetailsRepository;

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
        this.vendorDetailsRepository = vendorDetailsRepository2;
    }

    /**
     * This function is used to register new vendor
     * 
     * @param vendorDetails - Details of the vendor
     * @return - Vendor Details
     */
    public VendorDetails addVendorDetails(UserDetailsRequestModel vendorDetails) {

        log.info("VendorDetailsService >> addVendorDetails >> Initiated");
        VendorDetails vDetails = new VendorDetails();
        vDetails.setVCity(vendorDetails.getCity());
        vDetails.setVEmail(vendorDetails.getEmail());
        vDetails.setVFullAddress(vendorDetails.getFullAddress());
        vDetails.setVPostal(vendorDetails.getPostal());
        vDetails.setVGender(vendorDetails.getGender());

        UserClass user = userRepository.findByEmail(vDetails.getVEmail());

        if (user != null && user.getActivated()) {
            return vendorDetailsRepository.save(vDetails); // Register new vendor.
        }

        log.warn("VendorDetailsService >> addVendorDetails >> Null is returned");
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
        log.info("VendorDetailsService >> getVendorDetails >> Initiated");
        Optional<VendorDetails> optional = vendorDetailsRepository.findById(email);
        if (optional.isPresent()) {
            return optional.get(); // Fetching Vendor Details from email
        } else {
            log.error("VendorDetailsService >> getVendorDetails >> Exception thrown");
            throw new DataNotFoundException(NO_VENDOR_DETAILS_FOUND);
        }
    }
}
