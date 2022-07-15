package com.fitness.app.service;

import com.fitness.app.componets.Components;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserModel;
import com.fitness.app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private Components components;

    @Autowired
    UserService userService;

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
}