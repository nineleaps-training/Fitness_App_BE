package com.fitness.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.auth.Authenticate;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.SignUpResponse;
import com.fitness.app.model.UserModel;
import com.fitness.app.service.UserService;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mockMvc;

    Authenticate authenticate;
    UserModel userModel;
    UserClass userClass;
    SignUpResponse signUpResponse;

    @Mock
    private UserService userService;

    @InjectMocks
    UserController userController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        userModel = new UserModel("priyanshi.chaturvei@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "USER", false);

        userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "USER", false, false, false);

        signUpResponse = new SignUpResponse(userClass, " ");

        authenticate = new Authenticate("priyanshi.chaturvedi@nineleaps.com", "12345");

    }

    @Test
    void registerUser() throws Exception {
        String content = objectMapper.writeValueAsString(userModel);

        when(userService.registerNewUser(userModel)).thenReturn(signUpResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/register/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void verifyTheUser() throws Exception {
        String content = objectMapper.writeValueAsString(authenticate);

        when(userService.verifyUser(authenticate.getEmail())).thenReturn(userClass);

        mockMvc.perform(MockMvcRequestBuilders.put("/verify/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void verifyTheUserIfUserIsNull() throws Exception {
        String content = objectMapper.writeValueAsString(authenticate);

//        when(userService.verifyUser(authenticate.getEmail())).thenReturn(userClass);

        mockMvc.perform(MockMvcRequestBuilders.put("/verify/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void authenticateUser() throws Exception {
        String content = objectMapper.writeValueAsString(authenticate);

//        when(userService.logInFunctionality(authenticate.getEmail(), authenticate.getPassword())).thenReturn()

        mockMvc.perform(MockMvcRequestBuilders.post("/login/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void googleSignInVendor() throws Exception {
        UserModel userModel = new UserModel("priyanshi.chaturvei@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "VENDOR", false);
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "VENDOR", false, false, false);

        String content = objectMapper.writeValueAsString(userModel);
        when(userService.randomPass()).thenReturn("12345");
        when(userService.googleSignInMethod(userModel)).thenReturn(userClass);

        mockMvc.perform(MockMvcRequestBuilders.put("/google-sign-in/vendor").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
    }

    @Test
    void googleSignInVendorIfUserIsNull() throws Exception {
        userModel = new UserModel("priyanshi.chaturvei@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "VENDOR", false);

        String content = objectMapper.writeValueAsString(userModel);
        when(userService.googleSignInMethod(userModel)).thenReturn(userClass);

        mockMvc.perform(MockMvcRequestBuilders.put("/google-sign-in/vendor").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
    }

    @Test
    void googleSignInVendorIfRoleIsNotVendor() throws Exception {
        userModel = new UserModel("priyanshi.chaturvei@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "USER", false);

        String content = objectMapper.writeValueAsString(userModel);
        when(userService.randomPass()).thenReturn("12345");
        when(userService.googleSignInMethod(userModel)).thenReturn(userClass);

        mockMvc.perform(MockMvcRequestBuilders.put("/google-sign-in/vendor").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
    }

    @Test
    void googleSignInUser() throws Exception {
        String content = objectMapper.writeValueAsString(userModel);

        when(userService.randomPass()).thenReturn("12345");
        when(userService.googleSignInMethod(userModel)).thenReturn(userClass);

        mockMvc.perform(MockMvcRequestBuilders.put("/google-sign-in/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
    }

    @Test
    void googleSignInUserIfUserIsNull() throws Exception {
        String content = objectMapper.writeValueAsString(userModel);

        when(userService.googleSignInMethod(userModel)).thenReturn(userClass);

        mockMvc.perform(MockMvcRequestBuilders.put("/google-sign-in/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
    }

    @Test
    void googleSignInUserIfRoleIsNotUser() throws Exception {
        UserModel userModel = new UserModel("priyanshi.chaturvei@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "VENDOR", false);
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "VENDOR", false, false, false);

        String content = objectMapper.writeValueAsString(userModel);
        when(userService.randomPass()).thenReturn("12345");
        when(userService.googleSignInMethod(userModel)).thenReturn(userClass);

        mockMvc.perform(MockMvcRequestBuilders.put("/google-sign-in/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
    }
}