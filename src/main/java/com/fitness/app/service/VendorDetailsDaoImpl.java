package com.fitness.app.service;

import com.fitness.app.dto.request.DetailsModel;
import com.fitness.app.dto.response.ApiResponse;
import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorDetailsClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.repository.VendorDetailsRepository;
import com.fitness.app.service.dao.VendorDetailsDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Component
public class VendorDetailsDaoImpl implements VendorDetailsDao {


    final private VendorDetailsRepository vendordetailsRepository;
    final private UserRepository userRepository;

    // register new vendor service function.
    @Override
    public ApiResponse addVendorDetails(DetailsModel vendorDetails) {

        UserClass user = userRepository.findByEmail(vendorDetails.getEmail());

        if (user != null && user.getActivated()) {
            VendorDetailsClass details = new VendorDetailsClass();
            details.setVendorEmail(vendorDetails.getEmail());
            details.setVendorGender(vendorDetails.getGender());
            details.setVendorFullAddress(vendorDetails.getFullAddress());
            details.setVendorCity(vendorDetails.getCity());
            details.setVendorPostal(vendorDetails.getPostal());


            vendordetailsRepository.save(details);
            return new ApiResponse(HttpStatus.OK, "Successful");
        }
        return new ApiResponse(HttpStatus.NO_CONTENT, "Failed");
    }

    @Override
    public VendorDetailsClass getVendorDetails(String email) throws DataNotFoundException {
        try{
            return vendordetailsRepository.findByVendorEmail(email);
        }
        catch (Exception e)
        {
            throw new DataNotFoundException("No Details found for this vendor:");
        }
    }
}
