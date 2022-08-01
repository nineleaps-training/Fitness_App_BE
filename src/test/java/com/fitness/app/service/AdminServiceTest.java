package com.fitness.app.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.config.JwtUtils;
import com.fitness.app.entity.AdminPay;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorPayment;
import com.fitness.app.model.AdminPayRequestModel;
import com.fitness.app.model.SignUpResponseModel;
import com.fitness.app.repository.AddGymRepo;
import com.fitness.app.repository.AdminPayRepo;
import com.fitness.app.repository.UserRepo;
import com.fitness.app.repository.VendorPayRepo;
import com.fitness.app.security.service.UserDetailsServiceImpl;
import com.razorpay.Order;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

        @Mock
        AdminPayRepo adminPayRepo;

        @Mock
        VendorPayRepo vendorPayRepo;

        @Mock
        UserRepo userRepo;

        @Mock
        UserDetailsServiceImpl userDetailsServiceImpl;

        @Mock
        JwtUtils jwtUtils;

        @Mock
        AddGymRepo gymRepo;

        List<String> aList = new ArrayList<>(List.of("Hello"));

        long l = 123;

        @Mock
        AuthenticationManager authenticationManager;

        @InjectMocks
        AdminService adminService;

        List<GymClass> gymClasses = new ArrayList<>();
        List<UserClass> userClasses = new ArrayList<>();

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Hello@123",
                        "USER",
                        true, true, true);

        UserClass userClass1 = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Hello@123",
                        "VENDOR", true, true, true);

        GymClass gymClass = new GymClass("GM6", "pankaj.jain@nineleaps.com", "Pankaj Jain", aList, l, 2.3, 15);

        @Test
        @DisplayName("Testing to fetch the details of the order")
        void testGetDataPay() {

                LocalDate localDate = LocalDate.of(2022, 6, 24);
                LocalTime localTime = LocalTime.of(12, 32, 4);
                AdminPayRequestModel adminPayRequestModel = new AdminPayRequestModel("GM6", "orderId", "vendor", 123,
                                "Due",
                                "string", "string", localDate, localTime);
                AdminPay adminPay = new AdminPay("GM6", "orderId", "vendor", 123, "Due", "string", "string", localTime,
                                localDate);
                when(adminPayRepo.findByVendorAndAmountAndStatus(adminPayRequestModel.getVendor(),
                                adminPayRequestModel.getAmount(), "Due")).thenReturn(adminPay);
                AdminPay adminPayRequestModel2 = adminService.getDataPay(adminPayRequestModel);
                Assertions.assertEquals(adminPay.getVendor(), adminPayRequestModel2.getVendor());

        }

        @Test
        void testLoginAdmin() {
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ADMIN"));
                org.springframework.security.core.userdetails.UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                                "pankaj.jain@nineleaps.com", "Pankaj@123", authorities);
                UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322",
                                "Pankaj@123", "ADMIN",
                                true, true, true);
                Authenticate authenticate = new Authenticate("pankaj.jain@nineleaps.com", "Pankaj@123");
                Mockito.when(userRepo.findByEmail("pankaj.jain@nineleaps.com")).thenReturn(userClass);
                Mockito.when(userDetailsServiceImpl.loadUserByUsername(authenticate.getEmail()))
                                .thenReturn(userDetails);
                ResponseEntity<SignUpResponseModel> responseEntity = ResponseEntity
                                .ok(new SignUpResponseModel(userClass, null));
                ResponseEntity<SignUpResponseModel> responseEntity2 = adminService.loginAdmin(authenticate);
                Assertions.assertEquals(responseEntity, responseEntity2);

        }

        @Test
        void testLoginAdminElse() {
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ADMIN"));
                org.springframework.security.core.userdetails.UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                                "pankaj.jain@nineleaps.com", "Pankaj@123", authorities);
                UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322",
                                "Pankaj@123", "USER",
                                true, true, true);
                Authenticate authenticate = new Authenticate("pankaj.jain@nineleaps.com", "Pankaj@123");
                Mockito.when(userRepo.findByEmail("pankaj.jain@nineleaps.com")).thenReturn(userClass);
                Mockito.when(userDetailsServiceImpl.loadUserByUsername(authenticate.getEmail()))
                                .thenReturn(userDetails);
                ResponseEntity<SignUpResponseModel> responseEntity = ResponseEntity
                                .ok(new SignUpResponseModel(null, null));
                ResponseEntity<SignUpResponseModel> responseEntity2 = adminService.loginAdmin(authenticate);
                Assertions.assertEquals(responseEntity, responseEntity2);

        }

        @Test
        @DisplayName("Testing to fetch the payment history of vendor")
        void testPaidHistoryVendor() {

                LocalDate localDate = LocalDate.of(2022, 6, 24);
                LocalTime localTime = LocalTime.of(12, 32, 4);
                AdminPayRequestModel adminPayRequestModel = new AdminPayRequestModel("GM6", "orderId", "vendor", 123,
                                "Completed", "string", "string", localDate, localTime);
                AdminPay adminPay = new AdminPay("GM6", "orderId", "vendor", 123, "Completed", "string", "string",
                                localTime,
                                localDate);
                List<AdminPayRequestModel> list = new ArrayList<>();
                list.add(adminPayRequestModel);
                List<AdminPay> list2 = new ArrayList<>();
                list2.add(adminPay);
                when(adminPayRepo.findByVendor(adminPayRequestModel.getVendor())).thenReturn(list2);
                List<AdminPay> list3 = adminService.paidHistoryVendor(adminPayRequestModel.getVendor());
                Assertions.assertEquals(list2, list3);
        }

        @Test
        @DisplayName("Testing for payment to vendor")
        void testPayNow() {

                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                AdminPayRequestModel adminPayRequestModel = new AdminPayRequestModel("GM6", "orderId", "vendor", 123,
                                "Due",
                                "string", "string", localDate, localTime);
                AdminPay adminPay = new AdminPay("GM6", "orderId", "vendor", 123, "Due", "string", "string", localTime,
                                localDate);
                List<AdminPayRequestModel> list = new ArrayList<>();
                list.add(adminPayRequestModel);
                List<AdminPay> list2 = new ArrayList<>();
                list2.add(adminPay);
                JSONObject jsonObject = new JSONObject();
                Order order = new Order(jsonObject);
                when(adminPayRepo.findByVendorAndAmountAndStatus(adminPayRequestModel.getVendor(),
                                adminPayRequestModel.getAmount(), adminPayRequestModel.getStatus()))
                                .thenReturn(adminPay);
                AdminPay adminPayRequestModel2 = adminService.payNow(adminPayRequestModel, order);
                Assertions.assertEquals(adminPay, adminPayRequestModel2);

        }

        @Test
        void testGetAllNumber() {
                List<String> list = new ArrayList<>();
                list.add("4");
                list.add("1");
                list.add("5");

                userClasses.add(userClass);
                userClasses.add(userClass1);
                gymClasses.add(gymClass);
                Mockito.when(userRepo.findAll()).thenReturn(userClasses);
                Mockito.when(gymRepo.findAll()).thenReturn(gymClasses);
                ResponseEntity<Object> responseEntity = adminService.getAllNumber();
                List<String> lStrings = new ArrayList<>();
                lStrings.add(Integer.toString(1));
                lStrings.add(Integer.toString(1));
                lStrings.add(Integer.toString(1));
                ResponseEntity<Object> responseEntity2 = new ResponseEntity<>(lStrings, HttpStatus.OK);
                Assertions.assertEquals(responseEntity, responseEntity2);
        }

        @Test
        void testGetAllGymsByEmail() {

                gymClasses.add(gymClass);
                Mockito.when(gymRepo.findByEmail("pankaj.jain@nineleaps.com")).thenReturn(gymClasses);
                List<GymClass> gClasses = adminService.getAllGymsByEmail("pankaj.jain@nineleaps.com");
                Assertions.assertEquals(gymClasses, gClasses);
        }

        @Test
        @DisplayName("Testing of payment exception")
        void testPayNowNull() {

                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                AdminPayRequestModel adminPayRequestModel = new AdminPayRequestModel("GM6", "orderId", "vendor", 123,
                                "Due",
                                "string", "string", localDate, localTime);
                List<AdminPayRequestModel> list = new ArrayList<>();
                list.add(adminPayRequestModel);
                JSONObject jsonObject = new JSONObject();
                Order order = new Order(jsonObject);
                when(adminPayRepo.findByVendorAndAmountAndStatus(adminPayRequestModel.getVendor(),
                                adminPayRequestModel.getAmount(), adminPayRequestModel.getStatus())).thenReturn(null);
                AdminPay adminPayRequestModel2 = adminService.payNow(adminPayRequestModel, order);
                Assertions.assertEquals(null, adminPayRequestModel2);

        }

        @Test
        @DisplayName("Testing of updating the order details")
        void testUpdatePayment() {

                Map<String, String> data = new HashMap<>();
                data.put("order_id", "id");
                data.put("payment_id", "paymentId");
                data.put("status", "Due");
                VendorPayment vendorPayment = new VendorPayment("email", "vendor", 123, "Due");
                List<VendorPayment> list = new ArrayList<>();
                list.add(vendorPayment);
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                AdminPayRequestModel adminPayRequestModel = new AdminPayRequestModel("GM6", "id", "vendor", 123, "Due",
                                "string", "string", localDate, localTime);
                AdminPay adminPay = new AdminPay("GM6", "id", "vendor", 123, "Due", "string", "string", localTime,
                                localDate);
                List<AdminPayRequestModel> list1 = new ArrayList<>();
                list1.add(adminPayRequestModel);
                when(adminPayRepo.findByOrderId(data.get("order_id"))).thenReturn(adminPay);
                when(vendorPayRepo.findByVendorAndStatus(adminPayRequestModel.getVendor(),
                                adminPayRequestModel.getStatus()))
                                .thenReturn(list);
                AdminPay adminPayRequestModel2 = adminService.updatePayment(data);
                Assertions.assertEquals(adminPay, adminPayRequestModel2);

        }

        @Test
        @DisplayName("Testing of Vendor Payment")
        void testVendorPayment() {

                Map<String, String> data = new HashMap<>();
                data.put("order_id", "id");
                data.put("payment_id", "paymentId");
                data.put("status", "Due");
                VendorPayment vendorPayment = new VendorPayment("email", "vendor", 123, "Due");
                List<VendorPayment> list = new ArrayList<>();
                list.add(vendorPayment);
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                AdminPayRequestModel adminPayRequestModel = new AdminPayRequestModel("GM6", "id", "vendor", 123, "Due",
                                "string", "string", localDate, localTime);
                List<AdminPayRequestModel> list1 = new ArrayList<>();
                list1.add(adminPayRequestModel);
                AdminPay adminPay = new AdminPay("GM6", "id", "vendor", 123, "Due", "string", "string", localTime,
                                localDate);
                List<AdminPay> list2 = new ArrayList<>();
                list2.add(adminPay);
                when(vendorPayRepo.findByVendor(vendorPayment.getVendor())).thenReturn(list);
                when(adminPayRepo.findByVendorAndAmountAndStatus(adminPayRequestModel.getVendor(),
                                adminPayRequestModel.getAmount(), "Due")).thenReturn(adminPay);
                when(adminPayRepo.findAll()).thenReturn(list2);
                AdminPay adminPayRequestModel2 = adminService.vendorPayment(adminPayRequestModel.getVendor());
                Assertions.assertEquals(adminPay, adminPayRequestModel2);
        }

        @Test
        @DisplayName("Testing of vendor payment when Optional is empty")
        void testVendorPaymentEmpty() {

                VendorPayment vendorPayment = new VendorPayment();
                AdminPayRequestModel adminPayRequestModel = new AdminPayRequestModel();
                List<AdminPayRequestModel> list1 = new ArrayList<>();
                list1.add(adminPayRequestModel);
                AdminPay adminPay = new AdminPay();
                List<AdminPay> list2 = new ArrayList<>();
                list2.add(adminPay);
                when(vendorPayRepo.findByVendor(vendorPayment.getVendor())).thenReturn(Collections.emptyList());
                when(adminPayRepo.findByVendorAndAmountAndStatus(null, 0, "Due")).thenReturn(null);
                when(adminPayRepo.findAll()).thenReturn(list2);
                when(adminPayRepo.save(any())).thenReturn(adminPay);
                AdminPay adminPayRequestModel2 = adminService.vendorPayment(vendorPayment.getVendor());
                Assertions.assertEquals(adminPay.getVendor(), adminPayRequestModel2.getVendor());
        }

        @Test
        @DisplayName("Testing of vendor payment when filter is empty")
        void testVendorPaymentFilterEmpty() {

                VendorPayment vendorPayment = new VendorPayment("email", "vendor", 123, "Completed");
                List<VendorPayment> list = new ArrayList<>();
                list.add(vendorPayment);
                AdminPayRequestModel adminPayRequestModel = new AdminPayRequestModel();
                List<AdminPayRequestModel> list1 = new ArrayList<>();
                list1.add(adminPayRequestModel);
                AdminPay adminPay = new AdminPay();
                List<AdminPay> list2 = new ArrayList<>();
                list2.add(adminPay);
                when(vendorPayRepo.findByVendor(vendorPayment.getVendor())).thenReturn(list);
                when(adminPayRepo.findByVendorAndAmountAndStatus("vendor", 0, "Due")).thenReturn(null);
                when(adminPayRepo.findAll()).thenReturn(list2);
                when(adminPayRepo.save(any())).thenReturn(adminPay);
                AdminPay adminPayRequestModel2 = adminService.vendorPayment(vendorPayment.getVendor());
                Assertions.assertEquals("vendor", adminPayRequestModel2.getVendor());
        }
}
