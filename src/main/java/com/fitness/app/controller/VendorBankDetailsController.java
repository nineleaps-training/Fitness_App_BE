package com.fitness.app.controller;

import com.fitness.app.model.VendorBankDetailsRequestModel;
import com.fitness.app.service.VendorBankDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.validation.Valid;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class VendorBankDetailsController {
    @Autowired
    private VendorBankDetailsService vendorBankDetailsService;
    //Adding bank details of the vendor
    @ApiOperation(value = "Adding Bank Details", notes = "Vendors can add their bank details")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Bank Details Added", response = ResponseEntity.class),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
    @PutMapping(value = "/v1/vendor-bankdetails/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public ResponseEntity<Object> addDetails(@Valid @RequestBody VendorBankDetailsRequestModel details) {
        VendorBankDetailsRequestModel vendorBankDetails = vendorBankDetailsService.addDetails(details);

        if (vendorBankDetails != null) {
            return new ResponseEntity<>(vendorBankDetails, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }   
    }
    //Febtching bank details of the vendor by email id
    @ApiOperation(value = "Fetch Bank Details", notes = "Fetching the bank details of the vendor from his email")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Vendor Fetched", response = VendorBankDetailsRequestModel.class),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
    @GetMapping(value = "/v1/vendor-bankdetails/get/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public VendorBankDetailsRequestModel getBankDetails(@PathVariable String email) {
        return vendorBankDetailsService.getBankDetails(email);
    }
}
