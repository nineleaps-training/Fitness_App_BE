package com.fitness.app.dao;

import org.springframework.stereotype.Component;

import com.fitness.app.entity.VendorDetails;
import com.fitness.app.model.UserDetailsRequestModel;

@Component
public interface VendorDetailsDAO {

    public VendorDetails addVendorDetails(UserDetailsRequestModel vendorDetails);

    public VendorDetails getVendorDetails(String email);
    
}
