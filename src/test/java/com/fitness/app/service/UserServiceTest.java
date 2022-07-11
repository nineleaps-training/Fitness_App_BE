package com.fitness.app.service;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fitness.app.componets.Components;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserModel;
import com.fitness.app.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    UserService userService;

    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    Components componets;

    UserClass userClass;
    UserModel userModel;

    @BeforeEach
    public void initcase() {
        userService=new UserService(userRepository,componets,passwordEncoder);
    }
    
    @Test
    void testGoogleSignInMethod() {

        userClass=new UserClass("pankaj.jain@nineleps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR", false, false, false);
        userModel=new UserModel("pankaj.jain@nineleaps.com","Pankaj Jain","8469492322","Hello@123","VENDOR",false);
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userClass);
        when(userRepository.save(userClass)).thenReturn(userClass);
        UserClass userClass2 = userService.googleSignInMethod(userModel);
        Assertions.assertEquals(userClass, userClass2);

    }

    @Test
    void testGoogleSignInMethodwithNull() {

        userClass=new UserClass(null,null,null, null, null, true, false, null);
        userModel=new UserModel();
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(null);
        when(userRepository.save(userClass)).thenReturn(userClass);
        UserClass userClass2 = userService.googleSignInMethod(userModel);
        Assertions.assertEquals(userClass, userClass2);

    }

    @Test
    void testGoogleSignInMethodwithNullCustom() {

        userClass=new UserClass(null,null,null, null, null, true, false, true);
        userModel=new UserModel();
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userClass);
        UserClass userClass2 = userService.googleSignInMethod(userModel);
        Assertions.assertEquals(null, userClass2);

    }

    @Test
    void testGoogleSignInMethodwithCustom() {

        userClass=new UserClass("pankaj.jain@nineleps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR", false, false, true);
        userModel=new UserModel("pankaj.jain@nineleaps.com","Pankaj Jain","8469492322","Hello@123","VENDOR",true);
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userClass);
        UserClass userClass2 = userService.googleSignInMethod(userModel);
        Assertions.assertEquals(null, userClass2);

    }

    @Test
    void testRandomPass() {
        String random="";
        String random2 = userService.randomPass();
        Assertions.assertEquals(random.getClass(), random2.getClass());      

    }

    @Test
    void testRegisterUser() {
        userClass=new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR", false, false, false);
        userModel=new UserModel("pankaj.jain@nineleaps.com","Pankaj Jain","8469492322","Hello@123","VENDOR",false);
        when(userRepository.save(any())).thenReturn(userClass);
        when(componets.otpBuilder()).thenReturn("hello");
        when(componets.sendOtpMessage(anyString(), anyString(), anyString())).thenReturn(200);
        UserClass userClass2 = userService.registerUser(userModel);
        Assertions.assertEquals(userClass.getEmail(), userClass2.getEmail());
    }

    @Test
    void testRegisterUserCode() {
        userClass=new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR", false, false, false);
        userModel=new UserModel("pankaj.jain@nineleaps.com","Pankaj Jain","8469492322","Hello@123","VENDOR",false);
        when(componets.otpBuilder()).thenReturn("hello");
        when(componets.sendOtpMessage(anyString(), anyString(), anyString())).thenReturn(404);
        UserClass userClass2 = userService.registerUser(userModel);
        Assertions.assertEquals(null, userClass2);
    }

    @Test
    void testVerifyUser() {
        Optional<UserClass> optional;
        optional=Optional.of(new UserClass("pankaj.jain@nineleps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR", false, false, false));
        userClass=new UserClass("pankaj.jain@nineleps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR", false, false, false);
        userModel=new UserModel("pankaj.jain@nineleaps.com","Pankaj Jain","8469492322","Hello@123","VENDOR",false);
        when(userRepository.findById(userClass.getEmail())).thenReturn(optional);
        when(userRepository.save(any())).thenReturn(userClass);
        // when(optional.isPresent()).thenReturn(true);
        // when(userClass!=null).thenReturn(true);
        UserClass userClass2 = userService.verifyUser(userClass.getEmail());
        Assertions.assertEquals(userClass.getEmail(), userClass2.getEmail());
    }

    @Test
    void testVerifyUserwithNoOptional() {
        Optional<UserClass> optional = Optional.empty();
        userClass=new UserClass();
        when(userRepository.findById(userClass.getEmail())).thenReturn(optional);
        UserClass userClass2 = userService.verifyUser(userClass.getEmail());
        Assertions.assertEquals(null, userClass2);
    }
}
