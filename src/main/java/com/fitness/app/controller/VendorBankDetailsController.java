package com.fitness.app.controller;

import com.fitness.app.dao.VendorBankDetailsDAO;
import com.fitness.app.entity.VendorBankDetails;
import com.fitness.app.model.UserBankDetailsRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Validated
public class VendorBankDetailsController {

    @Autowired
    private VendorBankDetailsDAO vendorBankDetailsService;

    /**
     * This controller is used to add the bank details of the vendor
     * 
     * @param details - Bank details of the vendor
     * @return - Status is created if success or else bad request
     */
    @ApiOperation(value = "Adding Bank Details", notes = "Vendors can add their bank details")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Bank Details Added", response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
    @PutMapping(value = "/v1/vendorBankDetails/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Validated
    public ResponseEntity<Object> addDetails(@Valid @RequestBody UserBankDetailsRequestModel details) {
        VendorBankDetails vendorBankDetails = vendorBankDetailsService.addDetails(details);

        if (vendorBankDetails != null) {
            return new ResponseEntity<>(vendorBankDetails, HttpStatus.CREATED); // Adding bank details of the vendor
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This controller is used to fetch the bank details of the vendor from his email
     * 
     * @param email - Email id of the vendor
     * @return - Bank details of the vendor
     */
    @ApiOperation(value = "Fetch Bank Details", notes = "Fetching the bank details of the vendor from his email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vendor Fetched", response = UserBankDetailsRequestModel.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
    @GetMapping(value = "/v1/vendorBankDetails/get/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public VendorBankDetails getBankDetails(@NotBlank @NotEmpty @NotNull @Email@PathVariable String email) {
        return vendorBankDetailsService.getBankDetails(email); // Fetching bank details of the vendor by email id
    }
}
