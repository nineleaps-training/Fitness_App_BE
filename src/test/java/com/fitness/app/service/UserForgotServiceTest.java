package com.fitness.app.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.components.Components;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserForgotModel;
import com.fitness.app.repository.UserRepo;

@ExtendWith(MockitoExtension.class)
class UserForgotServiceTest {

    @Mock
    UserRepo userRepo;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserForgotService userForgotService;

    @Mock
    Components components;

    @Test
    void testSetPassword() {

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Pankaj@123",
                "VENDOR", false, false, false);
        Authenticate authenticate = new Authenticate("pankaj.jain@nineleaps.com", "Pankaj@123");
        Mockito.when(userRepo.findByEmail(authenticate.getEmail())).thenReturn(userClass);
        Boolean boo = true;
        Boolean boo1=userForgotService.setPassword(authenticate);
        Assertions.assertEquals(boo, boo1);

    }

    @Test
    void testUserForgot() {

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Pankaj@123",
                "VENDOR", false, false, false);
        UserForgotModel userForgot = new UserForgotModel(null, true);
        Mockito.when(userRepo.findByEmail(userClass.getEmail())).thenReturn(userClass);
        Mockito.when(components.sendOtpMessage("hello ", null, userClass.getMobile())).thenReturn(200);
        UserForgotModel userForgotModel = userForgotService.userForgot(userClass.getEmail());
        Assertions.assertEquals(userForgot, userForgotModel);
    
    }

    @Test
    void testUserForgotNull() {

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Pankaj@123",
                "VENDOR", false, false, false);
        UserForgotModel userForgot = new UserForgotModel(null, false);
        Mockito.when(userRepo.findByEmail(userClass.getEmail())).thenReturn(null);
        UserForgotModel userForgotModel = userForgotService.userForgot(userClass.getEmail());
        Assertions.assertEquals(userForgot, userForgotModel);
    
    }

    @Test
    void testUserForgotElse() {

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Pankaj@123",
                "VENDOR", false, false, false);
        UserForgotModel userForgot = new UserForgotModel("Something went wrong..Please try again!!", false);
        Mockito.when(userRepo.findByEmail(userClass.getEmail())).thenReturn(userClass);
        Mockito.when(components.sendOtpMessage("hello ", null, userClass.getMobile())).thenReturn(400);
        UserForgotModel userForgotModel = userForgotService.userForgot(userClass.getEmail());
        Assertions.assertEquals(userForgot, userForgotModel);
    
    }
}
