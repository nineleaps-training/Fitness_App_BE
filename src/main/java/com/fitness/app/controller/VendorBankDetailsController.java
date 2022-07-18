package com.fitness.app.controller;

import com.fitness.app.entity.VendorBankDetailsClass;
import com.fitness.app.model.UserBankModel;
import com.fitness.app.service.VendorBankDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
public class VendorBankDetailsController {
    @Autowired
    private VendorBankDetailsService vendorBankDetailsService;
    //Adding bank details of the vendor
    @PutMapping("/vendor-bankdetails/add")
    public ResponseEntity<VendorBankDetailsClass> addDetails(@RequestBody UserBankModel details) {
        VendorBankDetailsClass vendorBankDetailsClass = vendorBankDetailsService.addDetails(details);
        Assert.notNull(vendorBankDetailsClass, "bank details are null");
        return new ResponseEntity<VendorBankDetailsClass>(vendorBankDetailsClass, HttpStatus.OK);

    }
    //Febtching bank details of the vendor by email id
    @GetMapping("/vendor-bankdetails/get/{email}")
    public VendorBankDetailsClass getBankDetails(@PathVariable String email) {
        return vendorBankDetailsService.getBankDetails(email);
    }

}
