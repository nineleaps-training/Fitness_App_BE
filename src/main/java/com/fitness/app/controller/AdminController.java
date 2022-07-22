package com.fitness.app.controller;

import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.dto.requestDtos.AdminPayModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.AdminPayClass;
import com.fitness.app.entity.UserClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.exceptions.UserNotFoundException;
import com.fitness.app.service.AdminServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
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
    @Validated
    @ApiOperation(value = "Admin Login", notes = "Admin can login for access")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Successful with Token", response = ApiResponse.class),
            @io.swagger.annotations.ApiResponse(code = 404, message = "No Data fon or Bad Credentials", response = UserNotFoundException.class)
    })
    public ResponseEntity<ApiResponse> authenticateUser(@Valid @RequestBody Authenticate authCredential) throws UserNotFoundException {
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
    @GetMapping("/private/get-all-users/{offSet}/{pageSize}")
    @ApiOperation(value = "get all Users as page-vise", notes = "Admin can get user list")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "List of user", response = List.class),
            @io.swagger.annotations.ApiResponse(code = 401, message = "UnAuthorised")
    })
    public ResponseEntity<List<UserClass>> getAllUsers(@PathVariable int offSet, @PathVariable int pageSize) {
        return new ResponseEntity<>(adminServiceImpl.getAllUsersService(offSet, pageSize), HttpStatus.OK);
    }


    /**
     * Gets all vendors.
     *
     * @return the all vendors
     */
//finding all vendors from the database.
    @GetMapping("/private/get-all-vendors/{offSet}/{pageSize}")
    @ApiOperation(value = "get all vendor list page-vise", notes = "Admin can get list of vendors.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "list of vendors", response = List.class),})
    public ResponseEntity<?> getAllVendors(@PathVariable int offSet, @PathVariable int pageSize) {
        return new ResponseEntity<>(adminServiceImpl.getAllVendorService(offSet, pageSize), HttpStatus.OK);
    }

    /**
     * Gets all gyms.
     *
     * @return the all gyms
     */
//finding all fitness centers in the list.
    @GetMapping("/private/get-all-gyms/{offSet}/{pageSize}")
    @Validated
    @ApiOperation(value = "list of all fitness centers page-vise", notes = "Admin can get list all fitness center")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "list of fitness centers", response = List.class),})
    public ResponseEntity<?> getAllGyms(@PathVariable int offSet, @PathVariable int pageSize) {
        return new ResponseEntity<>(adminServiceImpl.getAllFitnessCenter(offSet, pageSize), HttpStatus.OK);

    }

    /**
     * Gets all gyms by email.
     *
     * @param email the email
     * @return the all gyms by email
     */
//finding list of registered fitness center by email id of vendor.
    @GetMapping("/private/get-all-gyms-by-email/{email}")
    @Validated
    @ApiOperation(value = "Find fitness center of a vendor", notes = "Admin can get all fitness center of vendor")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "List of fitness centers", response = List.class),})
    public ResponseEntity<?> getAllGymsByEmail(@Email @PathVariable String email, int offSet, int pageSize) {
        return new ResponseEntity<>(adminServiceImpl.getAllGymsByEmail(email, offSet, pageSize), HttpStatus.OK);
    }


    /**
     * Vendor payment api response.
     *
     * @param vendor the vendor
     * @return the api response
     */
//finding total amount to pay to the vendor.
    @GetMapping("/private/vendor-payment/{vendor}")
    @Validated
    @ApiOperation(value = "get pay data to pay vendor", notes = "Admin can get data to pay")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Data of due payment", response = AdminPayClass.class),
            @io.swagger.annotations.ApiResponse(code = 401, message = "UnAuthorised")
    })
    public ApiResponse vendorPayment(@Valid @PathVariable String vendor) {
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
    @Validated
    public ResponseEntity<?> getDataPay(@Valid @RequestBody AdminPayModel pay) {
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
    @Validated
    public ApiResponse payNow(@Valid @RequestBody AdminPayModel payment) throws Exception {
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
    @Validated
    public ApiResponse updatingOrder(@Valid @RequestBody Map<String, String> data) {
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
    @Validated
    public ApiResponse paidHistory(@Valid @PathVariable String vendor) throws DataNotFoundException {

        return new ApiResponse(HttpStatus.OK, adminServiceImpl.paidHistoryVendor(vendor));

    }


    /**
     * Gets all number.
     *
     * @return the all number
     */
//finding total registered vendors, fitness center and fitness enthusiast.
    @GetMapping("/public/all-numbers")
    @ApiOperation(value = "get total number of user, vendor and fitness", notes = "publicly all matrix of user and fitness service provider and fitness centers ")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "List of matrix data.", response = List.class),
            @io.swagger.annotations.ApiResponse(code = 401, message = "UnAuthorised")
    })
    public ResponseEntity<List<String>> getAllNumber() {
        return new ResponseEntity<>(adminServiceImpl.getAllNumber(), HttpStatus.OK);
    }


}