package com.fitness.app.controller;

import com.fitness.app.entity.UserBankDetailsClass;
import com.fitness.app.model.UserBankModel;
<<<<<<< HEAD
import com.fitness.app.service.UserBankDetailsService;
=======
import com.fitness.app.service.UserBankDetailsServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserBankDetailsController {

    @Autowired
<<<<<<< HEAD
    UserBankDetailsService userBankDetailsService;
=======
    UserBankDetailsServiceImpl userBankDetailsServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48

    //Add or update bank details of the user.
    @PutMapping("/user-bankdetails/add")
    public ResponseEntity<UserBankDetailsClass> addBankDetails(@RequestBody UserBankModel details) {

<<<<<<< HEAD
        UserBankDetailsClass userBankDetailsClass = userBankDetailsService.addBankDetails(details);
=======
        UserBankDetailsClass userBankDetailsClass = userBankDetailsServiceImpl.addBankDetails(details);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
        Assert.notNull(userBankDetailsClass, "UserBankDetails is null");
        return new ResponseEntity<UserBankDetailsClass>(userBankDetailsClass, HttpStatus.OK);

    }

    //get bank details of the user to make payment.
    @GetMapping("/user-bankdetails/get/{email}")
    public UserBankDetailsClass getBankDetails(@PathVariable String email) {
<<<<<<< HEAD
        return userBankDetailsService.getBankDetails(email);
=======
        return userBankDetailsServiceImpl.getBankDetails(email);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
    }

    //List of user's bank.
    @GetMapping("user-bankdetails/getall")
    public List<UserBankDetailsClass> getAllDetails() {
<<<<<<< HEAD
        return userBankDetailsService.getAllDetails();
=======
        return userBankDetailsServiceImpl.getAllDetails();
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
    }
}
