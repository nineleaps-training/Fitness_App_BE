package com.fitness.app.controller;

import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.dto.AdminPayModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.UserClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.exceptions.UserNotFoundException;
import com.fitness.app.service.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * The type Admin controller.
 */
@Slf4j
@RequestMapping("/api/v1/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {


    final private AdminServiceImpl adminServiceImpl;


    /**
     * Authenticate user response entity.
     *
     * @param authCredential the auth credential
     * @return the response entity
     * @throws UserNotFoundException the user not found exception
     */
    @PostMapping("/public/login-admin")
    public ResponseEntity<ApiResponse> authenticateUser(@RequestBody Authenticate authCredential) throws UserNotFoundException {
        String jwt = adminServiceImpl.authenticateAdmin(authCredential);
        if (jwt == null) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NO_CONTENT, null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, jwt), HttpStatus.OK);

    }


    /**
     * Gets all users.
     *
     * @return the all users
     */
//finding all user details....
    @GetMapping("/private/get-all-users")
    public ResponseEntity<List<UserClass>> getAllUsers() {
        return new ResponseEntity<>(adminServiceImpl.getAllUsersService(), HttpStatus.OK);
    }


    /**
     * Gets all vendors.
     *
     * @return the all vendors
     */
//finding all vendors from the database.
    @GetMapping("/private/get-all-vendors")
    public ResponseEntity<?> getAllVendors() {
        return new ResponseEntity<>(adminServiceImpl.getAllVendorService(), HttpStatus.OK);
    }

    /**
     * Gets all gyms.
     *
     * @return the all gyms
     */
//finding all fitness centers in the list.
    @GetMapping("/private/get-all-gyms")
    public ResponseEntity<?> getAllGyms() {
        return new ResponseEntity<>(adminServiceImpl.getAllFitnessCenter(), HttpStatus.OK);

    }

    /**
     * Gets all gyms by email.
     *
     * @param email the email
     * @return the all gyms by email
     */
//finding list of registered fitness center by email id of vendor.
    @GetMapping("/private/get-all-gyms-by-email/{email}")
    public ResponseEntity<?> getAllGymsByEmail(@PathVariable String email) {
        return new ResponseEntity<>(adminServiceImpl.getAllGymsByEmail(email), HttpStatus.OK);
    }


    /**
     * Vendor payment api response.
     *
     * @param vendor the vendor
     * @return the api response
     */
//finding total amount to pay to the vendor.
    @GetMapping("/private/vendor-payment/{vendor}")
    public ApiResponse vendorPayment(@PathVariable String vendor) {
        return new ApiResponse(HttpStatus.OK, adminServiceImpl.vendorPayment(vendor));

    }

    /**
     * Gets data pay.
     *
     * @param pay the pay
     * @return the data pay
     */
//demo api for payment.
    @GetMapping("/private/get-data-pay")
    public ResponseEntity<?> getDataPay(@RequestBody AdminPayModel pay) {
        return new ResponseEntity<>(adminServiceImpl.getDataPay(pay), HttpStatus.OK);
    }


    /**
     * Pay now api response.
     *
     * @param payment the payment
     * @return the api response
     * @throws Exception the exception
     */
//Initiating payment to the vendor
    @PutMapping("/private/pay-vendor-now")
    @ResponseBody
    public ApiResponse payNow(@RequestBody AdminPayModel payment) throws Exception {
        return adminServiceImpl.PayNow(payment);
    }


    /**
     * Updating order api response.
     *
     * @param data the data
     * @return the api response
     */
//updating status of payment maid to the vendor by admin.
    @PutMapping("/private/update-vendor-payment")
    public ApiResponse updatingOrder(@RequestBody Map<String, String> data) {
        return new ApiResponse(HttpStatus.OK, adminServiceImpl.updatePayment(data));
    }


    /**
     * Paid history api response.
     *
     * @param vendor the vendor
     * @return the api response
     * @throws DataNotFoundException the data not found exception
     */
//Finding payment history of the vendor.
    @GetMapping("/private/paid-history/{vendor}")
    public ApiResponse paidHistory(@PathVariable String vendor) throws DataNotFoundException {

        return new ApiResponse(HttpStatus.OK, adminServiceImpl.paidHistoryVendor(vendor));

    }


    /**
     * Gets all number.
     *
     * @return the all number
     */
//finding total registered vendors, fitness center and fitness enthusiast.
    @GetMapping("/public/all-numbers")
    public ResponseEntity<List<String>> getAllNumber() {
        return new ResponseEntity<>(adminServiceImpl.getAllNumber(), HttpStatus.OK);
    }


}