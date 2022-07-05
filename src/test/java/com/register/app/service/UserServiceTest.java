package com.register.app.service;

import com.fitness.app.componets.Components;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserModel;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Components sendMessage;

    @InjectMocks
    private UserService userService;

    @Before
    public void setup()
    {

    }


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
    public void initEach()
    {
           String generatedString="generated String";
    }

    @Test
    public void registerUser()
    {
        String otp="4521";
        final int code=200;
         Mockito.when(sendMessage.otpBuilder()).thenReturn(otp);
         Mockito.when(sendMessage.sendOtpMessage(otp, USER_MODEL.getMobile())).thenReturn(code);

         UserClass userRes=userService.registerUser(USER_MODEL);

        Assertions.assertNotNull(userRes);
        Assertions.assertEquals(userRes.getRole(), USER1.getRole());

    }
    @Test
    public void registerUserElsePart()
    {
        String otp="4521";
        final int code=400;
        Mockito.when(sendMessage.otpBuilder()).thenReturn(otp);
        Mockito.when(sendMessage.sendOtpMessage(otp, USER_MODEL.getMobile())).thenReturn(code);
        UserClass userRes=userService.registerUser(USER_MODEL);
        Assertions.assertNull(userRes);


    }


    @Test
    public void verifyUser()
    {
        Optional<UserClass> users=Optional.of(USER1);
        Mockito.when(userRepo.findById(USER1.getEmail())).thenReturn(users);
        UserClass userClass = userService.verifyUser(USER1.getEmail());
        Assertions.assertNotNull(userClass);
        Assertions.assertEquals(userClass.getActivated(), USER1_ACT.getActivated());

    }

    @Test
    public void verifyUserForNUll()
    {
        Optional<UserClass> users=Optional.empty();
        Mockito.when(userRepo.findById(USER1.getEmail())).thenReturn(users);
        UserClass userClass = userService.verifyUser(USER1.getEmail());
        Assertions.assertNull(userClass);


    }


    @Test
    public void loginUser()
    {
        Optional<UserClass> userClass = Optional.of(USER1);
        Mockito.when(userRepo.findById(USER1.getEmail())).thenReturn(userClass);
        userService.loginUser(USER1.getEmail());
        Assertions.assertEquals(userClass.get().getLoggedin(), true);
    }

    @Test
    public void googleSignInMethod()
    {   UserClass localUser=null;
        Mockito.when(userRepo.findByEmail(USER_MODEL_G.getEmail())).thenReturn(localUser);
        Mockito.when(passwordEncoder.encode(USER_MODEL_G.getPassword())).thenReturn("PassordEncoded");
        UserClass returned=userService.googleSignInMethod(USER_MODEL_G);
        Assertions.assertNotNull(returned);
        Assertions.assertEquals(returned.getMobile(), USER_MODEL_G.getMobile());
    }

    @Test
    public void googleSignInMethodForAgainLogIn()
    {
        Mockito.when(userRepo.findByEmail(USER_MODEL_G.getEmail())).thenReturn(USER1_G);
        Mockito.when(passwordEncoder.encode(USER_MODEL_G.getPassword())).thenReturn("PassordEncoded");
        UserClass returned=userService.googleSignInMethod(USER_MODEL_G);
        Assertions.assertNotNull(returned);
        Assertions.assertEquals(returned.getMobile(), USER1_G.getMobile());
    }


    @Test
    public void googleSignInMethodForCustomLogIn()
    {
        Mockito.lenient().when(userRepo.findByEmail(USER_MODEL_G.getEmail())).thenReturn(USER1);
        Mockito.lenient().when(passwordEncoder.encode(USER_MODEL_G.getPassword())).thenReturn("PassordEncoded");
        UserClass returned=userService.googleSignInMethod(USER_MODEL_G);
        Assertions.assertNull(returned);

    }


    @Test
    public void randompass()throws  Exception
    {
        Random random= SecureRandom.getInstanceStrong();
        String pass="hello";

        String ans=userService.randomPass();
        Assertions.assertNotNull(ans);
    }


}
