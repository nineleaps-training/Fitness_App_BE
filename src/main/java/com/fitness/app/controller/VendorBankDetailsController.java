package com.fitness.app.controller;

import com.fitness.app.dao.VendorBankDetailsDao;
import com.fitness.app.entity.VendorBankDetails;
import com.fitness.app.model.VendorBankDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@RestController
@Validated
public class VendorBankDetailsController {
    @Autowired
    private VendorBankDetailsDao vendorBankDetailsDao;

    //Adding bank bankDetailsModel of the vendor
    @PutMapping("/vendor-bankdetails/add")
    public ResponseEntity<VendorBankDetails> addDetails(@Valid @RequestBody VendorBankDetailsModel bankDetailsModel) {
        VendorBankDetails vendorBankDetails = vendorBankDetailsDao.addDetails(bankDetailsModel);

        if (vendorBankDetails != null) {
            return new ResponseEntity<>(vendorBankDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //Fetching bank details of the vendor by email id
    @GetMapping("/vendor-bankdetails/get/{email}")
    public VendorBankDetails getBankDetails(@NotBlank @NotNull @NotEmpty @Email @PathVariable String email) {
        return vendorBankDetailsDao.getBankDetails(email);
    }

    //List of vendor's bank.
    @GetMapping("/vendor-bankdetails/getall/{pageNo}/{pageSize}")
    public List<VendorBankDetails> getDetails(@NotNull @Min(value = 1L) @Max(value = 1000L) @PathVariable int pageNo, @NotNull @Min (value = 1L) @Max(value = 25L)  @PathVariable int pageSize) {
        return vendorBankDetailsDao.getDetails(pageNo, pageSize);
    }

}
