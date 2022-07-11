package com.fitness.app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.auth.Authenticate;
import com.fitness.app.componets.Components;
import com.fitness.app.config.JwtUtils;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserModel;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.security.service.UserDetailsServiceImpl;
import com.fitness.app.service.UserService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtUtils jwtUtils;

    @Mock
    UserRepository userRepository;
    
    @Mock
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    Components sendMessage;
    
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    
    @BeforeEach
    public void setup()
    {
        this.mockMvc=MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testAuthenticateUser() throws Exception {

        UserDetails userDetails = null;
        UserClass userclass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", false, false, true);
        UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", true);
        Authentication authentication = null;
        String content=objectMapper.writeValueAsString(userModel);
        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userclass.getEmail(), userclass.getPassword()))).thenReturn(authentication);
        Mockito.when(userDetailsServiceImpl.loadUserByUsername(userclass.getEmail())).thenReturn(userDetails);
        Mockito.when(jwtUtils.generateToken(userDetails)).thenReturn("hello");
        Mockito.when(userRepository.findByEmail(userclass.getEmail())).thenReturn(userclass);
        mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/login/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void testAuthenticateUserAdmin() throws Exception {

        UserDetails userDetails = null;
        UserClass userclass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "ADMIN", false, false, true);
        UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "ADMIN", true);
        Authentication authentication = null;
        String content=objectMapper.writeValueAsString(userModel);
        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userclass.getEmail(), userclass.getPassword()))).thenReturn(authentication);
        Mockito.when(userDetailsServiceImpl.loadUserByUsername(userclass.getEmail())).thenReturn(userDetails);
        Mockito.when(jwtUtils.generateToken(userDetails)).thenReturn("hello");
        Mockito.when(userRepository.findByEmail(userclass.getEmail())).thenReturn(userclass);
        mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/login/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void testGoogleSignInUser() throws Exception {
        UserDetails userDetails = null;
        UserClass userclass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", false, false, true);
        UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", true);
        Authentication authentication = null;
        String content=objectMapper.writeValueAsString(userModel);
        Mockito.when(userService.randomPass()).thenReturn("Pankaj@123");
        Mockito.when(userService.googleSignInMethod(userModel)).thenReturn(userclass);
        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userclass.getEmail(), userclass.getPassword()))).thenReturn(authentication);
        Mockito.when(userDetailsServiceImpl.loadUserByUsername(userclass.getEmail())).thenReturn(userDetails);
        Mockito.when(jwtUtils.generateToken(userDetails)).thenReturn("hello");
        Mockito.when(userRepository.findByEmail(userclass.getEmail())).thenReturn(userclass);
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/google-sign-in/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void testGoogleSignInUserVendor() throws Exception {
        
        UserClass userclass = new UserClass();
        userclass.setEmail("pankaj.jain@nineleaps.com");
        userclass.setFullName("Pankaj Jain");
        userclass.setMobile("mobile");
        userclass.setPassword("Pankaj@123");
        userclass.setRole("VENDOR");
        userclass.setActivated(false);
        userclass.setLoggedin(false);
        userclass.setCustom(true);
        UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "VENDOR", true);
        String content=objectMapper.writeValueAsString(userModel);
        Mockito.when(userService.randomPass()).thenReturn("Pankaj@123");
        Mockito.when(userService.googleSignInMethod(userModel)).thenReturn(userclass);
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/google-sign-in/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
    }

    @Test
    void testGoogleSignInUserNull() throws Exception {
        
       
        UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "VENDOR", true);
        String content=objectMapper.writeValueAsString(userModel);
        Mockito.when(userService.randomPass()).thenReturn("Pankaj@123");
        Mockito.when(userService.googleSignInMethod(userModel)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/google-sign-in/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
    }



    @Test
    void testGoogleSignInVendor() throws Exception {

        UserDetails userDetails = null;
        UserClass userclass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "VENDOR", false, false, true);
        UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "VENDOR", true);
        Authentication authentication = null;
        String content=objectMapper.writeValueAsString(userModel);
        Mockito.when(userService.randomPass()).thenReturn("Pankaj@123");
        Mockito.when(userService.googleSignInMethod(userModel)).thenReturn(userclass);
        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userclass.getEmail(), userclass.getPassword()))).thenReturn(authentication);
        Mockito.when(userDetailsServiceImpl.loadUserByUsername(userclass.getEmail())).thenReturn(userDetails);
        Mockito.when(jwtUtils.generateToken(userDetails)).thenReturn("hello");
        Mockito.when(userRepository.findByEmail(userclass.getEmail())).thenReturn(userclass);
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/google-sign-in/vendor").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void testGoogleSignInVendorNull() throws Exception {

        UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "VENDOR", true);
        String content=objectMapper.writeValueAsString(userModel);
        Mockito.when(userService.randomPass()).thenReturn("Pankaj@123");
        Mockito.when(userService.googleSignInMethod(userModel)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/google-sign-in/vendor").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void testGoogleSignInVendorUser() throws Exception {

       
        UserClass userclass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", false, false, true);
        UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", true);
        String content=objectMapper.writeValueAsString(userModel);
        Mockito.when(userService.randomPass()).thenReturn("Pankaj@123");
        Mockito.when(userService.googleSignInMethod(userModel)).thenReturn(userclass);
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/google-sign-in/vendor").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void testGoogleSignInNull() throws Exception {

        
        UserClass userclass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", false, false, true);
        UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", true);
        String content=objectMapper.writeValueAsString(userModel);
        Mockito.when(userService.randomPass()).thenReturn("Pankaj@123");
        Mockito.when(userService.googleSignInMethod(userModel)).thenReturn(userclass);
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/google-sign-in/vendor").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void testRegisterUser() throws Exception {

        
        UserClass userclass = new UserClass();
        userclass.setEmail("pankaj.jain@nineleaps.com");
        userclass.setFullName("Pankaj Jain");
        userclass.setMobile("mobile");
        userclass.setPassword("Pankaj@123");
        userclass.setRole("USER");
        userclass.setActivated(false);
        userclass.setLoggedin(false);
        userclass.setCustom(false);
        UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", false);
        String content=objectMapper.writeValueAsString(userModel);
        Mockito.when(userRepository.findByEmail(userclass.getEmail())).thenReturn(userclass);
        Mockito.when(sendMessage.otpBuilder()).thenReturn("1234");
        Mockito.when(sendMessage.sendOtpMessage("hello ", "1234",userclass.getMobile())).thenReturn(200);
        mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/register/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isCreated());

    }

    @Test
    void testRegisterUserCustom() throws Exception {
        
        UserClass userclass = new UserClass();
        userclass.setEmail("pankaj.jain@nineleaps.com");
        userclass.setFullName("Pankaj Jain");
        userclass.setMobile("mobile");
        userclass.setPassword("Pankaj@123");
        userclass.setRole("USER");
        userclass.setActivated(false);
        userclass.setLoggedin(false);
        userclass.setCustom(false);
        UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", false);
        String content=objectMapper.writeValueAsString(userModel);
        Mockito.when(userRepository.findByEmail(userclass.getEmail())).thenReturn(userclass);
        Mockito.when(sendMessage.otpBuilder()).thenReturn("1234");
        Mockito.when(sendMessage.sendOtpMessage("hello ", "1234",userclass.getMobile())).thenReturn(404);
        mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/register/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isCreated());

    }

    @Test
    void testRegisterUserCustomTrue() throws Exception {
        
        UserClass userclass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", false, false, true);
        UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", true);
        String content=objectMapper.writeValueAsString(userModel);
        Mockito.when(userRepository.findByEmail(userclass.getEmail())).thenReturn(userclass);
        Mockito.when(sendMessage.otpBuilder()).thenReturn("1234");
        Mockito.when(sendMessage.sendOtpMessage("hello ", "1234",userclass.getMobile())).thenReturn(404);
        mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/register/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isCreated());

    }

    @Test
    void testRegisterUserCustomTrueCode() throws Exception {
        
        UserClass userclass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "Pankaj Jain", "Pankaj@123", "USER", false, false, true);
        UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "Pankaj Jain", "Pankaj@123", "USER", true);
        String content=objectMapper.writeValueAsString(userModel);
        Mockito.when(userRepository.findByEmail(userclass.getEmail())).thenReturn(userclass);
        Mockito.when(sendMessage.otpBuilder()).thenReturn("1234");
        Mockito.when(sendMessage.sendOtpMessage("hello ", "1234",userclass.getMobile())).thenReturn(200);
        mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/register/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isCreated());

    }

    @Test
    void testRegisterUserCustomTrueActivated() throws Exception {
        
        UserClass userclass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", true, false, true);
        UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", true);
        String content=objectMapper.writeValueAsString(userModel);
        Mockito.when(userRepository.findByEmail(userclass.getEmail())).thenReturn(userclass);
        mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/register/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isCreated());

    }

    @Test
    void testRegisterUserNull() throws Exception {
        
        UserClass userclass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", true, false, true);
        UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", true);
        String content=objectMapper.writeValueAsString(userModel);
        Mockito.when(userRepository.findByEmail(userclass.getEmail())).thenReturn(null);
        Mockito.when(sendMessage.otpBuilder()).thenReturn("1234");
        Mockito.when(sendMessage.sendOtpMessage("hello ", "1234",userclass.getMobile())).thenReturn(200);
        mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/register/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isCreated());

    }

    @Test
    void testRegisterUserNullCustom() throws Exception {
        
        UserClass userclass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", true, false, false);
        UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", false);
        String content=objectMapper.writeValueAsString(userModel);
        Mockito.when(userRepository.findByEmail(userclass.getEmail())).thenReturn(null);
        Mockito.when(sendMessage.otpBuilder()).thenReturn("1234");
        Mockito.when(sendMessage.sendOtpMessage("hello ", "1234",userclass.getMobile())).thenReturn(200);
        mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/register/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isCreated());

    }

    @Test
    void testVerifyTheUser() throws Exception {

        UserDetails userDetails = null;
        UserClass userclass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", false, false, true);
        UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", true);
        Authentication authentication = null;
        Authenticate auth = new Authenticate("pankaj.jain@nineleaps.com", "Pankaj@123");
        String content=objectMapper.writeValueAsString(userModel);
        Mockito.when(userService.verifyUser(auth.getEmail())).thenReturn(userclass);
        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userclass.getEmail(), userclass.getPassword()))).thenReturn(authentication);
        Mockito.when(userDetailsServiceImpl.loadUserByUsername(userclass.getEmail())).thenReturn(userDetails);
        Mockito.when(jwtUtils.generateToken(userDetails)).thenReturn("hello");
        Mockito.when(userRepository.findByEmail(userclass.getEmail())).thenReturn(userclass);
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/verify/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void testVerifyTheUserNull() throws Exception {

        UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER", true);
        Authenticate auth = new Authenticate("pankaj.jain@nineleaps.com", "Pankaj@123");
        UserClass userClass2 = null;
        String content=objectMapper.writeValueAsString(userModel);
        Mockito.when(userService.verifyUser(auth.getEmail())).thenReturn(userClass2);
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/verify/user").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }
}
