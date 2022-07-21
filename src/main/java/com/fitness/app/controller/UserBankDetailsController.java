package com.fitness.app.controller;

import com.fitness.app.dto.requestDtos.UserBankModel;
import com.fitness.app.entity.UserBankDetailsClass;
import com.fitness.app.service.UserBankDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type User bank details controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bank")
public class UserBankDetailsController {

    private final UserBankDetailsServiceImpl userBankDetailsServiceImpl;

    /**
     * Add bank details response entity.
     *
     * @param details the details
     * @return the response entity
     */
//Add or update bank details of the user.
    @PutMapping("/private/user-bankDetails/add")
    public ResponseEntity<UserBankDetailsClass> addBankDetails(@RequestBody UserBankModel details) {
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
    @GetMapping("/private/user-bankDetails/get/{email}")
    public ResponseEntity<?> getBankDetails(@PathVariable String email) {
        return new ResponseEntity<>(userBankDetailsServiceImpl.getBankDetails(email), HttpStatus.OK);
    }

    /**
     * Gets all details.
     *
     * @return the all details
     */
//List of user's bank.
    @GetMapping("/private/user-bankDetails/get-all")
    public ResponseEntity<?> getAllDetails() {
        return new ResponseEntity<>(userBankDetailsServiceImpl.getAllDetails(), HttpStatus.OK);

    }
}
