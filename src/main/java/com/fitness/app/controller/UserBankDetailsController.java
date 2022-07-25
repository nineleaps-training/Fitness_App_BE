package com.fitness.app.controller;

import com.fitness.app.dto.request.UserBankModel;
import com.fitness.app.entity.UserBankDetailsClass;
import com.fitness.app.service.UserBankDetailsDaoImpl;
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
 * The type User bank details controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bank")
public class UserBankDetailsController {

    private final UserBankDetailsDaoImpl userBankDetailsServiceImpl;

    /**
     * Add bank details response entity.
     *
     * @param details the details
     * @return the response entity
     */
//Add or update bank details of the user.
    @PutMapping("/user/bankDetails/add")
    @ApiOperation(value = "Add Bank Details", notes = "Add bank Details of User.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Added or Successful", response = String.class),
    })
    @Validated
    public ResponseEntity<UserBankDetailsClass> addBankDetails(@Valid @RequestBody UserBankModel details) {
        UserBankDetailsClass userBankDetailsClass = userBankDetailsServiceImpl.addBankDetails(details);
        return new ResponseEntity<UserBankDetailsClass>(userBankDetailsClass, HttpStatus.OK);

    }

    /**
     * Gets bank details.
     *
     * @param email the email
     * @return the bank details
     */
//get bank details of the user to make payment.
    @GetMapping("/user/bankDetails/get/{email}")
    @ApiOperation(value = "Add Bank Details", notes = "Add bank Details of User.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Added or Successful", response = String.class),
    })
    @Validated
    @Email
    public ResponseEntity<?> getBankDetails(@Valid @PathVariable String email) {
        return new ResponseEntity<>(userBankDetailsServiceImpl.getBankDetails(email), HttpStatus.OK);
    }

    /**
     * Gets all details.
     *
     * @return the all details
     */
//List of user's bank.
    @GetMapping("/user/bankDetails/get-all")
    @ApiOperation(value = "Bank Details", notes = "Get bank Details of User.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Bank Details", response = UserBankDetailsClass.class),
    })
    public ResponseEntity<?> getAllDetails() {
        return new ResponseEntity<>(userBankDetailsServiceImpl.getAllDetails(), HttpStatus.OK);

    }
}
