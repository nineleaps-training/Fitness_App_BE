package com.fitness.app.controller;

import com.fitness.app.dao.PagingDAO;
import com.fitness.app.dao.UserBankDetailsDAO;
import com.fitness.app.entity.UserBankDetails;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Validated
public class UserBankDetailsController {

    @Autowired
    UserBankDetailsDAO userBankDetailsService;

    @Autowired
    PagingDAO pagingService;

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
    @PutMapping(value = "/v1/userBankDetails/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    @GetMapping(value = "/v1/userBankDetails/get/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserBankDetails getBankDetails(@NotBlank @NotEmpty @NotNull @Email @PathVariable String email) {
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
    @GetMapping(value = "/v1/userBankDetails/getall/{pageNo}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<UserBankDetails> getAllDetails(@NotNull @Min(value = 1L, message = "Page number should be mininum 1") @Max(value = 1000L, message = "Page number can be maximum 999") @PathVariable int pageNo, @NotNull @Min(value = 1L, message = "Page size should be mininum 1") @Max(value = 20L, message = "Page size can be maximum 19") @PathVariable int pageSize) {
        return pagingService.getallDetails(pageNo, pageSize); // List of user's bank.
    }
}
