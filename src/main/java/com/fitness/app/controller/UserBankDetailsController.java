package com.fitness.app.controller;

import com.fitness.app.entity.UserBankDetails;
import com.fitness.app.service.UserBankDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserBankDetailsController {

    @Autowired
    UserBankDetailsService userBankDetailsService;

    @PutMapping("/user-bankdetails/add")
    public ResponseEntity<?> addBankDetails(@RequestBody UserBankDetails details) {

        UserBankDetails userBankDetails = userBankDetailsService.addBankDetails(details);

        if (userBankDetails != null) {
            return new ResponseEntity<>(userBankDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user-bankdetails/get/{email}")
    public UserBankDetails getBankDetails(@PathVariable String email) {
        return userBankDetailsService.getBankDetails(email);
    }

    @GetMapping("user-bankdetails/getall")
    public List<UserBankDetails> getAllDetails() {
        return userBankDetailsService.getAllDetails();
    }
}
