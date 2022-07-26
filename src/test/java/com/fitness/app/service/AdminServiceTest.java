package com.fitness.app.service;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.config.JwtUtils;
import com.fitness.app.entity.AdminPay;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorPayment;
import com.fitness.app.model.AdminPayModel;
import com.fitness.app.model.SignUpResponse;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.AdminPayRepository;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.repository.VendorPayRepository;
import com.fitness.app.security.service.UserDetailsServiceImpl;
import com.razorpay.Order;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdminServiceTest {

    Order order;
    HashMap<String, String> data;

    Authenticate authenticate;
    List<String> workout = new ArrayList<>();


    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    UserDetails userDetails;

    @MockBean
    private JwtUtils jwtUtils;

    @Mock
    UserRepository userRepo;

    @Mock
    AdminPayRepository adminPayRepository;

    @Mock
    VendorPayRepository vendorPay;

    @Mock
    AddGymRepository gymRepo;

    @InjectMocks
    AdminService adminService;

    @BeforeEach
    public void setUp() {

        JSONObject jsonObject = new JSONObject();
        order = new Order(jsonObject.put("abcd", 1234));

        data = new HashMap<>();
        data.put("order_id", "12345");
        data.put("payment_id", "54321");
        data.put("status", "Completed");

        authenticate = new Authenticate("priyanshi.chaturvedi@nineleaps.com", "12345");

    }

    @Test
    void authenticateUser() {

        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "ADMIN", false, false, true);
        when(authenticationManager.authenticate(null)).thenReturn(null);
        when(userDetailsService.loadUserByUsername(authenticate.getEmail())).thenReturn(userDetails);
        when(userRepo.findByEmail(authenticate.getEmail())).thenReturn(userClass);

        ResponseEntity<SignUpResponse> responseEntity = ResponseEntity.ok(new SignUpResponse(userClass, null));
        ResponseEntity<SignUpResponse> actual = adminService.authenticateUser(authenticate);

        assertEquals(responseEntity, actual);
    }

    @Test
    void authenticateUserIfRoleIsNotAdmin() throws Exception {
        Authentication authentication = null;
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", false, false, true);

        when(authenticationManager.authenticate(authentication)).thenReturn(authentication);
        when(userDetailsService.loadUserByUsername(authenticate.getEmail())).thenReturn(userDetails);
        when(jwtUtils.generateToken(userDetails)).thenReturn("");
        when(userRepo.findByEmail(authenticate.getEmail())).thenReturn(userClass);

        assertNotNull(adminService.authenticateUser(authenticate));

    }

    @Test
    void getAllUsers() {
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "USER", true, true, false);
        List<UserClass> userClasses = new ArrayList<>();
        userClasses.add(userClass);
        when(userRepo.findAll()).thenReturn(userClasses);

        List<UserClass> actual = adminService.getAllUsers(0, 1);
        assertEquals(userClasses, actual);
    }

    @Test
    void getAllVendors() {
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "VENDOR", true, true, false);
        List<UserClass> userClasses = new ArrayList<>();
        userClasses.add(userClass);
        when(userRepo.findAll()).thenReturn(userClasses);

        List<UserClass> actual = adminService.getAllVendors(0, 1);
        assertEquals(userClasses, actual);
    }

    @Test
    void getAllGyms() {
        GymClass gymClass = new GymClass("GM1", "priyanshi.chaturvedi@nineleaps.com", "Fitness",
                workout, 9685903290L, 4.2, 100);
        List<GymClass> gymClasses = new ArrayList<>();
        gymClasses.add(gymClass);
        Pageable pageable = PageRequest.of(0, 1);
        Page<GymClass> page = new PageImpl<>(gymClasses);
        when(gymRepo.findAll(pageable)).thenReturn(page);
        List<GymClass> actual = adminService.getAllGyms(0, 1);
        assertEquals(gymClasses, actual);

    }

    @Test
    void getAllGymsByEmail() {
        GymClass gymClass = new GymClass("GM1", "priyanshi.chaturvedi@nineleaps.com", "Fitness",
                workout, 9685903290L, 4.2, 100);
        List<GymClass> gymClasses = new ArrayList<>();
        gymClasses.add(gymClass);

        when(gymRepo.findByEmail(gymClass.getEmail())).thenReturn(gymClasses);
        List<GymClass> actual = adminService.getAllGymsByEmail(gymClass.getEmail());
        assertEquals(gymClasses, actual);

    }

    @Test
    void getDataPay() {
        AdminPayModel adminPayModel = new AdminPayModel("1", "123456", "Priyanshi", 100,
                "Completed", "54321", "XYZ", LocalDate.now(), LocalTime.now());

        AdminPay adminPay = new AdminPay("1", "123456", "Priyanshi", 100,
                "Completed", "54321", "XYZ", LocalDate.now(), LocalTime.now());

        when(adminPayRepository.findByVendorAndAmountAndStatus(adminPayModel.getVendor(), adminPayModel.getAmount(), "Due")).thenReturn(adminPay);

        AdminPay dataPay = adminService.getDataPay(adminPayModel);
        assertEquals(adminPay, dataPay);

    }

    @Test
    void payNow() {
        AdminPayModel adminPayModel = new AdminPayModel("1", "123456", "Priyanshi", 100,
                "Completed", "54321", "XYZ", LocalDate.now(), LocalTime.now());

        AdminPay adminPay = new AdminPay("1", "123456", "Priyanshi", 100,
                "Completed", "54321", "XYZ", LocalDate.now(), LocalTime.now());

        when(adminPayRepository.findByVendorAndAmountAndStatus(adminPayModel.getVendor(), adminPayModel.getAmount(), "Due")).thenReturn(adminPay);

        boolean condition = adminService.payNow(adminPayModel, order);
        assertTrue(condition);
    }

    @Test
    void returnFalseIfAdminPayModelIsNull() {
        AdminPayModel adminPayModel = new AdminPayModel();

        boolean condition = adminService.payNow(adminPayModel, order);
        assertFalse(condition);
    }

    @Test
    void vendorPayment() {

        AdminPay adminPay = new AdminPay("P00", "123456", "Priyanshi", 100,
                "Due", "54321", "XYZ", LocalDate.now(), LocalTime.now());

        VendorPayment vendorPayment = new VendorPayment("Priyanshi", "Geet", "Fitness",
                0, "Due", LocalDate.now(), LocalTime.now());

        List<VendorPayment> vendorPayments = new ArrayList<>();
        vendorPayments.add(vendorPayment);

        when(vendorPay.findByVendor(adminPay.getVendor())).thenReturn(vendorPayments);
        when(adminPayRepository.findByVendorAndAmountAndStatus(adminPay.getVendor(), adminPay.getAmount(), "Due")).thenReturn(adminPay);

        String actual = adminService.vendorPayment(adminPay.getVendor()).getVendor();
        assertEquals(adminPay.getVendor(), actual);
    }

    @Test
    void returnAdminPayModelIfAdminPayModelIsNull() {
        AdminPay adminPay = new AdminPay();
        VendorPayment vendorPayment = new VendorPayment("Priyanshi", "Geet", "Fitness", 0,
                "Due", LocalDate.now(), LocalTime.now());
        List<VendorPayment> vendorPayments = new ArrayList<>();
        vendorPayments.add(vendorPayment);

        when(vendorPay.findByVendor(adminPay.getVendor())).thenReturn(vendorPayments);
        when(adminPayRepository.findByVendorAndAmountAndStatus(adminPay.getVendor(), adminPay.getAmount(), "Due")).thenReturn(adminPay);

        AdminPay actual = adminService.vendorPayment(adminPay.getVendor());
        assertEquals(adminPay, actual);
    }

    @Test
    void updatePayment() {
        AdminPay adminPay = new AdminPay("1", "123456", "Priyanshi", 100,
                "Completed", "54321", "XYZ", LocalDate.now(), LocalTime.now());

        VendorPayment vendorPayment = new VendorPayment("Priyanshi", "Geet", "Fitness", 100,
                "Due", LocalDate.now(), LocalTime.now());
        List<VendorPayment> vendorPayments = new ArrayList<>();
        vendorPayments.add(vendorPayment);

        when(adminPayRepository.findByOrderId(data.get("order_id"))).thenReturn(adminPay);
        when(vendorPay.findByVendorAndStatus(adminPay.getVendor(), "Due")).thenReturn(vendorPayments);

        AdminPay actual = adminService.updatePayment(data);
        assertEquals(adminPay, actual);
    }

    @Test
    void returnAllAdminPayModelWhenStatusIsCompleted() {

        AdminPay adminPay1 = new AdminPay("1", "123456", "Priyanshi", 100,
                "Completed", "54321", "XYZ", LocalDate.now(), LocalTime.now());
        AdminPay adminPay2 = new AdminPay("2", "234567", "Priyanshi", 200,
                "Completed", "43210", "ABC", LocalDate.now(), LocalTime.now());

        List<AdminPay> adminPayList = new ArrayList<>();
        adminPayList.add(adminPay1);
        adminPayList.add(adminPay2);

        when(adminPayRepository.findByVendor(adminPay1.getVendor())).thenReturn(adminPayList);
        when(adminPayRepository.findByVendor(adminPay2.getVendor())).thenReturn(adminPayList);

        List<AdminPay> actual = adminService.paidHistoryVendor(adminPay1.getVendor());
        assertEquals(adminPayList, actual);

    }

    @Test
    void doNotReturnAdminPayModelWhenStatusIsNotCompleted() {

        AdminPay adminPay1 = new AdminPay("1", "123456", "Priyanshi", 100,
                "Due", "54321", "XYZ", LocalDate.now(), LocalTime.now());
        AdminPay adminPay2 = new AdminPay("2", "234567", "Priyanshi", 200,
                "Processing", "43210", "ABC", LocalDate.now(), LocalTime.now());

        List<AdminPay> adminPayList = new ArrayList<>();
        adminPayList.add(adminPay1);
        adminPayList.add(adminPay2);

        List<AdminPay> adminPayNull = new ArrayList<>();

        when(adminPayRepository.findByVendor(adminPay1.getVendor())).thenReturn(adminPayList);
        when(adminPayRepository.findByVendor(adminPay2.getVendor())).thenReturn(adminPayList);

        List<AdminPay> actual = adminService.paidHistoryVendor(adminPay1.getVendor());
        assertEquals(adminPayNull, actual);

    }

    @Test
    void returnOnlyThoseAdminPayModelWhoseStatusIsCompleted() {

        AdminPay adminPay1 = new AdminPay("1", "123456", "Priyanshi", 100,
                "Due", "54321", "XYZ", LocalDate.now(), LocalTime.now());
        AdminPay adminPay2 = new AdminPay("2", "234567", "Priyanshi", 200,
                "Completed", "43210", "ABC", LocalDate.now(), LocalTime.now());

        List<AdminPay> adminPayList = new ArrayList<>();
        adminPayList.add(adminPay1);
        adminPayList.add(adminPay2);

        List<AdminPay> adminPays = new ArrayList<>();
        adminPays.add(adminPay2);

        when(adminPayRepository.findByVendor(adminPay1.getVendor())).thenReturn(adminPayList);
        when(adminPayRepository.findByVendor(adminPay2.getVendor())).thenReturn(adminPayList);

        List<AdminPay> actual = adminService.paidHistoryVendor(adminPay1.getVendor());
        assertEquals(adminPays, actual);

    }

    @Test
    void returnNullWhenAdminPayModelIsNull() {
        List<AdminPay> adminPay = new ArrayList<>();

        List<AdminPay> actual = adminService.paidHistoryVendor("Priyanshi");
        assertEquals(adminPay, actual);
    }

    @Test
    void getAllNumber() {
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "VENDOR", true, true, false);
        List<UserClass> userClasses = new ArrayList<>();
        userClasses.add(userClass);
        GymClass gymClass = new GymClass("GM1", "priyanshi.chaturvedi@nineleaps.com", "Fitness",
                workout, 9685903290L, 4.2, 100);
        List<GymClass> gymClasses = new ArrayList<>();
        gymClasses.add(gymClass);
        List<String> nums = new ArrayList<>();
        nums.add(Integer.toString(0));
        nums.add(Integer.toString(1));
        nums.add(Integer.toString(1));
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(nums, HttpStatus.OK);

        when(userRepo.findAll()).thenReturn(userClasses);
        when(gymRepo.findAll()).thenReturn(gymClasses);
        ResponseEntity<List<String>> actual = adminService.getAllNumber();

        assertEquals(actual, responseEntity);
    }

}