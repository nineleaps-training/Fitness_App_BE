package com.fitness.app.controller;

import com.fitness.app.dto.requestDtos.DetailsModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.VendorBankDetailsClass;
import com.fitness.app.entity.VendorDetailsClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.service.VendorDetailsServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

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
    @ApiOperation(value = "Add Details", notes = "Add vendor details.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Successful", response = ApiResponse.class),
            @io.swagger.annotations.ApiResponse(code = 401, message = "Not found or bad request ", response = DataNotFoundException.class)
    })
    @Validated
    public ApiResponse addVendorDetails(@RequestBody @Valid @Email DetailsModel vendorDetails) {
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
    @ApiOperation(value = "Get Vendor details ", notes = "Vendor Details.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Details of vendor", response = VendorBankDetailsClass.class),
            @io.swagger.annotations.ApiResponse(code = 401, message = "Not found or bad request ", response = DataNotFoundException.class)
    })
    @Validated
    public ResponseEntity<VendorDetailsClass> getVendorDetails(@PathVariable String email) {

        return new ResponseEntity<VendorDetailsClass>(vendorDetailsServiceImpl.getVendorDetails(email), HttpStatus.OK);

    }


}
