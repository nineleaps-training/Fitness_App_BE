package com.fitness.app.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fitness.app.auth.Authenticate;
import com.fitness.app.config.JwtUtils;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.AdminPayRequestModel;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.AdminPayRepo;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.security.service.UserDetailsServiceImpl;
import com.fitness.app.service.AdminService;
import com.fitness.app.service.PagingService;
import com.razorpay.RazorpayException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @InjectMocks
    AdminController adminController;

    @Mock
    UserRepository userRepo;

    @Mock
    AddGymRepository gymRepo;

    @Mock
    AdminService adminService;

    @Mock
    AdminPayRepo adminPayRepo;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    JwtUtils jwtUtils;

    @Mock
    PagingService pagingService;

    List<String> aList=new ArrayList<>(List.of("Hello"));

    MockMvc mockMvc;

    long l =123;

    GymClass gymClass=new GymClass("GM6","pankaj.jain@nineleaps.com","Pankaj Jain",aList,l,2.3,15);

    UserClass userClass=new UserClass("pankaj.jain@nineleaps.com","Pankaj Jain","8469492322","hello@123","USER",true,true,true);

    UserClass userClass1=new UserClass("pankaj.jain@nineleaps.com","Pankaj Jain","8469492322","hello@123","VENDOR",true,true,true);

    ObjectMapper objectMapper1 = new ObjectMapper();
    
    ObjectMapper objectMapper = 
    new ObjectMapper().registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    List<GymClass> gymClasses=new ArrayList<>();
    List<UserClass>userClasses=new ArrayList<>();
    
    @BeforeEach
    public void setup()
    {
        this.mockMvc=MockMvcBuilders.standaloneSetup(adminController).build();
    }
        
    @Test
    void testAuthenticateUser() throws Exception {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_admin"));
        org.springframework.security.core.userdetails.UserDetails userDetails=new org.springframework.security.core.userdetails.User("pankaj.jain@nineleaps.com", "Pankaj@123", authorities);
        UserClass userClass=new UserClass("pankaj.jain@nineleaps.com", "fullName", "mobile", "password", "ADMIN", true, true, true);
        Authenticate authenticate=new Authenticate("pankaj.jain@nineleaps.com","Pankaj@123");       
        String content=objectMapper.writeValueAsString(authenticate);
        Mockito.when(userRepo.findByEmail("pankaj.jain@nineleaps.com")).thenReturn(userClass);
        Mockito.when(userDetailsServiceImpl.loadUserByUsername(authenticate.getEmail())).thenReturn(userDetails);
        mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/login/admin").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
    }

    @Test
    void testAuthenticateUserNull() throws Exception {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_user"));
        org.springframework.security.core.userdetails.UserDetails userDetails=new org.springframework.security.core.userdetails.User("pankaj.jain@nineleaps.com", "Pankaj@123", authorities);
        UserClass userClass=new UserClass("pankaj.jain@nineleaps.com", "fullName", "mobile", "password", "USER", true, true, true);
        Authenticate authenticate=new Authenticate("pankaj.jain@nineleaps.com","Pankaj@123");       
        String content=objectMapper.writeValueAsString(authenticate);
        Mockito.when(userRepo.findByEmail("pankaj.jain@nineleaps.com")).thenReturn(userClass);
        Mockito.when(userDetailsServiceImpl.loadUserByUsername(authenticate.getEmail())).thenReturn(userDetails);
        mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/login/admin").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
    }

    @Test
    void testGetAllGyms() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/get-all-gyms/0/2").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void testGetAllGymsByEmail() throws Exception {

        List<GymClass> gymClasses=new ArrayList<>();
        gymClasses.add(gymClass);
        Mockito.when(gymRepo.findByEmail("pankaj.jain@nineleaps.com")).thenReturn(gymClasses);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/get-all-gyms-by-email/pankaj.jain@nineleaps.com").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void testGetAllNumber() throws Exception {
        List<String> list=new ArrayList<>();
        list.add("4");
        list.add("1");
        list.add("5");
        
        userClasses.add(userClass);
        userClasses.add(userClass1);
        gymClasses.add(gymClass);
        Mockito.when(userRepo.findAll()).thenReturn(userClasses);
        Mockito.when(gymRepo.findAll()).thenReturn(gymClasses);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/all-numbers").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


    }

    @Test
    void testGetAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/get-all-users/0/2").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void testGetAllVendors() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/get-all-vendors/0/2").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


    }

    @Test
    void testGetDatapay() throws Exception {
        
          LocalDate localDate=LocalDate.now();
          LocalTime localTime=LocalTime.now();
          AdminPayRequestModel adminPayRequestModel=new AdminPayRequestModel("GM6","orderid","vendor",123,"Due","string","string",localDate,localTime);
          String content=objectMapper.writeValueAsString(adminPayRequestModel);
          Mockito.when(adminService.getDataPay(adminPayRequestModel)).thenReturn(adminPayRequestModel);
          mockMvc.perform(MockMvcRequestBuilders
          .get("/v1/get-data-pay").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
    }

    @Test
    void testPaidHistroy() throws Exception {
        
        LocalDate localDate=LocalDate.of(2022, 6, 24);
        LocalTime localTime=LocalTime.of(12, 32, 4);
        AdminPayRequestModel adminPayRequestModel=new AdminPayRequestModel("GM6","orderid","vendor",123,"string","string","string",localDate,localTime);
        List<AdminPayRequestModel> list=new ArrayList<>();
        list.add(adminPayRequestModel);
        Mockito.when(adminService.paidHistroyVendor(adminPayRequestModel.getVendor())).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/paid-history/vendor").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


    }

    @Test
    void testPayNow() throws RazorpayException {

        try
        {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_admin"));
            LocalDate localDate=LocalDate.now();
            LocalTime localTime=LocalTime.now();
            AdminPayRequestModel adminPayRequestModel=new AdminPayRequestModel("GM6","orderid","vendor",123,"Completed","string","string",localDate,localTime);
            String content=objectMapper.writeValueAsString(adminPayRequestModel);
            mockMvc.perform(MockMvcRequestBuilders
            .put("/v1/pay-vendor-now").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
        }
        catch(RazorpayException e)
        {
            Assertions.assertEquals("Cannot pay", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testPayNowNull() throws RazorpayException {

        try
        {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_admin"));
            LocalDate localDate=LocalDate.now();
            LocalTime localTime=LocalTime.now();
            AdminPayRequestModel adminPayRequestModel=new AdminPayRequestModel("GM6","orderid","vendor",123,"Completed","string","string",localDate,localTime);
            String content=objectMapper.writeValueAsString(adminPayRequestModel);
            mockMvc.perform(MockMvcRequestBuilders
            .put("/v1/pay-vendor-now").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
        }
        catch(RazorpayException e)
        {
            Assertions.assertEquals("Cannot pay", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testUpdatingOrder() throws Exception {
        
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        AdminPayRequestModel adminPayRequestModel=new AdminPayRequestModel("GM6","orderid","vendor",123,"Due","string","string",localDate,localTime);
        List<AdminPayRequestModel> list=new ArrayList<>();
        list.add(adminPayRequestModel);
        Map<String,String> data=new HashMap<>();
        data.put("order_id", "id");
        data.put("payment_id", "id");
        data.put("status", "Due");
        Mockito.when(adminService.updatePayment(data)).thenReturn(adminPayRequestModel);
        String content=objectMapper.writeValueAsString(data);
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/update-vendor-payment").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void testVendorPayment() throws Exception {
        
        LocalDate localDate=LocalDate.of(2022, 6, 24);
        LocalTime localTime=LocalTime.of(12, 32, 4);
        AdminPayRequestModel adminPayRequestModel=new AdminPayRequestModel("GM6","orderid","vendor",123,"string","string","string",localDate,localTime);
        Mockito.when(adminService.vendorPayment(adminPayRequestModel.getVendor())).thenReturn(adminPayRequestModel);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/vendor-payment/vendor").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


    }
}
