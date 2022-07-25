package com.fitness.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.config.JwtUtils;
import com.fitness.app.dao.AdminDao;
import com.fitness.app.entity.*;
import com.fitness.app.model.AdminPayModel;
import com.fitness.app.model.SignUpResponse;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.security.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import com.fitness.app.repository.AdminPayRepository;
import com.fitness.app.repository.VendorPayRepository;
import com.razorpay.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class AdminService implements AdminDao {

    private VendorPayRepository vendorPay;
    private AdminPayRepository adminPayRepository;
    private AddGymRepository gymRepo;
    private UserRepository userRepo;
    private AuthenticationManager authenticationManager;
    private UserDetailsServiceImpl userDetailsService;
    private JwtUtils jwtUtils;

    @Autowired
    public AdminService(VendorPayRepository vendorPay, AdminPayRepository adminPayRepository, AddGymRepository gymRepo, UserRepository userRepo, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtils jwtUtils) {
        this.vendorPay = vendorPay;
        this.adminPayRepository = adminPayRepository;
        this.gymRepo = gymRepo;
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<SignUpResponse> authenticateUser(Authenticate authCredential) {
        log.info("AdminService >> authenticateUser >> Initiated");
        authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authCredential.getEmail(), authCredential.getPassword()));

        final UserDetails usrDetails = userDetailsService.loadUserByUsername(authCredential.getEmail());
        final String jwt = jwtUtils.generateToken(usrDetails);
        final UserClass localUser = userRepo.findByEmail(authCredential.getEmail());
        if (localUser.getRole().equals("ADMIN")) {
            log.info("AdminService >> authenticateUser >> Role is ADMIN");
            return ResponseEntity.ok(new SignUpResponse(localUser, jwt));
        } else {
            log.info("AdminService >> authenticateUser >> Returns null");
            return ResponseEntity.ok(new SignUpResponse(null, null));
        }
    }

    public List<UserClass> getAllUsers(int pageNo, int pageSize) {
        log.info("AdminService >> getAllUsers >> Initiated");
        List<UserClass> userList = userRepo.findAll();
        userList = userList.stream().filter(e -> e.getRole().equals("USER")).collect(Collectors.toList());
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UserClass> page = new PageImpl<>(userList, pageable, userList.size());
        return page.getContent();
    }

    public List<UserClass> getAllVendors(int pageNo, int pageSize) {
        log.info("AdminService >> getAllVendors >> Initiated");
        List<UserClass> vendorList = userRepo.findAll();
        vendorList = vendorList.stream().filter(e -> e.getRole().equals("VENDOR")).collect(Collectors.toList());
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UserClass> page = new PageImpl<>(vendorList, pageable, vendorList.size());
        return page.getContent();
    }

    public List<GymClass> getAllGyms(int pageNo, int pageSize) {
        log.info("AdminService >> getAllGyms >> Initiated");
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<GymClass> page = gymRepo.findAll(pageable);
        return page.getContent();
    }

    public List<GymClass> getAllGymsByEmail(String email) {
        log.info("AdminService >> getAllGymsByEmail >> Initiated");
        return gymRepo.findByEmail(email);
    }

    public AdminPay getDataPay(AdminPayModel payment) {
        log.info("AdminService >> getDataPay >> Initiated");
        return adminPayRepository.findByVendorAndAmountAndStatus(payment.getVendor(), payment.getAmount(), "Due");

    }

    public boolean payNow(AdminPayModel payment, Order myOrder) {
        log.info("AdminService >> payNow >> Initiated");
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        AdminPay payVendor = adminPayRepository.findByVendorAndAmountAndStatus(payment.getVendor(), payment.getAmount(), "Due");

        if (payVendor == null) {
            log.warn("AdminService >> payNow >> AdminPay is null");
            return false;
        }
        payVendor.setOrderId(myOrder.get("id"));
        payVendor.setStatus(myOrder.get("status"));
        payVendor.setPaymentId(null);
        payVendor.setReciept(myOrder.get("receipt"));
        payVendor.setDate(date);
        payVendor.setTime(time);

        adminPayRepository.save(payVendor);
        log.info("AdminService >> payNow >> Ends");
        return true;
    }


    public AdminPay vendorPayment(String vendor) {
        log.info("AdminService >> vendorPayment >> Initiated");
        List<VendorPayment> payments = vendorPay.findByVendor(vendor);

        payments = payments.stream().filter(p -> p.getStatus().equals("Due")).collect(Collectors.toList());

        AdminPay payment = new AdminPay();

        payment.setVendor(vendor);
        payment.setStatus("Due");
        int amount = 0;
        for (VendorPayment pay : payments) {

            amount += pay.getAmount();

        }

        payment.setAmount(amount);
        int s = adminPayRepository.findAll().size();
        String id = "P0" + s;
        payment.setId(id);
        AdminPay oldPay = adminPayRepository.findByVendorAndAmountAndStatus(vendor, amount, "Due");
        if (oldPay != null) {
            return oldPay;
        }
        adminPayRepository.save(payment);
        log.info("AdminService >> vendorPayment >> Ends");
        return payment;
    }


    public AdminPay updatePayment(Map<String, String> data) {
        log.info("AdminService >> updatePayment >> Initiated");
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        AdminPay payment = adminPayRepository.findByOrderId(data.get("order_id"));

        payment.setPaymentId(data.get("payment_id"));
        payment.setStatus(data.get("status"));
        payment.setDate(date);
        payment.setTime(time);

        List<VendorPayment> vendorPaid = vendorPay.findByVendorAndStatus(payment.getVendor(), "Due");

        for (VendorPayment pays : vendorPaid) {
            pays.setStatus("Paid");
            vendorPay.save(pays);
        }

        adminPayRepository.save(payment);
        log.info("AdminService >> updatePayment >> Ends");
        return payment;
    }


    public List<AdminPay> paidHistoryVendor(String vendor) {
        log.info("AdminService >> paidHistoryVendor >> Initiated");

        List<AdminPay> allPaid = adminPayRepository.findByVendor(vendor);
        return allPaid.stream().filter(p -> p.getStatus().equals("Completed")).collect(Collectors.toList());

    }

    public ResponseEntity<List<String>> getAllNumber() {
        log.info("AdminService >> getAllNumber >> Initiated");
        List<UserClass> l = userRepo.findAll();

        int u = l.stream().filter(e -> e.getRole().equals("USER")).collect(Collectors.toList()).size();
        int v = l.stream().filter(e -> e.getRole().equals("VENDOR")).collect(Collectors.toList()).size();
        List<GymClass> gyms = gymRepo.findAll();
        int g = gyms.size();

        List<String> nums = new ArrayList<>();
        nums.add(Integer.toString(u));
        nums.add(Integer.toString(v));
        nums.add(Integer.toString(g));

        log.info("AdminService >> getAllNumber >> Ends");
        return new ResponseEntity<>(nums, HttpStatus.OK);
    }

}
