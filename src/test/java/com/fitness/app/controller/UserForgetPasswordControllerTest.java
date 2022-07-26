package com.fitness.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.auth.Authenticate;
import com.fitness.app.componets.Components;
import com.fitness.app.dao.UserForgetPasswordDao;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserForgot;
import com.fitness.app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UserForgetPasswordControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mockMvc;

    UserClass userClass;
    Authenticate authenticate;
    String otp = "2468";

    @Mock
    UserForgetPasswordDao userForgetPasswordDao;

    @InjectMocks
    UserForgetPasswordController userForgetPasswordController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userForgetPasswordController).build();

        userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", false, false, true);

        authenticate = new Authenticate("priyanshi.chaturvedi@nineleaps.com", "12345");
    }


    @Test
    void userForgotPasswordIfStatusCodeIs200() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/forget/user/priyanshi.chaturvedi@nineleaps.com").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void setPassword() throws Exception {
        String content = objectMapper.writeValueAsString(authenticate);

        when(userForgetPasswordDao.setPassword(authenticate)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/user/set-password").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }
}