package com.fitness.app.service;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.UserDetails;
import com.fitness.app.model.UserDetailsRequestModel;
import com.fitness.app.repository.UserDetailsRepository;
import com.fitness.app.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserDetailsRepository userDetailsRepository;

    UserDetailsService userDetailsService;

    long l=1234;
   
    MockMvc mockMvc;
    UserDetailsRequestModel userDetailsRequestModel;
    UserClass userClass;
    UserDetails userDetails;

    @BeforeEach
    public void initcase() {
        userDetailsService=new UserDetailsService(userRepository,userDetailsRepository);
    }

    @Test
    void addUserDetailsIfUserIsNotNullAndStatusIsActivated() {
        UserDetailsRequestModel userDetailsModel = new UserDetailsRequestModel("pankaj.jain@nineleaps.com", "Male",
                "80 Feet Road, Koramangala", "Bangalore");

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj",
                "9999999999", "12345", "Enthusiast", true, true, true);

        when(userRepository.findByEmail(userDetailsModel.getEmail())).thenReturn(userClass);

        UserDetailsRequestModel actual = userDetailsService.addUserDetails(userDetailsModel);

        Assertions.assertEquals(null, actual);
    }

    @Test
    void doNotAddUserDetailsIfUserIsNotNullAndStatusIsNotActivated() {
        UserDetailsRequestModel userDetailsModel = new UserDetailsRequestModel("pankaj.jain@nineleaps.com", "Male",
                "80 Feet Road, Koramangala", "Bangalore");

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj",
                "9999999999", "12345", "Enthusiast", false, true, true);

        when(userRepository.findByEmail(userDetailsModel.getEmail())).thenReturn(userClass);

        UserDetailsRequestModel actual = userDetailsService.addUserDetails(userDetailsModel);

        Assertions.assertEquals(null,actual);
    }

    @Test
    void doNotAddUserDetailsIfUserIsNullAndStatusIsNotActivated() {
        UserDetailsRequestModel userDetailsModel = new UserDetailsRequestModel("pankaj.jain@nineleaps.com", "Male",
                "80 Feet Road, Koramangala", "Bangalore");

        UserClass userClass = new UserClass();
        userClass.setActivated(false);

        when(userRepository.findByEmail(userDetailsModel.getEmail())).thenReturn(userClass);

        UserDetailsRequestModel actual = userDetailsService.addUserDetails(userDetailsModel);

        Assertions.assertEquals(null,actual);
    }

    @Test
    void doNotAddUserDetailsIfUserIsNullAndStatusIsActivated() {
        UserDetailsRequestModel userDetailsModel = new UserDetailsRequestModel("pankaj.jain@nineleaps.com", "Male",
                "80 Feet Road, Koramangala", "Bangalore");

        UserClass userClass = new UserClass();
        userClass.setActivated(true);

        UserDetailsRequestModel actual = userDetailsService.addUserDetails(userDetailsModel);

        Assertions.assertEquals(null,actual);
    }

    
    @Test
    void testGetUserDetails() {
        userDetails = new UserDetails("pankaj.jain@nineleaps.com","male","bhatar","Surat",395007);
        when(userDetailsRepository.findByEmail(userDetails.getUEmail())).thenReturn(userDetails);
        Assertions.assertEquals(userDetails.getUEmail(), userDetailsService.getUserDetails("pankaj.jain@nineleaps.com").getUEmail());
       
    }
}
