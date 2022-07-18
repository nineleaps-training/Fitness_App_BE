package com.fitness.app.controller;

import com.fitness.app.entity.UserBankDetails;
import com.fitness.app.model.UserBankDetailsRequestModel;
import com.fitness.app.service.PagingService;
import com.fitness.app.service.UserBankDetailsService;
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

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserBankDetailsController {

    @Autowired
    UserBankDetailsService userBankDetailsService;

    @Autowired
    PagingService pagingService;

    /**
     * This controller is used to add or update the bank details of the user
     * 
     * @param details - Details of the bank
     * @return - Response Created or Bad Request
     */
    @ApiOperation(value = "Adding Bank Details", notes = "Users can add their bank details")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Bank Details Added", response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
    @PutMapping(value = "/v1/user-bankdetails/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Validated
    public ResponseEntity<Object> addBankDetails(@Valid @RequestBody UserBankDetailsRequestModel details) {

        UserBankDetails userBankDetails = userBankDetailsService.addBankDetails(details); // Add or update bank details of the user.

        if (userBankDetails != null) {
            return new ResponseEntity<>(userBankDetails, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This controller is used to fetch the bank details of the user for payment
     * 
     * @param email - Email id of the user
     * @return - Bank detials of the user
     */
    @ApiOperation(value = "Fetch Bank Details", notes = "Fetching the bank details of the user from his email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User Fetched", response = UserBankDetailsRequestModel.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
    @GetMapping(value = "/v1/user-bankdetails/get/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserBankDetails getBankDetails(@PathVariable String email) {
        return userBankDetailsService.getBankDetails(email); // Returning bank details of the user to make payment.
    }

    /**
     * This controller is used to fetch bank details of all the registered users
     * 
     * @param pageNo   - Page Number
     * @param pageSize - Size of the page
     * @return - List of bank details of all the users
     */
    @ApiOperation(value = "Get All Bank Details", notes = "Fetching Bank Details of all Users")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "List Fetched"),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
    @GetMapping(value = "/v1/user-bankdetails/getall/{pageNo}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<UserBankDetails> getAllDetails(@PathVariable int pageNo, @PathVariable int pageSize) {
        return pagingService.getallDetails(pageNo, pageSize); // List of user's bank.
    }
}
