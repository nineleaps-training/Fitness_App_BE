package com.fitness.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.auth.Authenticate;
import com.fitness.app.config.JwtUtils;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.AdminPayModel;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.repository.VendorRepository;
import com.fitness.app.security.service.UserDetailsServiceImpl;
import com.fitness.app.service.AdminService;
import com.razorpay.Order;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.mail.AuthenticationFailedException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdminControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mockMvc;

    Order order;
    HashMap<String, String> data;

    Authenticate authenticate;
    AdminPayModel adminPayModel;
    List<AdminPayModel> adminPayModels = new ArrayList<>();

    UserClass userClass;
    List<UserClass> userClasses = new ArrayList<>();
    GymClass gymClass;
    List<GymClass> gymClasses = new ArrayList<>();
    List<String> workout = new ArrayList<>();



    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    UserDetails userDetails;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private VendorRepository vendorRepo;

    @MockBean
    private AddGymRepository gymRepo;

    @MockBean
    private AdminService adminService;

    @Autowired
    AdminController adminController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

        JSONObject jsonObject = new JSONObject();
        order = new Order(jsonObject.put("abcd", 1234));

        data = new HashMap<>();
        data.put("order_id", "12345");
        data.put("payment_id", "54321");
        data.put("status", "Completed");

        authenticate = new Authenticate("priyanshi.chaturvedi@nineleaps.com", "12345");

        adminPayModel = new AdminPayModel("1", "123456", "Priyanshi", 100,
                "created", "54321", "XYZ", null, null);
        adminPayModels.add(adminPayModel);

        userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", false, false, true);
        userClasses.add(userClass);

        gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);
        gymClasses.add(gymClass);

    }

    @Test
    void authenticateUser() throws Exception {

        Authentication authentication = null;
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "ADMIN", false, false, true);

        String content = objectMapper.writeValueAsString(authenticate);
        when(authenticationManager.authenticate(authentication)).thenReturn(authentication);
        when(userDetailsService.loadUserByUsername(authenticate.getEmail())).thenReturn(userDetails);
        when(jwtUtils.generateToken(userDetails)).thenReturn("");
        when(userRepo.findByEmail(authenticate.getEmail())).thenReturn(userClass);

        mockMvc.perform(MockMvcRequestBuilders.post("/login/admin").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void authenticateUserIfRoleIsNotAdmin() throws Exception {

        Authentication authentication = null;
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", false, false, true);

        String content = objectMapper.writeValueAsString(authenticate);
        when(authenticationManager.authenticate(authentication)).thenReturn(authentication);
        when(userDetailsService.loadUserByUsername(authenticate.getEmail())).thenReturn(userDetails);
        when(jwtUtils.generateToken(userDetails)).thenReturn("");
        when(userRepo.findByEmail(authenticate.getEmail())).thenReturn(userClass);

        mockMvc.perform(MockMvcRequestBuilders.post("/login/admin").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

//    @Test
//    void authenticateUser() throws Exception {
//        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
//                "9685903290", "12345", "ADMIN", false, false, true);
//
//        String content = objectMapper.writeValueAsString(authenticate);
//
//        when(userDetailsService.loadUserByUsername(authenticate.getEmail())).thenReturn(userDetails);
//        when(jwtUtils.generateToken(userDetails)).thenReturn("");
//        when(userRepo.findByEmail(authenticate.getEmail())).thenReturn(userClass);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/login/admin").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isBadRequest());
//
//    }

    @Test
    void getAllUsers() throws Exception {
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "USER", false, false, true);
        List<UserClass> userClassList = new ArrayList<>();
        userClassList.add(userClass);

        when(userRepo.findAll()).thenReturn(userClassList);

        mockMvc.perform(MockMvcRequestBuilders.get("/get-all-users").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getAllVendors() throws Exception {
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "VENDOR", false, false, true);
        List<UserClass> userClassList = new ArrayList<>();
        userClassList.add(userClass);

        when(userRepo.findAll()).thenReturn(userClassList);

        mockMvc.perform(MockMvcRequestBuilders.get("/get-all-vendors").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void getAllGyms() throws Exception {
        List<String> workout = new ArrayList<>();
        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);
        List<GymClass> gymClasses = new ArrayList<>();
        gymClasses.add(gymClass);

        when(gymRepo.findAll()).thenReturn(gymClasses);

        mockMvc.perform(MockMvcRequestBuilders.get("/get-all-gyms").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getAllGymsByEmail() throws Exception {
        List<String> workout = new ArrayList<>();
        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);
        List<GymClass> gymClasses = new ArrayList<>();
        gymClasses.add(gymClass);

        when(gymRepo.findByEmail(gymClass.getEmail())).thenReturn(gymClasses);

        mockMvc.perform(MockMvcRequestBuilders.get("/get-all-gyms-by-email/priyanshi.chaturvedi@nineleaps.com").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void vendorPayment() throws Exception {
        AdminPayModel adminPayModel = new AdminPayModel("P00", "123456", "Priyanshi",
                100, "Due", "54321", "XYZ", LocalDate.now(), LocalTime.now());

        when(adminService.vendorPayment(adminPayModel.getVendor())).thenReturn(adminPayModel);

        mockMvc.perform(MockMvcRequestBuilders.get("/vendor-payment/Priyanshi").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void getDatapay() throws Exception {

        AdminPayModel adminPayModel = new AdminPayModel("P00", "123456", "Priyanshi",
                100, "Due", "54321", "XYZ", null, null);

        String content = objectMapper.writeValueAsString(adminPayModel);

        when(adminService.getDataPay(adminPayModel)).thenReturn(adminPayModel);

        mockMvc.perform(MockMvcRequestBuilders.get("/get-data-pay").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void payNow() throws Exception {
        String content = objectMapper.writeValueAsString(adminPayModel);

        when(adminService.payNow(adminPayModel, order)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/pay-vendor-now").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void returnNullIfPayNowIsNull() throws Exception {
        String content = objectMapper.writeValueAsString(adminPayModel);

        when(adminService.payNow(adminPayModel, order)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put("/pay-vendor-now").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void updatingOrder() throws Exception {
        String content = objectMapper.writeValueAsString(data);

        when(adminService.updatePayment(data)).thenReturn(adminPayModel);

        mockMvc.perform(MockMvcRequestBuilders.put("/update-vendor-payment").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void paidHistory() throws Exception {

        when(adminService.paidHistoryVendor(adminPayModel.getVendor())).thenReturn(adminPayModels);

        mockMvc.perform(MockMvcRequestBuilders.get("/paid-history/Priyanshi").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void getAllNumber() throws Exception {

        when(userRepo.findAll()).thenReturn(userClasses);

        when(gymRepo.findAll()).thenReturn(gymClasses);

        mockMvc.perform(MockMvcRequestBuilders.get("/all-numbers").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

}