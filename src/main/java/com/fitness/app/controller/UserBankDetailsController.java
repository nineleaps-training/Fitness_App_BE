package com.fitness.app.controller;

import com.fitness.app.dao.UserBankDetailsDao;
import com.fitness.app.entity.UserBankDetails;
import com.fitness.app.model.UserBankDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@RestController
@Validated
public class UserBankDetailsController {

    @Autowired
    UserBankDetailsDao userBankDetailsDao;

    //Add or update bank details of the user.
    @PutMapping("/user-bankdetails/add")
    @Validated
    public ResponseEntity<UserBankDetails> addBankDetails(@Valid @RequestBody UserBankDetailsModel details) {

        UserBankDetails userBankDetails = userBankDetailsDao.addBankDetails(details);

        if (userBankDetails != null) {
            return new ResponseEntity<>(userBankDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //get bank details of the user to make payment.
    @GetMapping("/user-bankdetails/get/{email}")
    public UserBankDetails getBankDetails(@NotNull @NotBlank @NotEmpty @Email @PathVariable String email) {
        return userBankDetailsDao.getBankDetails(email);
    }

    //List of user's bank.
    @GetMapping("/user-bankdetails/getall/{pageNo}/{pageSize}")
    public List<UserBankDetails> getAllDetails(@NotNull @Min (value = 1L) @Max(value = 1000L) @PathVariable int pageNo, @NotNull @Min (value = 1L) @Max(value = 25L) @PathVariable int pageSize) {
        return userBankDetailsDao.getAllDetails(pageNo, pageSize);
    }
}
