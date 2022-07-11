package com.fitness.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.auth.Authenticate;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.SignUpResponse;
import com.fitness.app.model.UserModel;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.security.service.UserDetailsServiceImpl;
import com.fitness.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mockMvc;
    UserModel userModel;
    UserClass userClass;
    Authenticate authenticate;

    SignUpResponse signUpResponse;

    UserDetails userDetails;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    UserController userController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        userModel = new UserModel("priyanshi.chaturvedi@nineleaps.com", "Priyanshi", "9685903290", "password", "Enthusiast", true);

        userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
        "9685903290", "12345", "Enthusiast", false, false, true);

        authenticate = new Authenticate("priyanshi.chaturvedi@nineleaps.com", "password");

    }

    @Test
    void registerUser() throws Exception {
        String content = objectMapper.writeValueAsString(userModel);

        when(userRepo.findByEmail(userModel.getEmail())).thenReturn(userClass);

//        when(userService.registerUser(userModel)).thenReturn(signUpResponse.getCurrentUser());

        mockMvc.perform(MockMvcRequestBuilders.post("/register/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

//    @Test
//    void verifyTheUser() throws Exception {
//        String content = objectMapper.writeValueAsString(authenticate);
//
//        when(userService.verifyUser(authenticate.getEmail())).thenReturn(userClass);
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/verify/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
//
//    }

//    @Test
//    void verifyTheUserIfUserIsNull() throws Exception {
//        UserClass userClassNull = new UserClass();
//
//        String content = objectMapper.writeValueAsString(authenticate);
//
//        when(userService.verifyUser(authenticate.getEmail())).thenReturn(userClassNull);
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/verify/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
//
//    }

//    @Test
//    void authenticateUser() throws Exception {
//        String content = objectMapper.writeValueAsString(authenticate);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/login/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
//
//    }

//    @Test
//    void logInFunctionality() {
//        when(userDetailsServiceImpl.loadUserByUsername(userClass.getEmail())).thenReturn(userDetails);
//
//        when(userRepo.findByEmail(userClass.getEmail())).thenReturn(userClass);
//
//
//    }

//    @Test
//    void googleSignInVendor() throws Exception {
//        String content = objectMapper.writeValueAsString(userModel);
//
//        when(userService.randomPass()).thenReturn(userModel.getPassword());
//
//        when(userService.googleSignInMethod(userModel)).thenReturn(userClass);
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/google-sign-in/vendor").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
//
//    }

    @Test
    void googleSignInUser() throws Exception {
        String content = objectMapper.writeValueAsString(userModel);

        when(userService.googleSignInMethod(userModel)).thenReturn(userClass);

        mockMvc.perform(MockMvcRequestBuilders.put("/google-sign-in/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }
}