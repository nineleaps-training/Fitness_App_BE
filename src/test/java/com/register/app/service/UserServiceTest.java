package com.register.app.service;

import com.fitness.app.config.JwtUtils;
import com.fitness.app.dto.Response;
import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.dto.requestDtos.UserModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.UserClass;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.security.service.LoginUserSec;
import com.fitness.app.security.service.UserDetailsSecServiceImpl;
import com.fitness.app.service.UserServiceImpl;
import com.fitness.app.utils.MessageComponents;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private LoginUserSec loginUserSec;

    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private UserDetailsSecServiceImpl  userDetailsSecService;
    @Mock
    private MessageComponents sendMessage;

    @InjectMocks
    private UserServiceImpl userService;


    UserModel USER_MODEL = new UserModel(
            "rahul",
            "Rahul Khamperia",
            "7651977515",
            "Rahul@123",
            "USER",
            true
    );
    UserModel USER_MODEL_G = new UserModel(
            "rahul",
            "Rahul Khamperia",
            "7651977515",
            "Rahul@123",
            "USER",
            false
    );

    UserClass USER1_ACT = new UserClass(
            "rahul",
            "Rahul Khamperia",
            "7651977515",
            "Rahul@123",
            "USER",
            true,
            true,
            true
    );
    UserClass USER1 = new UserClass(
            "rahul",
            "Rahul Khamperia",
            "7651977515",
            "Rahul@123",
            "USER",
            false,
            false,
            true
    );
    UserClass USER1_G = new UserClass(
            "rahul",
            "Rahul Khamperia",
            "7651977515",
            "Rahul@123",
            "USER",
            false,
            false,
            false
    );

    final String otp="2020";
    final String body="your verification code is: " + otp;
    final String subject="Verify YourSelf : Fitness Freak";
    @BeforeEach
    void initEach() {
        String generatedString = "generated String";
    }

    @Test
    @DisplayName("Already exist with activated")
    void registerUser() {
        Mockito.when(userRepo.findByEmail(USER_MODEL.getEmail())).thenReturn(USER1_ACT);
        ApiResponse response = userService.registerUser(USER_MODEL);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatus());
    }

    @Test
    @DisplayName("Already Exist but not activated:")
    void registerUserForNonActivated()
    {
        Mockito.when(userRepo.findByEmail(USER_MODEL.getEmail())).thenReturn(USER1);
        Mockito.when(sendMessage.otpBuilder()).thenReturn(otp);
        Mockito.when(sendMessage.sendMail(USER_MODEL.getEmail(), body, subject)).thenReturn(200);
        ApiResponse response = userService.registerUser(USER_MODEL);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.CONTINUE, response.getStatus());
    }
    @Test
    @DisplayName("New User Register: and send message too verify:")
    void registerUserElsePart() {
        UserClass localUser=new UserClass();
        Mockito.when(userRepo.findByEmail(USER_MODEL.getEmail())).thenReturn(null);
        Mockito.when(sendMessage.otpBuilder()).thenReturn(otp);
        Mockito.when(sendMessage.sendMail(USER_MODEL.getEmail(), body, subject)).thenReturn(200);
        ApiResponse response = userService.registerUser(USER_MODEL);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatus());


    }

    @Test
    @DisplayName("New User Register: and send message too verify:")
    void registerUserElsePartWithWrongMail() {
        UserClass localUser=new UserClass();
        Mockito.when(userRepo.findByEmail(USER_MODEL.getEmail())).thenReturn(null);
        Mockito.when(sendMessage.otpBuilder()).thenReturn(otp);
        Mockito.when(sendMessage.sendMail(USER_MODEL.getEmail(), body, subject)).thenReturn(100);
        ApiResponse response = userService.registerUser(USER_MODEL);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.RESET_CONTENT, response.getStatus());


    }

   UserDetails userDetails= new org.springframework.security.core.userdetails.User(USER_MODEL.getEmail(), USER_MODEL.getPassword(), new ArrayList<>());
    @Test
    @DisplayName("Verify user and get token: ")
    void verifyUser() {
        Optional<UserClass> users = Optional.of(USER1);
        Mockito.when(userRepo.findById(USER1.getEmail())).thenReturn(users);
        Mockito.when(userDetailsSecService.loadUserByUsername(USER_MODEL.getEmail())).thenReturn(userDetails);
        Mockito.when(jwtUtils.generateToken(userDetails)).thenReturn("Token");
        ApiResponse response = userService.verifyUser(USER1.getEmail(), "2545");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatus());


    }

    @Test
    @DisplayName("Wrong otp: ")
    void verifyUserOtpNotCorrect() {
        Optional<UserClass> users = Optional.of(USER1);
        Mockito.when(userRepo.findById(USER1.getEmail())).thenReturn(users);
        ApiResponse response = userService.verifyUser(USER1.getEmail(), "2145");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatus());


    }


    @Test
    void loginUser() {
        ApiResponse response=new ApiResponse(HttpStatus.OK, "Token");
        Authenticate authenticate=new Authenticate("Rahul", "Rahul");
        Mockito.when(loginUserSec.logInUserRes(authenticate)).thenReturn(response);
        ApiResponse expectedRes=userService.loginUser(authenticate);
        Assertions.assertNotNull(expectedRes);
        Assertions.assertEquals(HttpStatus.OK, expectedRes.getStatus());


    }

    @Test
    @DisplayName("Onboard with google: already registered with google")
    void googleSignInMethod() {
        UserClass localUser = null;
        Mockito.when(userRepo.findByEmail(USER_MODEL_G.getEmail())).thenReturn(USER1_G);
        Mockito.when(passwordEncoder.encode(USER_MODEL_G.getPassword())).thenReturn("PasswordEncoded");
        Mockito.when(userDetailsSecService.loadUserByUsername(USER_MODEL.getEmail())).thenReturn(userDetails);
        Mockito.when(jwtUtils.generateToken(userDetails)).thenReturn("Token");
        ApiResponse returned = userService.googleSignInMethod(USER_MODEL_G);
        Assertions.assertNotNull(returned);
        Assertions.assertEquals(HttpStatus.OK, returned.getStatus());

    }

    @Test
    @DisplayName("user who are already on boarded with custom credentials: ")
    void googleSignInMethodForCustomLogger() {
        Mockito.when(userRepo.findByEmail(USER_MODEL.getEmail())).thenReturn(USER1);
        ApiResponse returned = userService.googleSignInMethod(USER_MODEL_G);
        Assertions.assertNotNull(returned);
        Assertions.assertEquals(HttpStatus.NOT_ACCEPTABLE, returned.getStatus());

    }


    @Test
    @DisplayName("On Board new user with google account")
    void googleSignInMethodForCustomLogIn() {
        Mockito.lenient().when(userRepo.findByEmail(USER_MODEL_G.getEmail())).thenReturn(null);
        Mockito.lenient().when(passwordEncoder.encode(USER_MODEL_G.getPassword())).thenReturn("PassordEncoded");
        Mockito.when(userDetailsSecService.loadUserByUsername(USER_MODEL_G.getEmail())).thenReturn(userDetails);
        Mockito.when(jwtUtils.generateToken(userDetails)).thenReturn("Token");
        ApiResponse returned = userService.googleSignInMethod(USER_MODEL_G);
        Assertions.assertNotNull(returned);
        Assertions.assertEquals(HttpStatus.OK, returned.getStatus());

    }


    @Test
    void randompass() throws Exception {
        Random random = SecureRandom.getInstanceStrong();
        String pass = "hello";
        String ans = userService.randomPass();
        Assertions.assertNotNull(ans);
    }


}
