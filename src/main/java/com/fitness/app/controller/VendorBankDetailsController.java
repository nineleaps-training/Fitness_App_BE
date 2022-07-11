package com.fitness.app.controller;

import com.fitness.app.model.VendorBankDetailsModel;
import com.fitness.app.service.VendorBankDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VendorBankDetailsController {
    @Autowired
    private VendorBankDetailsService vendorBankDetailsService;

    //Adding bank details of the vendor
    @PutMapping("/vendor-bankdetails/add")
    public ResponseEntity<VendorBankDetailsModel> addDetails(@RequestBody VendorBankDetailsModel details) {
        VendorBankDetailsModel vendorBankDetails = vendorBankDetailsService.addDetails(details);

        if (vendorBankDetails != null) {
            return new ResponseEntity<>(vendorBankDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //Fetching bank details of the vendor by email id
    @GetMapping("/vendor-bankdetails/get/{email}")
    public VendorBankDetailsModel getBankDetails(@PathVariable String email) {
        return vendorBankDetailsService.getBankDetails(email);
    }

}
