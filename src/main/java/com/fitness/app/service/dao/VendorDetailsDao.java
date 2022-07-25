package com.fitness.app.service.dao;

import com.fitness.app.dto.response.ApiResponse;
import com.fitness.app.entity.VendorDetailsClass;
import com.fitness.app.dto.request.DetailsModel;


public interface VendorDetailsDao {

    ApiResponse addVendorDetails(DetailsModel detailsModel);
    VendorDetailsClass getVendorDetails(String email);

}
