package com.fitness.app.service;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.componets.Components;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UserForgetPasswordServiceTest {

    UserClass userClass;
    UserForgot userForgot;
    Authenticate authenticate;
    String otp = "2468";


    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    Components components;

    @InjectMocks
    UserForgetPasswordService userForgetPasswordService;

    @BeforeEach
    public void setUp() {

        userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", false, false, true);

        userForgot = new UserForgot("2468", true);
        authenticate = new Authenticate("priyanshi.chaturvedi@nineleaps.com", "12345");
    }

    @Test
    void userForgotPasswordIfStatusCodeIs200() {
        when(userRepo.findByEmail(userClass.getEmail())).thenReturn(userClass);
        when(components.otpBuilder()).thenReturn(otp);
        when(components.sendOtpMessage("hello ", otp, userClass.getMobile())).thenReturn(200);

        UserForgot actual = userForgetPasswordService.userForgot(userClass.getEmail());
        assertEquals(userForgot, actual);
    }

    @Test
    void userForgotPasswordIfStatusCodeIS400() {
        UserForgot userForgot = new UserForgot("Something went wrong..Please try again!!", false);

        when(userRepo.findByEmail(userClass.getEmail())).thenReturn(userClass);

        UserForgot actual = userForgetPasswordService.userForgot(userClass.getEmail());
        assertEquals(userForgot, actual);
    }

    @Test
    void returnUserForgotIfUserClassIsNull() {
        UserForgot userForgot = new UserForgot(null, false);

        when(userRepo.findByEmail(userClass.getEmail())).thenReturn(null);

        UserForgot actual = userForgetPasswordService.userForgot(userClass.getEmail());
        assertEquals(userForgot, actual);
    }

    @Test
    void setPassword() {
        when(userRepo.findByEmail(authenticate.getEmail())).thenReturn(userClass);

        boolean actual = userForgetPasswordService.setPassword(authenticate);
        assertTrue(actual);
    }
}