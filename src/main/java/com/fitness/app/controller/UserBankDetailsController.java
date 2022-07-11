package com.fitness.app.controller;

import com.fitness.app.model.UserBankDetailsRequestModel;
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

    //Add or update bank details of the user.
    @ApiOperation(value = "Adding Bank Details", notes = "Users can add their bank details")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Bank Details Added", response = ResponseEntity.class),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
    @PutMapping(value = "/v1/user-bankdetails/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public ResponseEntity<Object> addBankDetails(@Valid @RequestBody UserBankDetailsRequestModel details) {

        UserBankDetailsRequestModel userBankDetails = userBankDetailsService.addBankDetails(details);

        if (userBankDetails != null) {
            return new ResponseEntity<>(userBankDetails, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //get bank details of the user to make payment.
    @ApiOperation(value = "Fetch Bank Details", notes = "Fetching the bank details of the user from his email")
	@ApiResponses(value = { @ApiResponse(code=200, message = "User Fetched", response = UserBankDetailsRequestModel.class),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
    @GetMapping(value = "/v1/user-bankdetails/get/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserBankDetailsRequestModel getBankDetails(@PathVariable String email) {
        return userBankDetailsService.getBankDetails(email);
    }

    //List of user's bank.
    @ApiOperation(value = "Get All Bank Details", notes = "Fetching Bank Details of all Users")
	@ApiResponses(value = { @ApiResponse(code=200, message = "List Fetched"),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
    @GetMapping(value = "/v1/user-bankdetails/getall", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<UserBankDetailsRequestModel> getAllDetails() {
        return userBankDetailsService.getAllDetails();
    }
}
