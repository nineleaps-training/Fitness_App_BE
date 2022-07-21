package com.fitness.app.controller;

import com.fitness.app.dto.requestDtos.DetailsModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.VendorDetailsClass;
import com.fitness.app.service.VendorDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Vendor details controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vendor-details")
public class VendorDetailsController {

    private final VendorDetailsServiceImpl vendorDetailsServiceImpl;


    /**
     * Add vendor details api response.
     *
     * @param vendorDetails the vendor details
     * @return the api response
     */
//Adding details of the vendor
    @PutMapping("/add-vendor-details")
    public ApiResponse addVendorDetails(@RequestBody DetailsModel vendorDetails) {
        return vendorDetailsServiceImpl.addVendorDetails(vendorDetails);


    }

    /**
     * Gets vendor details.
     *
     * @param email the email
     * @return the vendor details
     */
//Fetching the details of the vendor by his email id
    @GetMapping("/get-vendor-details/{email}")
    public ResponseEntity<VendorDetailsClass> getVendorDetails(@PathVariable String email) {

        return new ResponseEntity<VendorDetailsClass>(vendorDetailsServiceImpl.getVendorDetails(email), HttpStatus.OK);

    }


}
