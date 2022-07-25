package com.fitness.app.service;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.componets.Components;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.SignUpResponse;
import com.fitness.app.model.UserModel;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.security.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private Components components;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    UserDetails userDetails;

    @Autowired
    UserService userService;

    @Test
    void registerNewUser() {
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true, false, true);

        UserModel userModel = new UserModel("priyanshi.chaturvei@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true);

        String signUpResponse = new SignUpResponse(userClass, "Already In Use!").getMessage();

        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userClass);
        when(components.otpBuilder()).thenReturn("hello");
        when(components.sendOtpMessage(anyString(), anyString(), anyString())).thenReturn(200);

        String actual = userService.registerNewUser(userModel).getMessage();
        assertEquals(actual, signUpResponse);
    }

    @Test
    void registerNewUserIfUserIsNotActivated() {
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", false, false, true);

        UserModel userModel = new UserModel("priyanshi.chaturvei@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true);

        String signUpResponse = new SignUpResponse(userClass, "hello").getMessage();

        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userClass);
        when(components.otpBuilder()).thenReturn("hello");
        when(components.sendOtpMessage(anyString(), anyString(), anyString())).thenReturn(200);

        String actual = userService.registerNewUser(userModel).getMessage();
        assertEquals(actual, signUpResponse);
    }

    @Test
    void registerNewUserIfStatusCodeIsNot200() {
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", false, false, true);

        UserModel userModel = new UserModel("priyanshi.chaturvei@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true);

        String signUpResponse = new SignUpResponse(userClass, "Something went wrong").getMessage();

        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userClass);
        when(components.otpBuilder()).thenReturn("hello");
        when(components.sendOtpMessage(anyString(), anyString(), anyString())).thenReturn(400);

        String actual = userService.registerNewUser(userModel).getMessage();
        assertEquals(actual, signUpResponse);
    }

    @Test
    void registerNewUserIfUserIsNull() {
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true, false, true);

        UserModel userModel = new UserModel("priyanshi.chaturvei@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true);

        String signUpResponse = new SignUpResponse(userClass, "hello").getMessage();

        when(components.otpBuilder()).thenReturn("hello");
        when(components.sendOtpMessage(anyString(), anyString(), anyString())).thenReturn(200);

        String actual = userService.registerNewUser(userModel).getMessage();
        assertEquals(actual, signUpResponse);
    }

    @Test
    void registerNewUserIfUserIsNullAndStatusCodeIsNot200() {
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true, false, true);

        UserModel userModel = new UserModel("priyanshi.chaturvei@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true);

        String signUpResponse = new SignUpResponse(userClass, "Something went wrong").getMessage();

        when(components.otpBuilder()).thenReturn("hello");
        when(components.sendOtpMessage(anyString(), anyString(), anyString())).thenReturn(400);

        String actual = userService.registerNewUser(userModel).getMessage();
        assertEquals(actual, signUpResponse);
    }

    @Test
    void registerUserIfStatusCodeIs400() {

        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", false, false, true);

        UserModel userModel = new UserModel("priyanshi.chaturvei@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true);

        String otp = "1234";

        when(components.sendOtpMessage("hello ", otp, userModel.getMobile())).thenReturn(400);
        when(userRepository.save(userClass)).thenReturn(userClass);

        UserClass actual = userService.registerUser(userModel);
        assertNull(actual);
    }

    @Test
    void registerUserIfStatusCodeIs200() {

        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", false, false, true);

        UserModel userModel = new UserModel("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true);

        String otp = "1234";
        when(components.otpBuilder()).thenReturn(otp);
        when(components.sendOtpMessage("hello ", otp, userModel.getMobile())).thenReturn(200);

        String actual = userService.registerUser(userModel).getEmail();
        assertEquals(userClass.getEmail(), actual);
    }

    @Test
    void verifyUser() {
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true, true, true);

        when(userRepository.findById(userClass.getEmail())).thenReturn(Optional.of(userClass));

        UserClass actual = userService.verifyUser(userClass.getEmail());
        assertEquals(userClass, actual);

    }

    @Test
    void doNotVerifyUserIfUserIsNull() {
        UserClass userClass = new UserClass();
        userClass.setActivated(true);
        Optional<UserClass> userClassOptional = Optional.empty();

        when(userRepository.findById(userClass.getEmail())).thenReturn(userClassOptional);

        UserClass actual = userService.verifyUser(userClass.getEmail());
        assertEquals(userClass, actual);

    }

    @Test
    void loginUser() {
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true, true, true);

        when(userRepository.findById(userClass.getEmail())).thenReturn(Optional.of(userClass));

        userService.loginUser(userClass.getEmail());
        assertNotNull(userClass);

    }

    @Test
    void loginUserIfOptionalIsNotPresent() {
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true, true, true);
        Optional<UserClass> userClassOptional = Optional.empty();

        when(userRepository.findById(userClass.getEmail())).thenReturn(userClassOptional);

        userService.loginUser(userClass.getEmail());
        assertNotNull(userClass);

    }

    @Test
    void signInWithGoogleIfUserIsNotNullAndCustomIsFalse() {
        UserModel userModel = new UserModel("priyanshi.chaturvedi@nineleaps.com", "Priyanshi", "9685903290", "password", "Enthusiast", false);

        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "password", "Enthusiast", true, true, false);

        when(userRepository.findByEmail(userClass.getEmail())).thenReturn(userClass);

        UserClass actual = userService.googleSignInMethod(userModel);
        assertEquals(userClass, actual);
    }

    @Test
    void doNotSignInWithGoogleIfUserIsNotNullAndCustomIsTrue() {
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", false, false, true);

        UserModel userModel = new UserModel("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true);

        when(userRepository.findByEmail(userClass.getEmail())).thenReturn(userClass);
        when(userRepository.save(userClass)).thenReturn(userClass);

        UserClass actual = userService.googleSignInMethod(userModel);
        assertNull(actual);
    }

    @Test
    void signInWithGoogleIfUserIsNullAndCustomIsFalse() {

        UserModel userModel = new UserModel("priyanshi.chaturvedi@nineleaps.com", "Priyanshi", "9685903290", "password", "Enthusiast", false);

        UserClass userClass = new UserClass();

        when(userRepository.findByEmail(userClass.getEmail())).thenReturn(userClass);

        UserClass newUser = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "password", "Enthusiast", true, false, false);

        Boolean actual = userService.googleSignInMethod(userModel).getCustom();
        assertEquals(newUser.getCustom(), actual);
    }

    @Test
    void signInWithGoogleIfUserIsNullAndCustomIsTrue() {

        UserModel userModel = new UserModel("priyanshi.chaturvedi@nineleaps.com", "Priyanshi", "9685903290", "password", "Enthusiast", true);
        UserClass userClass = new UserClass();

        when(userRepository.findByEmail(userClass.getEmail())).thenReturn(userClass);

        UserClass newUser = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "password", "Enthusiast", true, false, true);

        Boolean actual = userService.googleSignInMethod(userModel).getCustom();
        assertEquals(newUser.getCustom(), actual);
    }

    @Test
    void randomPass() {

        String generatedString = userService.randomPass();
        assertNotNull(generatedString);
    }

    @Test
    void loginFunctionality() {
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", false, false, true);

        UserModel userModel = new UserModel("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true);

        Authenticate authenticate = new Authenticate("priyanshi.chaturvedi@nineleaps.com", "12345");
        HttpStatus responseEntity = ResponseEntity.ok(new SignUpResponse(userClass, null)).getStatusCode();

        when(authenticationManager.authenticate(null)).thenReturn(null);
        when(userDetailsService.loadUserByUsername(authenticate.getEmail())).thenReturn(userDetails);
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userClass);

        HttpStatus actual = userService.logInFunctionality(userClass.getEmail(),userClass.getPassword()).getStatusCode();

        assertEquals(actual, responseEntity);
    }

    @Test
    void loginFunctionalityIfRoleIsAdmin() {
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "ADMIN", false, false, true);

        UserModel userModel = new UserModel("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "ADMIN", true);

        Authenticate authenticate = new Authenticate("priyanshi.chaturvedi@nineleaps.com", "12345");
        ResponseEntity<SignUpResponse> responseEntity = ResponseEntity.ok(new SignUpResponse(null, null));

        when(authenticationManager.authenticate(null)).thenReturn(null);
        when(userDetailsService.loadUserByUsername(authenticate.getEmail())).thenReturn(userDetails);
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userClass);

        ResponseEntity<SignUpResponse> actual = userService.logInFunctionality(userClass.getEmail(),userClass.getPassword());
        assertEquals(actual, responseEntity);
    }
}