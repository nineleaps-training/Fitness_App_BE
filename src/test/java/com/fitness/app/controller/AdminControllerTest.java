package com.fitness.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.auth.Authenticate;
import com.fitness.app.config.JwtUtils;
import com.fitness.app.entity.AdminPay;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
    AdminPay adminPay;
    List<AdminPay> adminPays = new ArrayList<>();


    UserClass userClass;
    List<UserClass> userClasses = new ArrayList<>();
    GymClass gymClass;
    List<GymClass> gymClasses = new ArrayList<>();
    List<String> workout = new ArrayList<>();


    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    UserDetails userDetails;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserRepository userRepo;

    @Mock
    private VendorRepository vendorRepo;

    @Mock
    private AddGymRepository gymRepo;

    @Mock
    private AdminService adminService;

    @InjectMocks
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

        adminPay = new AdminPay("1", "123456", "Priyanshi", 100,
                "Completed", "54321", "XYZ", LocalDate.now(), LocalTime.now());
        adminPays.add(adminPay);

        userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "USER", false, false, true);
        userClasses.add(userClass);

        gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);
        gymClasses.add(gymClass);

    }

    @Test
    void authenticateUser() throws Exception {
        String content = objectMapper.writeValueAsString(authenticate);

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/login").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void getAllUsers() throws Exception {
        when(adminService.getAllUsers(0, 1)).thenReturn(userClasses);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/getAllUsers/0/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getAllVendors() throws Exception {
        when(adminService.getAllVendors(0, 1)).thenReturn(userClasses);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/getAllVendors/0/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void getAllGyms() throws Exception {
        when(adminService.getAllGyms(0, 1)).thenReturn(gymClasses);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/getAllGyms/0/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getAllGymsByEmail() throws Exception {
        when(adminService.getAllGymsByEmail(gymClass.getEmail())).thenReturn(gymClasses);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/getAllGymsByEmail/priyanshi.chaturvedi@nineleaps.com").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void vendorPayment() throws Exception {
        when(adminService.vendorPayment(adminPayModel.getVendor())).thenReturn(adminPay);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/vendorPayment/Priyanshi").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void getDataPay() throws Exception {
        AdminPay adminPay = new AdminPay("P00", "123456", "Priyanshi",
                100, "Due", "54321", "XYZ", null, null);

        String content = objectMapper.writeValueAsString(adminPayModel);

        when(adminService.getDataPay(adminPayModel)).thenReturn(adminPay);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/getDataPay").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void payNow() throws Exception {
        String content = objectMapper.writeValueAsString(adminPayModel);

        when(adminService.payNow(adminPayModel, order)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/admin/payVendorNow").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void returnNullIfPayNowIsNull() throws Exception {
        String content = objectMapper.writeValueAsString(adminPayModel);

        when(adminService.payNow(adminPayModel, order)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put("/admin/payVendorNow").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void updatingOrder() throws Exception {
        String content = objectMapper.writeValueAsString(data);

        when(adminService.updatePayment(data)).thenReturn(adminPay);

        mockMvc.perform(MockMvcRequestBuilders.put("/admin/updateVendorPayment").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void paidHistory() throws Exception {
        when(adminService.paidHistoryVendor(adminPay.getVendor())).thenReturn(adminPays);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/paidHistory/Priyanshi").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void getAllNumber() throws Exception {

        when(userRepo.findAll()).thenReturn(userClasses);
        when(gymRepo.findAll()).thenReturn(gymClasses);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/allNumbers").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

}