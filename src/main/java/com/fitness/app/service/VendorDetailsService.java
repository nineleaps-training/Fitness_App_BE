package com.fitness.app.service;

import com.fitness.app.entity.VendorDetailsClass;
import com.fitness.app.model.DetailsModel;

public interface VendorDetailsService {

    VendorDetailsClass addVendorDetails(DetailsModel detailsModel);
    VendorDetailsClass getVendorDetails(String email);

}
