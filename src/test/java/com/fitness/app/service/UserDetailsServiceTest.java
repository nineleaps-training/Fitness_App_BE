package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.UserDetails;
import com.fitness.app.model.UserDetailsModel;
import com.fitness.app.repository.UserDetailsRepository;
import com.fitness.app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDetailsServiceTest {

    @MockBean
    private UserDetailsRepository userDetailsRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    UserDetailsService userDetailsService;

    @Test
    void addUserDetailsIfUserIsNotNullAndStatusIsActivated() {
        UserDetailsModel userDetailsModel = new UserDetailsModel("priyanshi.chaturvedi@nineleaps.com", "Female",
                "80 Feet Road, Koramangala", "Bangalore", 560034L);

        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true, true, true);

        UserDetails userDetails = new UserDetails("priyanshi.chaturvedi@nineleaps.com", "Female",
                "80 Feet Road, Koramangala", "Bangalore", 560034L);

        when(userRepository.findByEmail(userDetailsModel.getUserEmail())).thenReturn(userClass);

        UserDetails actual = userDetailsService.addUserDetails(userDetailsModel);
        assertEquals(userDetails, actual);
    }

    @Test
    void doNotAddUserDetailsIfUserIsNotNullAndStatusIsNotActivated() {
        UserDetailsModel userDetailsModel = new UserDetailsModel("priyanshi.chaturvedi@nineleaps.com", "Female",
                "80 Feet Road, Koramangala", "Bangalore", 560034L);

        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", false, true, true);

        when(userRepository.findByEmail(userDetailsModel.getUserEmail())).thenReturn(userClass);

        UserDetails actual = userDetailsService.addUserDetails(userDetailsModel);
        assertNull(actual);
    }

    @Test
    void doNotAddUserDetailsIfUserIsNullAndStatusIsNotActivated() {
        UserDetailsModel userDetailsModel = new UserDetailsModel("priyanshi.chaturvedi@nineleaps.com", "Female",
                "80 Feet Road, Koramangala", "Bangalore", 560034L);

        UserClass userClass = new UserClass();
        userClass.setActivated(false);

        when(userRepository.findByEmail(userDetailsModel.getUserEmail())).thenReturn(userClass);

        UserDetails actual = userDetailsService.addUserDetails(userDetailsModel);
        assertNull(actual);
    }

    @Test
    void doNotAddUserDetailsIfUserIsNullAndStatusIsActivated() {
        UserDetailsModel userDetailsModel = new UserDetailsModel("priyanshi.chaturvedi@nineleaps.com", "Female",
                "80 Feet Road, Koramangala", "Bangalore", 560034L);

        UserClass userClass = new UserClass();
        userClass.setActivated(true);

        UserDetails actual = userDetailsService.addUserDetails(userDetailsModel);
        assertNull(actual);
    }

    @Test
    void getUserDetails() {
        UserDetailsModel userDetailsModel = new UserDetailsModel("priyanshi.chaturvedi@nineleaps.com", "Female",
                "80 Feet Road, Koramangala", "Bangalore", 560034L);

        UserDetails userDetails = new UserDetails("priyanshi.chaturvedi@nineleaps.com", "Female",
                "80 Feet Road, Koramangala", "Bangalore", 560034L);

        when(userDetailsRepository.findByUserEmail(userDetailsModel.getUserEmail())).thenReturn(userDetails);

        UserDetails actual = userDetailsService.getUserDetails(userDetailsModel.getUserEmail());
        assertEquals(userDetails, actual);
    }
}