package com.fitness.app.controller;

import com.fitness.app.dto.requestDtos.UserBankModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.VendorBankDetailsClass;
import com.fitness.app.service.VendorBankDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Vendor bank details controller.
 */
@RestController
@RequestMapping("/api/v1/vendor-bank")
@RequiredArgsConstructor
public class VendorBankDetailsController {

    private final VendorBankDetailsServiceImpl vendorBankDetailsServiceImpl;

    /**
     * Add details api response.
     *
     * @param details the details
     * @return the api response
     */
//Adding bank details of the vendor
    @PutMapping("/vendor-bank-details/add")
    public ApiResponse addDetails(@RequestBody UserBankModel details) {
        return vendorBankDetailsServiceImpl.addDetails(details);

    }

    /**
     * Gets bank details.
     *
     * @param email the email
     * @return the bank details
     */
//Fetching bank details of the vendor by email id
    @GetMapping("/vendor-bank-details-get/{email}")
    public ResponseEntity<VendorBankDetailsClass> getBankDetails(@PathVariable String email) {
        return new ResponseEntity<VendorBankDetailsClass>(vendorBankDetailsServiceImpl.getBankDetails(email), HttpStatus.OK);

    }

}
