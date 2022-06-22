package com.fitness.app.controller;

import com.fitness.app.entity.VendorBankDetails;
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
    public ResponseEntity<VendorBankDetails> addDetails(@RequestBody VendorBankDetails details) {
        VendorBankDetails vendorBankDetails = vendorBankDetailsService.addDetails(details);

        if (vendorBankDetails != null) {
            return new ResponseEntity<>(vendorBankDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    //Febtching bank details of the vendor by email id
    @GetMapping("/vendor-bankdetails/get/{email}")
    public VendorBankDetails getBankDetails(@PathVariable String email) {
        return vendorBankDetailsService.getBankDetails(email);
    }

}
