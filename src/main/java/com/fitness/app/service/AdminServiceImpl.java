package com.fitness.app.service;

import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.config.JwtUtils;
import com.fitness.app.dto.AdminPayModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.AdminPayClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorPaymentClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.exceptions.UserNotFoundException;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.AdminPayRepository;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.repository.VendorPayRepository;
import com.fitness.app.security.service.UserDetailsSecServiceImpl;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    final private VendorPayRepository vendorPay;

    final private AddGymRepository gymRepo;

    final private AuthenticationManager authenticationManager;

    final private AdminPayRepository adminPayRepository;

    final private UserDetailsSecServiceImpl userDetailsService;
    final private JwtUtils jwtUtils;
    final private UserRepository userRepo;


    @Override
    public String authenticateAdmin(Authenticate authenticate) throws UserNotFoundException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticate.getEmail(), authenticate.getPassword()));
        } catch (BadCredentialsException e) {
            log.error("AdminController ::-> authenticateUer :: Error found due to : {}", e.getMessage());
            throw new UserNotFoundException(e.getMessage());
        }
        final UserDetails usrDetails = userDetailsService.loadUserByUsername(authenticate.getEmail());
        final String jwt = jwtUtils.generateToken(usrDetails);
        final UserClass localUser = userRepo.findByEmail(authenticate.getEmail());
        if (localUser.getRole().equals("ADMIN")) {
            return jwt;
        }
        return null;
    }

    @Override
    public List<UserClass> getAllUsersService() {
        List<UserClass> listOfUsers = userRepo.findAll();
        listOfUsers = listOfUsers.stream().filter(e -> e.getRole().equals("USER")).collect(Collectors.toList());
        if (listOfUsers != null && listOfUsers.size() > 0) {
            return listOfUsers;
        }
        log.info("AdminServiceImpl ::-> getAllUser :: No User found.");
        return listOfUsers;
    }

    @Override
    public List<UserClass> getAllVendorService(){
        List<UserClass> listOfVendors = userRepo.findAll();
        listOfVendors = listOfVendors.stream().filter(e -> e.getRole().equals("VENDOR")).collect(Collectors.toList());
        return listOfVendors;
    }

    @Override
    public List<GymClass> getAllFitnessCenter()
    {
        return gymRepo.findAll();
    }


    @Override
    public List<GymClass> getAllGymsByEmail(@PathVariable String email) {
        return gymRepo.findByEmail(email);
    }
    @Override
    public AdminPayClass getDataPay(AdminPayModel payment) {
        log.info("Payment to admin is being accessed");
        return adminPayRepository.findByVendorAndAmountAndStatus(payment.getVendor(), payment.getAmount(), "Due");

    }

    @Override
    public ApiResponse PayNow(AdminPayModel payment) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_vmHcJh5Dj4v5EB", "SGff6EaJ7l3RzR47hnE4dYJz");
        JSONObject ob = new JSONObject();
        ob.put("amount", payment.getAmount() * 100);
        ob.put("currency", "INR");
        ob.put("receipt", "txn_201456");

        Order myOrder = razorpayClient.Orders.create(ob);
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        AdminPayClass payVendor = adminPayRepository.findByVendorAndAmountAndStatus(payment.getVendor(), payment.getAmount(), "Due");
        if (payVendor == null) {
            return new ApiResponse(HttpStatus.NO_CONTENT, null);
        }
        payVendor.setOrderId(myOrder.get("id"));
        payVendor.setStatus(myOrder.get("status"));
        payVendor.setPaymentId(null);
        payVendor.setReciept(myOrder.get("receipt"));
        payVendor.setDate(date);
        payVendor.setTime(time);
        adminPayRepository.save(payVendor);
        return new ApiResponse(HttpStatus.OK, myOrder.toString());
    }

    @Override
    public AdminPayClass vendorPayment(String vendor) {
        List<VendorPaymentClass> payments = vendorPay.findByVendor(vendor);
        payments = payments.stream().filter(p -> p.getStatus().equals("Due")).collect(Collectors.toList());
        AdminPayClass payment = new AdminPayClass();
        payment.setVendor(vendor);
        payment.setStatus("Due");
        int amount = 0;
        if (payments != null) {
            for (VendorPaymentClass pay : payments) {
                amount += pay.getAmount();
            }
        }
        payment.setAmount(amount);
        int s = adminPayRepository.findAll().size();
        String id = "P0" + s;
        payment.setId(id);
        AdminPayClass oldPay = adminPayRepository.findByVendorAndAmountAndStatus(vendor, amount, "Due");
        if (oldPay != null) {
            return oldPay;
        }
        adminPayRepository.save(payment);
        return payment;
    }

    @Override
    public AdminPayClass updatePayment(Map<String, String> data) {

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        AdminPayClass payment = adminPayRepository.findByOrderId(data.get("order_id"));
        payment.setPaymentId(data.get("payment_id"));
        payment.setStatus(data.get("status"));
        payment.setDate(date);
        payment.setTime(time);
        List<VendorPaymentClass> vendorPaids = vendorPay.findByVendorAndStatus(payment.getVendor(), "Due");
        for (VendorPaymentClass pays : vendorPaids) {
            pays.setStatus("Paid");
            vendorPay.save(pays);
        }
        adminPayRepository.save(payment);
        return payment;
    }

    @Override
    public List<AdminPayClass> paidHistoryVendor(String vendor) throws DataNotFoundException {
        try {
            List<AdminPayClass> allPaid = adminPayRepository.findByVendor(vendor);
            if (allPaid == null) {
                log.info("No payment is there: ");
                throw new DataNotFoundException("No Payment Is there:");
            }
            allPaid = allPaid.stream().filter(p -> p.getStatus().equals("Completed")).collect(Collectors.toList());
            return allPaid;
        } catch (DataNotFoundException e) {
            log.error("AdminServiceImpl ::-> paidHistoryVendor ::  Either no vendor or no data for this vendor: ");
            throw new DataNotFoundException("No data Found with this vendor or No such vendor is there:");
        }
    }

    public List<String>  getAllNumber()
    {
        List<UserClass> listOfAllUser = userRepo.findAll();
        int user = listOfAllUser.stream().filter(e -> e.getRole().equals("USER")).collect(Collectors.toList()).size();
        int vendor = listOfAllUser.stream().filter(e -> e.getRole().equals("VENDOR")).collect(Collectors.toList()).size();
        List<GymClass> gyms = gymRepo.findAll();
        int fitnessCenter = gyms.size();
        List<String> response = new ArrayList<>();
        response.add(Integer.toString(user));
        response.add(Integer.toString(vendor));
        response.add(Integer.toString(fitnessCenter));

        return response;
    }

}
