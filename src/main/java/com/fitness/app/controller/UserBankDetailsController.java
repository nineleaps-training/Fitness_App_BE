package com.fitness.app.controller;

import com.fitness.app.entity.UserBankDetailsClass;
import com.fitness.app.model.UserBankModel;
import com.fitness.app.service.UserBankDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserBankDetailsController {

    @Autowired
    UserBankDetailsService userBankDetailsService;

    //Add or update bank details of the user.
    @PutMapping("/user-bankdetails/add")
    public ResponseEntity<UserBankDetailsClass> addBankDetails(@RequestBody UserBankModel details) {

        UserBankDetailsClass userBankDetailsClass = userBankDetailsService.addBankDetails(details);
        Assert.notNull(userBankDetailsClass, "UserBankDetails is null");
        return new ResponseEntity<UserBankDetailsClass>(userBankDetailsClass, HttpStatus.OK);

    }

    //get bank details of the user to make payment.
    @GetMapping("/user-bankdetails/get/{email}")
    public UserBankDetailsClass getBankDetails(@PathVariable String email) {
        return userBankDetailsService.getBankDetails(email);
    }

    //List of user's bank.
    @GetMapping("user-bankdetails/getall")
    public List<UserBankDetailsClass> getAllDetails() {
        return userBankDetailsService.getAllDetails();
    }
}
