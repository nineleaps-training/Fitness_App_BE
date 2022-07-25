package com.fitness.app.dao;

import com.fitness.app.entity.VendorDetails;
import com.fitness.app.model.VendorDetailsModel;

public interface VendorDetailsDao {

    VendorDetails addVendorDetails(VendorDetailsModel vendorDetailsModel);
    VendorDetails getVendorDetails(String email);
}
