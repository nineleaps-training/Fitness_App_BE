package com.fitness.app.controller;

import com.fitness.app.dto.request.UserBankModel;
import com.fitness.app.dto.response.ApiResponse;
import com.fitness.app.entity.VendorBankDetailsClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.service.dao.VendorBankDetailsDao;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

/**
 * The type Vendor bank details controller.
 */
@RestController
@RequestMapping("/api/v1/vendor-bank")
@RequiredArgsConstructor
public class VendorBankDetailsController {

    private final VendorBankDetailsDao vendorBankDetailsDao;

    /**
     * Add details api response.
     *
     * @param details the details
     * @return the api response
     */
//Adding bank details of the vendor
    @PutMapping("/vendor/bank/details/add")
    @ApiOperation(value = "Add Bank Details ", notes = "Add vendor details: .")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Successful", response = String.class),
            @io.swagger.annotations.ApiResponse(code = 401, message = "Not found or bad request ", response = DataNotFoundException.class)
    })
    @Validated
    public ApiResponse addDetails(@RequestBody @Valid UserBankModel details) {
        return vendorBankDetailsDao.addDetails(details);

    }

    /**
     * Gets bank details.
     *
     * @param email the email
     * @return the bank details
     */
//Fetching bank details of the vendor by email id
    @GetMapping("/vendor/bank/details/get/{email}")
    @ApiOperation(value = "Get bank Details ", notes = "Get bank details of vendor.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Bank Details", response = VendorBankDetailsClass.class),
            @io.swagger.annotations.ApiResponse(code = 401, message = "Not found or bad request ", response = DataNotFoundException.class)
    })
    @Validated
    public ResponseEntity<VendorBankDetailsClass> getBankDetails(@PathVariable @Valid @Email String email) {
        return new ResponseEntity<VendorBankDetailsClass>(vendorBankDetailsDao.getBankDetails(email), HttpStatus.OK);

    }

}
