package com.fitness.app.controller;

import com.fitness.app.entity.VendorBankDetails;
import com.fitness.app.service.VendorBankDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VendorBankDetailsController {
    @Autowired
    private VendorBankDetailsService vendorBankDetailsService;

    @PutMapping("/vendor-bankdetails/add")
    public ResponseEntity<?> addDetails(@RequestBody VendorBankDetails details) {
        VendorBankDetails vendorBankDetails = vendorBankDetailsService.addDetails(details);

        if (vendorBankDetails != null) {
            return new ResponseEntity<>(vendorBankDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/vendor-bankdetails/get/{email}")
    public VendorBankDetails getBankDetails(@PathVariable String email) {
        return vendorBankDetailsService.getBankDetails(email);
    }

    @GetMapping("/vendor-bankdetails/get")
    public List<VendorBankDetails> getDetails() {
        return vendorBankDetailsService.getDetails();
    }
}
