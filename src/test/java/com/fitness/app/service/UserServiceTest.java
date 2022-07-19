package com.fitness.app.service;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fitness.app.components.Components;
import com.fitness.app.config.JwtUtils;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.SignUpResponceModel;
import com.fitness.app.model.UserModel;
import com.fitness.app.repository.UserRepo;
import com.fitness.app.security.service.UserDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepo userRepository;
    
    @InjectMocks
    UserService userService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    Components componets;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    UserDetails userDetails;

    @Mock
    JwtUtils jwtUtils;

    @Mock
    UserDetailsServiceImpl userDetailsServiceImpl;

    UserClass userClass;
    UserModel userModel;

    @Test
    @DisplayName("Testing of logging in by Google")
    void testGoogleSignInMethod() {

        userClass = new UserClass("pankaj.jain@nineleps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR", false,
                false, false);
        userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR",
                false);
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userClass);
        when(userRepository.save(userClass)).thenReturn(userClass);
        UserClass userClass2 = userService.googleSignInMethod(userModel);
        Assertions.assertEquals(userClass, userClass2);

    }

    @Test
    @DisplayName("Testing of logging in by Google")
    void testGoogleSignInMethodwithNull() {

        userClass = new UserClass(null, null, null, null, null, true, false, null);
        userModel = new UserModel();
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(null);
        when(userRepository.save(userClass)).thenReturn(userClass);
        UserClass userClass2 = userService.googleSignInMethod(userModel);
        Assertions.assertEquals(userClass, userClass2);

    }

    @Test
    @DisplayName("Testing of logging in by Google")
    void testGoogleSignInMethodwithNullCustom() {

        userClass = new UserClass(null, null, null, null, null, true, false, true);
        userModel = new UserModel();
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userClass);
        UserClass userClass2 = userService.googleSignInMethod(userModel);
        Assertions.assertEquals(null, userClass2);

    }

    @Test
    void testRegisterNewUser()
    {
        userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", null, "VENDOR",
                false, false, false);
        userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR",
                false);
        when(userRepository.save(any())).thenReturn(userClass);
        when(componets.otpBuilder()).thenReturn("hello");
        when(componets.sendOtpMessage(anyString(), anyString(), anyString())).thenReturn(200);
        SignUpResponceModel signUpResponceModel = userService.registerNewUser(userModel);
        SignUpResponceModel signUpResponceModel2 = new SignUpResponceModel(userClass, "hello");
        Assertions.assertEquals(signUpResponceModel, signUpResponceModel2);
    }

    @Test
    void testRegisterNewUserIf()
    {
        userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", null, "VENDOR",
                false, false, false);
        userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR",
                false);
        when(componets.sendOtpMessage("hello ", null, "8469492322")).thenReturn(200);
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userClass);
        SignUpResponceModel signUpResponceModel2 = userService.registerNewUser(userModel);
        SignUpResponceModel signUpResponceModel = new SignUpResponceModel(userClass, null);
        Assertions.assertEquals(signUpResponceModel, signUpResponceModel2);
    }

    @Test
    void testRegisterNewUserIElse()
    {
        userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", null, "VENDOR",
                false, false, true);
        userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR",
                false);
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userClass);
        when(componets.otpBuilder()).thenReturn("hello");
        when(componets.sendOtpMessage(anyString(), anyString(), anyString())).thenReturn(404);
        SignUpResponceModel signUpResponceModel = userService.registerNewUser(userModel);
        SignUpResponceModel signUpResponceModel2 = new SignUpResponceModel(null, "Something went wrong");
        Assertions.assertEquals(signUpResponceModel, signUpResponceModel2);
    }

    @Test
    void testRegisterNewUserIfCode()
    {
        userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", null, "VENDOR",
                false, false, true);
        userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR",
                false);
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userClass);
        when(componets.otpBuilder()).thenReturn("hello");
        when(componets.sendOtpMessage(anyString(), anyString(), anyString())).thenReturn(200);
        SignUpResponceModel signUpResponceModel = userService.registerNewUser(userModel);
        SignUpResponceModel signUpResponceModel2 = new SignUpResponceModel(userClass, "hello");
        Assertions.assertEquals(signUpResponceModel, signUpResponceModel2);
    }

    @Test
    void testRegisterNewUserInUse()
    {
        userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", null, "VENDOR",
                true, false, true);
        userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR",
                false);
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userClass);
        SignUpResponceModel signUpResponceModel = userService.registerNewUser(userModel);
        SignUpResponceModel signUpResponceModel2 = new SignUpResponceModel(userClass, "Already In Use!");
        Assertions.assertEquals(signUpResponceModel, signUpResponceModel2);
    }

    @Test
    void testRegisterNewUserElse()
    {
        userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", null, "VENDOR",
                false, false, false);
        userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR",
                false);
        when(componets.otpBuilder()).thenReturn("hello");
        when(componets.sendOtpMessage(anyString(), anyString(), anyString())).thenReturn(404);
        SignUpResponceModel signUpResponceModel = userService.registerNewUser(userModel);
        SignUpResponceModel signUpResponceModel2 = new SignUpResponceModel(null, "Something went wrong");
        Assertions.assertEquals(signUpResponceModel, signUpResponceModel2);
    }

    @Test
    void testLogInFunctionality()
    {
        userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", null, "VENDOR",
                false, false, false);
        userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR",
                false);
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userClass);
        ResponseEntity<SignUpResponceModel> responseEntity = userService.logInFunctionality(userClass.getEmail(),userClass.getPassword());
        ResponseEntity<SignUpResponceModel> responseEntity2 = ResponseEntity.ok(new SignUpResponceModel(userClass, null));
        Assertions.assertEquals(responseEntity, responseEntity2);
    }

    @Test
    void testLogInFunctionalityElse()
    {
        userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", null, "ADMIN",
                false, false, false);
        userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Hello@123", "ADMIN",
                false);
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userClass);
        ResponseEntity<SignUpResponceModel> responseEntity = userService.logInFunctionality(userClass.getEmail(),userClass.getPassword());
        ResponseEntity<SignUpResponceModel> responseEntity2 = ResponseEntity.ok(new SignUpResponceModel(null, null));
        Assertions.assertEquals(responseEntity, responseEntity2);
    }

    @Test
    @DisplayName("Testing of logging in by Google")
    void testGoogleSignInMethodwithCustom() {

        userClass = new UserClass("pankaj.jain@nineleps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR", false,
                false, true);
        userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR",
                true);
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userClass);
        UserClass userClass2 = userService.googleSignInMethod(userModel);
        Assertions.assertEquals(null, userClass2);

    }

    @Test
    @DisplayName("Testing of random pass function")
    void testRandomPass() {
        String random = "";
        String random2 = userService.randomPass();
        Assertions.assertEquals(random.getClass(), random2.getClass());

    }

    @Test
    @DisplayName("Testing of registering the new user")
    void testRegisterUser() {
        userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR",
                false, false, false);
        userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR",
                false);
        when(userRepository.save(any())).thenReturn(userClass);
        when(componets.otpBuilder()).thenReturn("hello");
        when(componets.sendOtpMessage(anyString(), anyString(), anyString())).thenReturn(200);
        UserClass userClass2 = userService.registerUser(userModel);
        Assertions.assertEquals(userClass.getEmail(), userClass2.getEmail());
    }

    @Test
    @DisplayName("Testing of registering the new user")
    void testRegisterUserCode() {
        userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR",
                false, false, false);
        userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR",
                false);
        when(componets.otpBuilder()).thenReturn("hello");
        when(componets.sendOtpMessage(anyString(), anyString(), anyString())).thenReturn(404);
        UserClass userClass2 = userService.registerUser(userModel);
        Assertions.assertEquals(null, userClass2);
    }

    @Test
    @DisplayName("Testing of verifying the user")
    void testVerifyUser() {
        Optional<UserClass> optional;
        optional = Optional.of(new UserClass("pankaj.jain@nineleps.com", "Pankaj Jain", "8469492322", "Hello@123",
                "VENDOR", false, false, false));
        userClass = new UserClass("pankaj.jain@nineleps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR", false,
                false, false);
        userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR",
                false);
        when(userRepository.findById(userClass.getEmail())).thenReturn(optional);
        when(userRepository.save(any())).thenReturn(userClass);
        // when(optional.isPresent()).thenReturn(true);
        // when(userClass!=null).thenReturn(true);
        UserClass userClass2 = userService.verifyUser(userClass.getEmail());
        Assertions.assertEquals(userClass.getEmail(), userClass2.getEmail());
    }

    @Test
    @DisplayName("Testing of verifying the user")
    void testVerifyUserwithNoOptional() {
        Optional<UserClass> optional = Optional.empty();
        userClass = new UserClass();
        when(userRepository.findById(userClass.getEmail())).thenReturn(optional);
        UserClass userClass2 = userService.verifyUser(userClass.getEmail());
        Assertions.assertEquals(null, userClass2);
    }
}
