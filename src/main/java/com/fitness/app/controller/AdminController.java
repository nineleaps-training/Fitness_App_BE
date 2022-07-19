package com.fitness.app.controller;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.config.JwtUtils;
import com.fitness.app.entity.AdminPayClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.exceptions.UserNotFoundException;
import com.fitness.app.model.AdminPayModel;
import com.fitness.app.model.SignUpResponce;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.security.service.UserDetailsServiceImpl;
<<<<<<< HEAD
=======
import com.fitness.app.service.AdminServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class AdminController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AddGymRepository gymRepo;

    @Autowired
<<<<<<< HEAD
    private AdminServiceImplementation adminServiceImplementation;
=======
    private AdminServiceImpl adminServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48

    // log in user....with custom sign option.
    @PostMapping("/login/admin")
    public ResponseEntity<SignUpResponce> authenticateUser(@RequestBody Authenticate authCredential) throws UserNotFoundException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authCredential.getEmail(), authCredential.getPassword()));
        } catch (Exception e) {
            log.info("Exception found: {}", e.getMessage());

            throw new UserNotFoundException(e.getMessage());

        }
        final UserDetails usrDetails = userDetailsService.loadUserByUsername(authCredential.getEmail());
        final String jwt = jwtUtils.generateToken(usrDetails);
        final UserClass localUser = userRepo.findByEmail(authCredential.getEmail());
        if (localUser.getRole().equals("ADMIN")) {
            return ResponseEntity.ok(new SignUpResponce(localUser, jwt));
        } else {
            return ResponseEntity.ok(new SignUpResponce(null, null));
        }

    }


    //finding all user details....
    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserClass>> getAllUsers() {
        List<UserClass> l = userRepo.findAll();
        l = l.stream().filter(e -> e.getRole().equals("USER")).collect(Collectors.toList());
        if (l != null && l.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(l);
        }
        System.out.println(HttpStatus.NO_CONTENT);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }


    //finding all vendors from the database.
    @GetMapping("/get-all-vendors")
    public List<UserClass> getAllVendors() {
        List<UserClass> l = userRepo.findAll();
        l = l.stream().filter(e -> e.getRole().equals("VENDOR")).collect(Collectors.toList());
        return l;
    }


    //finding all fitness centers in the list.
    @GetMapping("/get-all-gyms")
    public List<GymClass> getAllGyms() {
        return gymRepo.findAll();

    }


    //finding list of registered fitness center by email id of vendor.
    @GetMapping("/get-all-gyms-by-email/{email}")
    public List<GymClass> getAllGymsByEmail(@PathVariable String email) {
        return gymRepo.findByEmail(email);
    }


    //finding total amount to pay to the vendor.
    @GetMapping("/vendor-payment/{vendor}")
    public AdminPayClass vendorPayment(@PathVariable String vendor) {
<<<<<<< HEAD
        return adminServiceImplementation.vendorPayment(vendor);
=======
        return adminServiceImpl.vendorPayment(vendor);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
    }

    //demo api for payment.
    @GetMapping("/get-data-pay")
    public AdminPayClass getDatapay(@RequestBody AdminPayModel pay) {
<<<<<<< HEAD
        return adminServiceImplementation.getDataPay(pay);
=======
        return adminServiceImpl.getDataPay(pay);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
    }


    //Initiating payment to the vendor
    @PutMapping("/pay-vendor-now")
    @ResponseBody
    public String payNow(@RequestBody AdminPayModel payment) throws Exception {
        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_vmHcJh5Dj4v5EB", "SGff6EaJ7l3RzR47hnE4dYJz");

        JSONObject ob = new JSONObject();
        ob.put("amount", payment.getAmount() * 100);
        ob.put("currency", "INR");
        ob.put("receipt", "txn_201456");

        Order myOrder = razorpayClient.Orders.create(ob);

<<<<<<< HEAD
        boolean flag = adminServiceImplementation.PayNow(payment, myOrder);
=======
        boolean flag = adminServiceImpl.PayNow(payment, myOrder);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
        if (flag) {
            return myOrder.toString();
        } else {
            return null;
        }
    }


    //updating status of payment maid to the vendor by admin.
    @PutMapping("/update-vendor-payment")
    public AdminPayClass updatingOrder(@RequestBody Map<String, String> data) {

<<<<<<< HEAD
        return adminServiceImplementation.updatePayment(data);
=======
        return adminServiceImpl.updatePayment(data);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
    }


    //Finding payment history of the vendor.
    @GetMapping("/paid-history/{vendor}")
    public List<AdminPayClass> paidHistroy(@PathVariable String vendor) throws DataNotFoundException {
<<<<<<< HEAD
        return adminServiceImplementation.paidHistoryVendor(vendor);
=======
        return adminServiceImpl.paidHistoryVendor(vendor);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
    }


    //finding total registered vendors, fitness center and fitness enthusiast.
    @GetMapping("/all-numbers")
    public ResponseEntity<List<String>> getAllNumber() {
        List<UserClass> l = userRepo.findAll();
        int u = l.stream().filter(e -> e.getRole().equals("USER")).collect(Collectors.toList()).size();
        int v = l.stream().filter(e -> e.getRole().equals("VENDOR")).collect(Collectors.toList()).size();
        List<GymClass> gyms = gymRepo.findAll();
        int g = gyms.size();

        List<String> nums = new ArrayList<>();
        nums.add(Integer.toString(u));
        nums.add(Integer.toString(v));
        nums.add(Integer.toString(g));

        return new ResponseEntity<List<String>>(nums, HttpStatus.OK);
    }


}