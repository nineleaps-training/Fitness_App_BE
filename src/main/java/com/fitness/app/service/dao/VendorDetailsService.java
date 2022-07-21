package com.fitness.app.service.dao;

import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.VendorDetailsClass;
import com.fitness.app.dto.requestDtos.DetailsModel;

public interface VendorDetailsService {

    ApiResponse addVendorDetails(DetailsModel detailsModel);
    VendorDetailsClass getVendorDetails(String email);

}
