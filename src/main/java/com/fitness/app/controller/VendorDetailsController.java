package com.fitness.app.controller;

import com.fitness.app.dao.VendorDetailsDao;
import com.fitness.app.entity.VendorDetails;
import com.fitness.app.model.VendorDetailsModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@RestController
@Validated
public class VendorDetailsController {

    @Autowired
    private VendorDetailsDao vendorDetailsDao;

    //Adding details of the vendor
    @PutMapping("/add/vendor-details")
    @Validated
    public ResponseEntity<ArrayList<VendorDetails>> addVendorDetails(@Valid @RequestBody VendorDetailsModel vendorDetailsModel) {
        VendorDetails vendorDetails = vendorDetailsDao.addVendorDetails(vendorDetailsModel);

        ArrayList<VendorDetails> vendor = new ArrayList<>();
        vendor.add(vendorDetails);

        if (vendorDetails != null) {
            return new ResponseEntity<>(vendor, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //Fetching the details of the vendor by his email id
    @GetMapping("/vendor-details/{email}")
    public VendorDetails getVendorDetails(@NotNull @NotEmpty @NotBlank @PathVariable String email) {
        return vendorDetailsDao.getVendorDetails(email);
    }

}
