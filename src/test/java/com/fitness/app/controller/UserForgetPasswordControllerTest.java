package com.fitness.app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.auth.Authenticate;
import com.fitness.app.components.Components;
import com.fitness.app.model.UserForgotModel;
import com.fitness.app.repository.UserRepo;
import com.fitness.app.service.UserForgotService;

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

    @Mock
    UserForgotService userForgotService;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userForgetPasswordController).build();
    }

    @Test
    @DisplayName("Testing of setting the new password")
    void testSetPassword() throws Exception {

        Authenticate authenticate = new Authenticate("pankaj.jain@nineleaps.com", "Pankaj@123");
        String content = objectMapper.writeValueAsString(authenticate);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/user/set-password").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Testing of user forgot function")
    void testUserForgot() throws Exception {

        UserForgotModel userForgot = new UserForgotModel();
        String content = objectMapper.writeValueAsString(userForgot);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/forget/user/pankaj.jain@nineleaps.com").contentType(MediaType.APPLICATION_JSON)
                .content(content)).andExpect(status().isOk());
    }
}