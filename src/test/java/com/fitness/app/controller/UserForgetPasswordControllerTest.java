package com.fitness.app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.auth.Authenticate;
import com.fitness.app.components.Components;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserForgot;
import com.fitness.app.repository.UserRepo;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.SecureRandom;

@ExtendWith(MockitoExtension.class)
class UserForgetPasswordControllerTest {

    @InjectMocks
    UserForgetPasswordController userForgetPasswordController;

    @Mock
    UserRepo userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    Components components;

    @Mock
    SecureRandom secureRandom;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userForgetPasswordController).build();
    }

    @Test
    @DisplayName("Testing of setting the new password")
    void testSetPassword() throws Exception {

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Pankaj@123",
                "VENDOR", false, false, false);
        Authenticate authenticate = new Authenticate("pankaj.jain@nineleaps.com", "Pankaj@123");
        String content = objectMapper.writeValueAsString(authenticate);
        Mockito.when(userRepository.findByEmail(authenticate.getEmail())).thenReturn(userClass);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/user/set-password").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Testing of user forgot function")
    void testUserForgot() throws Exception {

        UserForgot userForgot = new UserForgot();
        String content = objectMapper.writeValueAsString(userForgot);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/forget/user/pankaj.jain@nineleaps.com").contentType(MediaType.APPLICATION_JSON)
                .content(content)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testing of user forgot with null values")
    void testUserForgotNull() throws Exception {

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Pankaj@123",
                "VENDOR", false, false, false);
        UserForgot userForgot = new UserForgot();
        String content = objectMapper.writeValueAsString(userForgot);
        when(userRepository.findByEmail(userClass.getEmail())).thenReturn(userClass);
        when(components.otpBuilder()).thenReturn("hello");
        when(components.sendOtpMessage(anyString(), anyString(), anyString())).thenReturn(200);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/forget/user/pankaj.jain@nineleaps.com").contentType(MediaType.APPLICATION_JSON)
                .content(content)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testing of user forgot when code is returned")
    void testUserForgotNullCode() throws Exception {

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Pankaj@123",
                "VENDOR", false, false, false);
        UserForgot userForgot = new UserForgot();
        String content = objectMapper.writeValueAsString(userForgot);
        when(userRepository.findByEmail(userClass.getEmail())).thenReturn(userClass);
        when(components.otpBuilder()).thenReturn("hello");
        when(components.sendOtpMessage(anyString(), anyString(), anyString())).thenReturn(404);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/forget/user/pankaj.jain@nineleaps.com").contentType(MediaType.APPLICATION_JSON)
                .content(content)).andExpect(status().isOk());
    }
}
