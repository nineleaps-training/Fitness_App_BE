package com.fitness.app.controller;

import com.fitness.app.dao.AdminDao;
import com.fitness.app.entity.AdminPay;
import com.fitness.app.model.AdminPayModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.SignUpResponse;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import javax.mail.AuthenticationFailedException;
import javax.validation.Valid;
import javax.validation.constraints.*;


@Slf4j
@RequestMapping("/admin")
@RestController
@Validated
public class AdminController {

    @Autowired
    private AdminDao adminDao;

    // log in user....with custom sign option.
    @PostMapping("/login")
    @Validated
    @ApiOperation(value = "Admin Login")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Login Successful", response = SignUpResponse.class),
            @ApiResponse(code = 404, message = "User Not Found", response = ChangeSetPersister.NotFoundException.class)
    })
    public ResponseEntity<SignUpResponse> authenticateUser(@Valid @RequestBody Authenticate authCredential) {
        return adminDao.authenticateUser(authCredential);

    }

    //finding all user details....
    @GetMapping("/getAllUsers/{pageNo}/{pageSize}")
    @ApiOperation(value = "Get All Users")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List of Users"),
            @ApiResponse(code = 404, message = "Not Found", response = ChangeSetPersister.NotFoundException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationFailedException.class)
    })
    public List<UserClass> getAllUsers(@NotNull @Min(value = 1L) @Max(value = 1000L) @PathVariable int pageNo, @NotNull @Min (value = 1L) @Max(value = 25L) @PathVariable int pageSize) {
        return adminDao.getAllUsers(pageNo, pageSize);
    }

    //finding all vendors from the database.
    @GetMapping("/getAllVendors/{pageNo}/{pageSize}")
    @ApiOperation(value = "Get All Vendors")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List of Vendors"),
            @ApiResponse(code = 404, message = "Not Found", response = ChangeSetPersister.NotFoundException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationFailedException.class)
    })
    public List<UserClass> getAllVendors(@NotNull @Min(value = 1L) @Max(value = 1000L) @PathVariable int pageNo, @NotNull @Min (value = 1L) @Max(value = 25L) @PathVariable int pageSize) {
        return adminDao.getAllVendors(pageNo, pageSize);
    }

    //finding all fitness centers in the list.
    @GetMapping("/getAllGyms/{pageNo}/{pageSize}")
    @ApiOperation(value = "Get All Gyms")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List of Gyms"),
            @ApiResponse(code = 404, message = "Not Found", response = ChangeSetPersister.NotFoundException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationFailedException.class)
    })
    public List<GymClass> getAllGyms(@NotNull @Min(value = 1L) @Max(value = 1000L) @PathVariable int pageNo, @NotNull @Min (value = 1L) @Max(value = 25L) @PathVariable int pageSize) {
        return adminDao.getAllGyms(pageNo, pageSize);
    }

    //finding list of registered fitness center by email id of vendor.
    @GetMapping("/getAllGymsByEmail/{email}")
    @ApiOperation(value = "Get All Gyms by Email")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List of Gyms by Email"),
            @ApiResponse(code = 404, message = "Not Found", response = ChangeSetPersister.NotFoundException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationFailedException.class)
    })
    public List<GymClass> getAllGymsByEmail(@NotNull @NotEmpty @NotBlank @Email @PathVariable String email) {
        return adminDao.getAllGymsByEmail(email);
    }


    //finding total amount to pay to the vendor.
    @GetMapping("/vendorPayment/{vendor}")
    @ApiOperation(value = "Get Payment of Vendors")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Vendor due payments"),
            @ApiResponse(code = 404, message = "Not Found", response = ChangeSetPersister.NotFoundException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationFailedException.class)
    })
    public AdminPay vendorPayment(@NotNull @NotEmpty @NotBlank @PathVariable String vendor) {
        return adminDao.vendorPayment(vendor);
    }

    //demo api for payment.
    @GetMapping("/getDataPay")
    @ApiOperation(value = "Get Data Pay")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Payment details of vendor"),
            @ApiResponse(code = 404, message = "Not Found", response = ChangeSetPersister.NotFoundException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationFailedException.class)
    })
    public AdminPay getDataPay(@RequestBody AdminPayModel pay) {

        return adminDao.getDataPay(pay);
    }

    //Initiating payment to the vendor
    @PutMapping("/payVendorNow")
    @ApiOperation(value = "Payment to Vendor")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Payment done successfully"),
            @ApiResponse(code = 404, message = "Not Found", response = ChangeSetPersister.NotFoundException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationFailedException.class)
    })
    @ResponseBody
    public String payNow(@Valid @RequestBody AdminPayModel payment) throws Exception {
        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_vmHcJh5Dj4v5EB", "SGff6EaJ7l3RzR47hnE4dYJz");

        JSONObject ob = new JSONObject();
        ob.put("amount", payment.getAmount() * 100);
        ob.put("currency", "INR");
        ob.put("receipt", "txn_201456");

        Order myOrder = razorpayClient.Orders.create(ob);

        boolean flag = adminDao.payNow(payment, myOrder);
        if (flag) {
            return myOrder.toString();
        } else {
            return null;
        }
    }

    //updating status of payment maid to the vendor by admin.
    @PutMapping("/updateVendorPayment")
    @ApiOperation(value = "Update the order")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Order Updated"),
            @ApiResponse(code = 404, message = "Not Found", response = ChangeSetPersister.NotFoundException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationFailedException.class)
    })
    public AdminPay updatingOrder(@RequestBody Map<String, String> data) {
        return adminDao.updatePayment(data);
    }

    //Finding payment history of the vendor.
    @GetMapping("/paidHistory/{vendor}")
    @ApiOperation(value = "Get Payment History of vendor")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List of Payment History of particular Vendor"),
            @ApiResponse(code = 404, message = "Not Found", response = ChangeSetPersister.NotFoundException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationFailedException.class)
    })
    public List<AdminPay> paidHistory(@NotNull @NotEmpty @NotBlank @PathVariable String vendor) {
        return adminDao.paidHistoryVendor(vendor);
    }

    //finding total registered vendors, fitness center and fitness enthusiast.
    @GetMapping("/allNumbers")
    @ApiOperation(value = "Get User, Vendor and Gyms")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List of total numbers of User, Vendor and Gyms"),
            @ApiResponse(code = 404, message = "Not Found", response = ChangeSetPersister.NotFoundException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationFailedException.class)
    })
    public ResponseEntity<List<String>> getAllNumber() {
        return adminDao.getAllNumber();
    }

}