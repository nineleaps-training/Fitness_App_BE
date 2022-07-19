package com.fitness.app.controller;

import com.fitness.app.entity.VendorDetailsClass;
import com.fitness.app.model.DetailsModel;
<<<<<<< HEAD
import com.fitness.app.service.VendorDetailsService;
=======
import com.fitness.app.service.VendorDetailsServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48

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
<<<<<<< HEAD
    private VendorDetailsService vendorDetailsService;
=======
    private VendorDetailsServiceImpl vendorDetailsServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48

  
    //Adding details of the vendor
    @PutMapping("/add/vendor-details")
    public ResponseEntity<ArrayList<VendorDetailsClass>> addVendorDetails(@RequestBody DetailsModel vendorDetails) {
<<<<<<< HEAD
        VendorDetailsClass vendorDetailsClass1 = vendorDetailsService.addVendorDetails(vendorDetails);
=======
        VendorDetailsClass vendorDetailsClass1 = vendorDetailsServiceImpl.addVendorDetails(vendorDetails);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48

        ArrayList<VendorDetailsClass> vendor  = new ArrayList<>();
        vendor.add(vendorDetailsClass1);
        Assert.notNull(vendor, "vendor details is null");
        return new ResponseEntity<ArrayList<VendorDetailsClass>>(vendor, HttpStatus.OK);

    }
    //Fetching the details of the vendor by his email id
    @GetMapping("/vendor-details/{email}")
    public VendorDetailsClass getVendorDetails(@PathVariable String email) {
<<<<<<< HEAD
        return vendorDetailsService.getVendorDetails(email);
=======
        return vendorDetailsServiceImpl.getVendorDetails(email);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
    }


}
