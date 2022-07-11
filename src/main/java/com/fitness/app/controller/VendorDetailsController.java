package com.fitness.app.controller;

import com.fitness.app.model.VendorBankDetailsRequestModel;
import com.fitness.app.model.VendorDetailsRequestModel;
import com.fitness.app.service.VendorDetailsService;

import javax.validation.Valid;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class VendorDetailsController {

    @Autowired
    private VendorDetailsService vendorDetailsService;

    //Adding details of the vendor
    @ApiOperation(value = "Add Vendor Details", notes = "Adding the details of the vendor")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Vendor Details Added", response = ResponseEntity.class),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
    @PutMapping(value = "/v1/add/vendor-details", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public ResponseEntity<Object> addVendorDetails(@Valid @RequestBody VendorDetailsRequestModel vendorDetails) {
        VendorDetailsRequestModel vendorDetails1 = vendorDetailsService.addVendorDetails(vendorDetails);

        ArrayList<VendorDetailsRequestModel> vendor  = new ArrayList<>();
        vendor.add(vendorDetails1);

        if (vendorDetails1 != null) {
            return new ResponseEntity<>(vendor, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //Fetching the details of the vendor by his email id
    @ApiOperation(value = "Getting vendor details", notes = "Fetching the details of the vendor from his email")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Vendor Fetched", response = VendorBankDetailsRequestModel.class),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
    @GetMapping(value = "/v1/vendor-details/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public VendorDetailsRequestModel getVendorDetails(@PathVariable String email) {
        return vendorDetailsService.getVendorDetails(email);
    }
}
