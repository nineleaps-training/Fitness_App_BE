package com.fitness.app.service;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.UserDetails;
import com.fitness.app.model.UserDetailsRequestModel;
import com.fitness.app.repository.UserDetailsRepo;
import com.fitness.app.repository.UserRepo;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {

    @Mock
    UserRepo userRepository;

    @Mock
    UserDetailsRepo userDetailsRepository;

    UserDetailsService userDetailsService;

    long l = 1234;

    MockMvc mockMvc;
    UserDetailsRequestModel userDetailsRequestModel;
    UserClass userClass;
    UserDetails userDetails;

    @BeforeEach
    public void initcase() {
        userDetailsService = new UserDetailsService(userRepository, userDetailsRepository);
    }

    @Test
    @DisplayName("Testing of adding the user details")
    void addUserDetailsIfUserIsNotNullAndStatusIsActivated() {
        UserDetailsRequestModel userDetailsModel = new UserDetailsRequestModel("pankaj.jain@nineleaps.com", "Male",
                "80 Feet Road, Koramangala", "Bangalore", 395007);

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj",
                "9999999999", "12345", "Enthusiast", true, true, true);

        when(userRepository.findByEmail(userDetailsModel.getEmail())).thenReturn(userClass);

        UserDetails actual = userDetailsService.addUserDetails(userDetailsModel);

        Assertions.assertEquals(null, actual);
    }

    @Test
    @DisplayName("Testing of adding the user details")
    void doNotAddUserDetailsIfUserIsNotNullAndStatusIsNotActivated() {
        UserDetailsRequestModel userDetailsModel = new UserDetailsRequestModel("pankaj.jain@nineleaps.com", "Male",
                "80 Feet Road, Koramangala", "Bangalore", 395007);

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj",
                "9999999999", "12345", "Enthusiast", false, true, true);

        when(userRepository.findByEmail(userDetailsModel.getEmail())).thenReturn(userClass);

        UserDetails actual = userDetailsService.addUserDetails(userDetailsModel);

        Assertions.assertEquals(null, actual);
    }

    @Test
    @DisplayName("Testing of adding the user details")
    void doNotAddUserDetailsIfUserIsNullAndStatusIsNotActivated() {
        UserDetailsRequestModel userDetailsModel = new UserDetailsRequestModel("pankaj.jain@nineleaps.com", "Male",
                "80 Feet Road, Koramangala", "Bangalore", 395007);

        UserClass userClass = new UserClass();
        userClass.setActivated(false);

        when(userRepository.findByEmail(userDetailsModel.getEmail())).thenReturn(userClass);

        UserDetails actual = userDetailsService.addUserDetails(userDetailsModel);

        Assertions.assertEquals(null, actual);
    }

    @Test
    @DisplayName("Testing of adding the user details")
    void doNotAddUserDetailsIfUserIsNullAndStatusIsActivated() {
        UserDetailsRequestModel userDetailsModel = new UserDetailsRequestModel("pankaj.jain@nineleaps.com", "Male",
                "80 Feet Road, Koramangala", "Bangalore", 395007);

        UserClass userClass = new UserClass();
        userClass.setActivated(true);

        UserDetails actual = userDetailsService.addUserDetails(userDetailsModel);

        Assertions.assertEquals(null, actual);
    }

    @Test
    @DisplayName("Testing of fetching the user details")
    void testGetUserDetails() {
        userDetails = new UserDetails("pankaj.jain@nineleaps.com", "male", "bhatar", "Surat", 395007);
        Optional<UserDetails> optional = Optional
                .of(new UserDetails("pankaj.jain@nineleaps.com", "male", "bhatar", "Surat", 395007));
        when(userDetailsRepository.findById(userDetails.getUEmail())).thenReturn(optional);
        Assertions.assertEquals(userDetails.getUEmail(),
                userDetailsService.getUserDetails("pankaj.jain@nineleaps.com").getUEmail());

    }

    @Test
    @DisplayName("Testing of fetching the user details")
    void testGetUserDetailsNotFound() {
        userDetails = new UserDetails("pankaj.jain@nineleaps.com", "male", "bhatar", "Surat", 395007);
        try {
            userDetailsService.getUserDetails("pankaj.jain@nineleaps.com").getUEmail();
        } catch (Exception e) {
            Assertions.assertEquals("No User Details Found", e.getMessage());
        }
    }
}
