package com.fitness.app.controller;

import com.fitness.app.entity.VendorDetailsClass;
import com.fitness.app.model.DetailsModel;
import com.fitness.app.service.VendorDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class VendorDetailsController {

    @Autowired
    private VendorDetailsService vendorDetailsService;

  
    //Adding details of the vendor
    @PutMapping("/add/vendor-details")
    public ResponseEntity<ArrayList<VendorDetailsClass>> addVendorDetails(@RequestBody DetailsModel vendorDetails) {
        VendorDetailsClass vendorDetailsClass1 = vendorDetailsService.addVendorDetails(vendorDetails);

        ArrayList<VendorDetailsClass> vendor  = new ArrayList<>();
        vendor.add(vendorDetailsClass1);
        Assert.notNull(vendor, "vendor details is null");
        return new ResponseEntity<ArrayList<VendorDetailsClass>>(vendor, HttpStatus.OK);

    }
    //Fetching the details of the vendor by his email id
    @GetMapping("/vendor-details/{email}")
    public VendorDetailsClass getVendorDetails(@PathVariable String email) {
        return vendorDetailsService.getVendorDetails(email);
    }


}
