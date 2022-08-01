package com.fitness.app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.auth.Authenticate;
import com.fitness.app.components.Components;
import com.fitness.app.config.JwtUtils;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserModel;
import com.fitness.app.repository.UserRepo;
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
        UserRepo userRepository;

        @Mock
        UserDetailsServiceImpl userDetailsServiceImpl;

        @Mock
        Components sendMessage;

        @Mock
        PasswordEncoder passwordEncoder;

        MockMvc mockMvc;

        ObjectMapper objectMapper = new ObjectMapper();

        UserClass userClass;

        UserDetails userDetails;

        UserModel userModel;

        UserClass uClass;

        @BeforeEach
        public void setup() {
                this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        }

        @Test
        @DisplayName("Testing of authenticating the user")
        void testAuthenticateUser() throws Exception {

                userDetails = null;
                userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER",
                                false, false, true);
                userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER",
                                true);
                String content = objectMapper.writeValueAsString(userModel);
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/v1/user/login/user").contentType(MediaType.APPLICATION_JSON).content(content))
                                .andExpect(status().isCreated());

        }

        @Test
        @DisplayName("Testing of authenticating the admin")
        void testAuthenticateUserAdmin() throws Exception {

                UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123",
                                "ADMIN",
                                true);
                String content = objectMapper.writeValueAsString(userModel);
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/v1/user/login/user").contentType(MediaType.APPLICATION_JSON).content(content))
                                .andExpect(status().isCreated());

        }

        @Test
        @DisplayName("Testing of logging in by Google for User")
        void testGoogleSignInUser() throws Exception {

                userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER",
                                true);
                String content = objectMapper.writeValueAsString(userModel);
                mockMvc.perform(MockMvcRequestBuilders
                                .put("/v1/user/googleSignIn/user").contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andExpect(status().isOk());

        }

        @Test
        @DisplayName("Testing of logging in by Google for Vendor")
        void testGoogleSignInVendor() throws Exception {
                userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER",
                                false, false, true);
                userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER",
                                true);
                String content = objectMapper.writeValueAsString(userModel);
                mockMvc.perform(MockMvcRequestBuilders
                                .put("/v1/user/googleSignIn/vendor").contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andExpect(status().isOk());

        }

        @Test
        @DisplayName("Testing of logging in by Google for Vendor")
        void testGoogleSignInVendorElseIf() throws Exception {
                uClass = new UserClass();
                uClass.setEmail("pankaj.jain@nineleaps.com");
                uClass.setFullName("Pankaj Jain");
                uClass.setMobile("mobile");
                uClass.setPassword("Pankaj@123");
                uClass.setRole("USER");
                uClass.setActivated(false);
                uClass.setLoggedIn(false);
                uClass.setCustom(false);
                userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123",
                                "USER", false);
                Mockito.when(userService.googleSignInMethod(userModel)).thenReturn(uClass);
                Mockito.when(userService.randomPass()).thenReturn("Pankaj@123");
                String content = objectMapper.writeValueAsString(userModel);
                mockMvc.perform(MockMvcRequestBuilders
                                .put("/v1/user/googleSignIn/vendor").contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andExpect(status().isOk());

        }

        @Test
        @DisplayName("Testing of logging in by Google for Vendor")
        void testGoogleSignInVendorElse() throws Exception {
                uClass = new UserClass();
                uClass.setEmail("pankaj.jain@nineleaps.com");
                uClass.setFullName("Pankaj Jain");
                uClass.setMobile("mobile");
                uClass.setPassword("Pankaj@123");
                uClass.setRole("VENDOR");
                uClass.setActivated(false);
                uClass.setLoggedIn(false);
                uClass.setCustom(false);
                UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123",
                                "VENDOR", false);
                Mockito.when(userService.googleSignInMethod(userModel)).thenReturn(uClass);
                Mockito.when(userService.randomPass()).thenReturn("Pankaj@123");
                String content = objectMapper.writeValueAsString(userModel);
                mockMvc.perform(MockMvcRequestBuilders
                                .put("/v1/user/googleSignIn/vendor").contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andExpect(status().isOk());

        }

        @Test
        @DisplayName("Testing of logging in by Google for Vendor")
        void testGoogleSignInUserVendor() throws Exception {

                UserClass uClass = new UserClass();
                uClass.setEmail("pankaj.jain@nineleaps.com");
                uClass.setFullName("Pankaj Jain");
                uClass.setMobile("mobile");
                uClass.setPassword("Pankaj@123");
                uClass.setRole("VENDOR");
                uClass.setActivated(false);
                uClass.setLoggedIn(false);
                uClass.setCustom(true);
                UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123",
                                "VENDOR", true);
                String content = objectMapper.writeValueAsString(userModel);
                mockMvc.perform(MockMvcRequestBuilders
                                .put("/v1/user/googleSignIn/user").contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Testing of logging in by Google for Vendor")
        void testGoogleSignInUserElseIf() throws Exception {
                uClass = new UserClass();
                uClass.setEmail("pankaj.jain@nineleaps.com");
                uClass.setFullName("Pankaj Jain");
                uClass.setMobile("mobile");
                uClass.setPassword("Pankaj@123");
                uClass.setRole("USER");
                uClass.setActivated(false);
                uClass.setLoggedIn(false);
                uClass.setCustom(false);
                UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123",
                                "USER", false);
                Mockito.when(userService.googleSignInMethod(userModel)).thenReturn(uClass);
                Mockito.when(userService.randomPass()).thenReturn("Pankaj@123");
                String content = objectMapper.writeValueAsString(userModel);
                mockMvc.perform(MockMvcRequestBuilders
                                .put("/v1/user/googleSignIn/user").contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andExpect(status().isOk());

        }

        @Test
        @DisplayName("Testing of logging in by Google for Vendor")
        void testGoogleSignInUserElse() throws Exception {
                UserClass uClass = new UserClass();
                uClass.setEmail("pankaj.jain@nineleaps.com");
                uClass.setFullName("Pankaj Jain");
                uClass.setMobile("mobile");
                uClass.setPassword("Pankaj@123");
                uClass.setRole("USER");
                uClass.setActivated(false);
                uClass.setLoggedIn(false);
                uClass.setCustom(false);
                UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123",
                                "USER", false);
                Mockito.when(userService.googleSignInMethod(userModel)).thenReturn(uClass);
                Mockito.when(userService.randomPass()).thenReturn("Pankaj@123");
                String content = objectMapper.writeValueAsString(userModel);
                mockMvc.perform(MockMvcRequestBuilders
                                .put("/v1/user/googleSignIn/user").contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andExpect(status().isOk());

        }

        @Test
        @DisplayName("Testing of logging in by Google for Vendor")
        void testGoogleSignInVendorUser() throws Exception {
                UserClass uClass = new UserClass();
                uClass.setEmail("pankaj.jain@nineleaps.com");
                uClass.setFullName("Pankaj Jain");
                uClass.setMobile("mobile");
                uClass.setPassword("Pankaj@123");
                uClass.setRole("VENDOR");
                uClass.setActivated(false);
                uClass.setLoggedIn(false);
                uClass.setCustom(false);
                UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123",
                                "VENDOR", false);
                Mockito.when(userService.googleSignInMethod(userModel)).thenReturn(uClass);
                Mockito.when(userService.randomPass()).thenReturn("Pankaj@123");
                String content = objectMapper.writeValueAsString(userModel);
                mockMvc.perform(MockMvcRequestBuilders
                                .put("/v1/user/googleSignIn/user").contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andExpect(status().isOk());

        }

        @Test
        @DisplayName("Testing of registering the new user")
        void testRegisterUser() throws Exception {

                UserClass uClass = new UserClass();
                uClass.setEmail("pankaj.jain@nineleaps.com");
                uClass.setFullName("Pankaj Jain");
                uClass.setMobile("mobile");
                uClass.setPassword("Pankaj@123");
                uClass.setRole("USER");
                uClass.setActivated(false);
                uClass.setLoggedIn(false);
                uClass.setCustom(false);
                UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123",
                                "USER",
                                false);
                String content = objectMapper.writeValueAsString(userModel);
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/v1/user/register/user").contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andExpect(status().isCreated());

        }

        @Test
        @DisplayName("Testing of user registration when exception is thrown")
        void testRegisterUserException() throws Exception {

                UserClass uClass = new UserClass();
                uClass.setEmail("pankaj.jain@nineleaps.com");
                uClass.setFullName("Pankaj Jain");
                uClass.setMobile("mobile");
                uClass.setPassword("Pankaj@123");
                uClass.setRole("USER");
                uClass.setActivated(false);
                uClass.setLoggedIn(false);
                uClass.setCustom(false);
                UserModel userModel = new UserModel("pankaj", "Pankaj Jain", "mobile", "Pankaj@123", "USER", false);
                String content = objectMapper.writeValueAsString(userModel);
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/v1/user/register/user").contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andExpect(status().isBadRequest());

        }

        @Test
        @DisplayName("Testing of registering the user with custom registration")
        void testRegisterUserCustom() throws Exception {

                UserClass uClass = new UserClass();
                uClass.setEmail("pankaj.jain@nineleaps.com");
                uClass.setFullName("Pankaj Jain");
                uClass.setMobile("mobile");
                uClass.setPassword("Pankaj@123");
                uClass.setRole("USER");
                uClass.setActivated(false);
                uClass.setLoggedIn(false);
                uClass.setCustom(false);
                UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123",
                                "USER",
                                false);
                String content = objectMapper.writeValueAsString(userModel);
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/v1/user/register/user").contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andExpect(status().isCreated());

        }

        @Test
        @DisplayName("Testing of registering the user when custom is true")
        void testRegisterUserCustomTrue() throws Exception {

                UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123",
                                "USER",
                                true);
                String content = objectMapper.writeValueAsString(userModel);
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/v1/user/register/user").contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andExpect(status().isCreated());

        }

        @Test
        @DisplayName("Testing of registering the user when code is returned")
        void testRegisterUserCustomTrueCode() throws Exception {

                UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "Pankaj Jain",
                                "Pankaj@123",
                                "USER", true);
                String content = objectMapper.writeValueAsString(userModel);
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/v1/user/register/user").contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andExpect(status().isCreated());

        }

        @Test
        @DisplayName("Testing of registering the user when user is activated")
        void testRegisterUserCustomTrueActivated() throws Exception {

                UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123",
                                "USER",
                                true);
                String content = objectMapper.writeValueAsString(userModel);
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/v1/user/register/user").contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andExpect(status().isCreated());

        }

        @Test
        @DisplayName("Testing of registering the user with null values")
        void testRegisterUserNull() throws Exception {

                UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123",
                                "USER",
                                true);
                String content = objectMapper.writeValueAsString(userModel);
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/v1/user/register/user").contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andExpect(status().isCreated());

        }

        @Test
        @DisplayName("Testing of registering the custom user")
        void testRegisterUserNullCustom() throws Exception {

                UserModel userModel = new UserModel("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123",
                                "USER",
                                false);
                String content = objectMapper.writeValueAsString(userModel);
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/v1/user/register/user").contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                                .andExpect(status().isCreated());

        }

        @Test
        @DisplayName("Testing of verifying the user")
        void testVerifyTheUser() throws Exception {

                userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj Jain", "mobile", "Pankaj@123", "USER",
                                false, false, true);
                Authenticate authenticate = new Authenticate("pankaj.jain@nineleaps.com", "Pankaj@123");
                String content = objectMapper.writeValueAsString(authenticate);
                Mockito.when(userService.verifyUser(authenticate.getEmail())).thenReturn(userClass);
                mockMvc.perform(MockMvcRequestBuilders
                                .put("/v1/user/verify/user").contentType(MediaType.APPLICATION_JSON).content(content))
                                .andExpect(status().isOk());

        }

        @Test
        @DisplayName("Testing of verifying the user with null values")
        void testVerifyTheUserNull() throws Exception {

                Authenticate authenticate = new Authenticate("pankaj.jain@nineleaps.com", "Pankaj@123");
                String content = objectMapper.writeValueAsString(authenticate);
                mockMvc.perform(MockMvcRequestBuilders
                                .put("/v1/user/verify/user").contentType(MediaType.APPLICATION_JSON).content(content))
                                .andExpect(status().isOk());

        }
}
