package com.register.app.service;

import com.fitness.app.componets.MessageComponents;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserModel;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {


    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MessageComponents sendMessage;

    @InjectMocks
    private UserServiceImpl userServiceImpl;



    UserModel USER_MODEL=new UserModel(
            "rahul",
            "Rahul Khamperia",
            "7651977515",
            "Rahul@123",
            "USER",
            true
    );
    UserModel USER_MODEL_G=new UserModel(
            "rahul",
            "Rahul Khamperia",
            "7651977515",
            "Rahul@123",
            "USER",
            false
    );

    UserClass USER1_ACT=new UserClass(
            "rahul",
            "Rahul Khamperia",
            "7651977515",
            "Rahul@123",
            "USER",
            true,
            true,
            true
    );
    UserClass USER1=new UserClass(
            "rahul",
            "Rahul Khamperia",
            "7651977515",
            "Rahul@123",
            "USER",
            false,
            false,
            true
    );
    UserClass USER1_G=new UserClass(
            "rahul",
            "Rahul Khamperia",
            "7651977515",
            "Rahul@123",
            "USER",
            false,
            false,
            false
    );

    @BeforeEach
    void initEach()
    {
           String generatedString="generated String";
    }

    @Test
     void registerUser()
    {
        String otp="4521";
        final int code=200;
         Mockito.when(sendMessage.otpBuilder()).thenReturn(otp);
         Mockito.when(sendMessage.sendOtpMessage(otp, USER_MODEL.getMobile())).thenReturn(code);

         UserClass userRes= userServiceImpl.registerUser(USER_MODEL);

        Assertions.assertNotNull(userRes);
        Assertions.assertEquals(userRes.getRole(), USER1.getRole());

    }
    @Test
     void registerUserElsePart()
    {
        String otp="4521";
        final int code=400;
        Mockito.when(sendMessage.otpBuilder()).thenReturn(otp);
        Mockito.when(sendMessage.sendOtpMessage(otp, USER_MODEL.getMobile())).thenReturn(code);
        UserClass userRes= userServiceImpl.registerUser(USER_MODEL);
        Assertions.assertNull(userRes);


    }


    @Test
     void verifyUser()
    {
        Optional<UserClass> users=Optional.of(USER1);
        Mockito.when(userRepo.findById(USER1.getEmail())).thenReturn(users);
        UserClass userClass = userServiceImpl.verifyUser(USER1.getEmail());
        Assertions.assertNotNull(userClass);
        Assertions.assertEquals(userClass.getActivated(), USER1_ACT.getActivated());

    }

    @Test
     void verifyUserForNUll()
    {
        Optional<UserClass> users=Optional.empty();
        Mockito.when(userRepo.findById(USER1.getEmail())).thenReturn(users);
        UserClass userClass = userServiceImpl.verifyUser(USER1.getEmail());
        Assertions.assertNull(userClass);


    }


    @Test
     void loginUser()
    {
        Optional<UserClass> userClass = Optional.of(USER1);
        Mockito.when(userRepo.findById(USER1.getEmail())).thenReturn(userClass);
        userServiceImpl.loginUser(USER1.getEmail());
        Assertions.assertEquals(true, userClass.get().getLoggedin());
    }

    @Test
     void googleSignInMethod()
    {   UserClass localUser=null;
        Mockito.when(userRepo.findByEmail(USER_MODEL_G.getEmail())).thenReturn(localUser);
        Mockito.when(passwordEncoder.encode(USER_MODEL_G.getPassword())).thenReturn("PassordEncoded");
        UserClass returned= userServiceImpl.googleSignInMethod(USER_MODEL_G);
        Assertions.assertNotNull(returned);
        Assertions.assertEquals(returned.getMobile(), USER_MODEL_G.getMobile());
    }

    @Test
     void googleSignInMethodForAgainLogIn()
    {
        Mockito.when(userRepo.findByEmail(USER_MODEL_G.getEmail())).thenReturn(USER1_G);
        Mockito.when(passwordEncoder.encode(USER_MODEL_G.getPassword())).thenReturn("PassordEncoded");
        UserClass returned= userServiceImpl.googleSignInMethod(USER_MODEL_G);
        Assertions.assertNotNull(returned);
        Assertions.assertEquals(returned.getMobile(), USER1_G.getMobile());
    }


    @Test
     void googleSignInMethodForCustomLogIn()
    {
        Mockito.lenient().when(userRepo.findByEmail(USER_MODEL_G.getEmail())).thenReturn(USER1);
        Mockito.lenient().when(passwordEncoder.encode(USER_MODEL_G.getPassword())).thenReturn("PassordEncoded");
        UserClass returned= userServiceImpl.googleSignInMethod(USER_MODEL_G);
        Assertions.assertNull(returned);

    }


    @Test
     void randompass()throws  Exception
    {
        Random random= SecureRandom.getInstanceStrong();
        String pass="hello";

        String ans= userServiceImpl.randomPass();
        Assertions.assertNotNull(ans);
    }


}
