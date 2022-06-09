package com.fitness.app.controller;

import com.fitness.app.entity.VendorDetails;
import com.fitness.app.repository.UserOrderRepo;
import com.fitness.app.service.VendorDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private UserOrderRepo userOrderRepo;
    //Adding details of the vendor
    @PutMapping("/add/vendor-details")
    public ResponseEntity<?> addVendorDetails(@RequestBody VendorDetails vendorDetails) {
        VendorDetails vendorDetails1 = vendorDetailsService.addVendorDetails(vendorDetails);

        ArrayList<VendorDetails> vendor  = new ArrayList<>();
        vendor.add(vendorDetails1);

        if (vendorDetails1 != null) {
            return new ResponseEntity<>(vendor, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    //Fetching the details of the vendor by his email id
    @GetMapping("/vendor-details/{email}")
    public VendorDetails getVendorDetails(@PathVariable String email) {
        return vendorDetailsService.getVendorDetails(email);
    }

    /*@GetMapping("/get-users/{id}")
    public List<UserOrder> getUsers(@PathVariable String id)
    {
        List<UserOrder> u = userOrderRepo.findAll();
        u=u.stream().filter(e -> e.getGym().equals(id)).collect(Collectors.toList());
        return u;
    }*/

}
