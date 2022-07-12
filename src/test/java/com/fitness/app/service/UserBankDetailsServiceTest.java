package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserBankDetailsModel;
import com.fitness.app.repository.UserBankDetailsRepo;
import com.fitness.app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserBankDetailsServiceTest {

    @MockBean
    private UserBankDetailsRepo repository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    UserBankDetailsService userBankDetailsService;


    @Test
    void addBankDetailsIfUserIsNotNullAndStatusIsActivated() {
        UserBankDetailsModel userBankDetailsModel = new UserBankDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore",
                59109876543211L, "HDFC0000036");

        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true, true, true);

        when(userRepository.findByEmail(userBankDetailsModel.getUserEmail())).thenReturn(userClass);

        UserBankDetailsModel actual = userBankDetailsService.addBankDetails(userBankDetailsModel);
        assertEquals(userBankDetailsModel, actual);

    }

    @Test
    void doNotAddBankDetailsIfUserIsNotNullAndStatusIsNotActivated() {
        UserBankDetailsModel userBankDetailsModel = new UserBankDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore",
                59109876543211L, "HDFC0000036");

        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", false, true, true);

        when(userRepository.findByEmail(userBankDetailsModel.getUserEmail())).thenReturn(userClass);

        UserBankDetailsModel actual = userBankDetailsService.addBankDetails(userBankDetailsModel);
        assertNull(actual);

    }

    @Test
    void doNotAddBankDetailsIfUserIsNullAndStatusIsNotActivated() {
        UserBankDetailsModel userBankDetailsModel = new UserBankDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore",
                59109876543211L, "HDFC0000036");

        UserClass userClass = new UserClass();
        userClass.setActivated(false);

        when(userRepository.findByEmail(userBankDetailsModel.getUserEmail())).thenReturn(userClass);

        UserBankDetailsModel actual = userBankDetailsService.addBankDetails(userBankDetailsModel);
        assertNull(actual);

    }

    @Test
    void doNotAddBankDetailsIfUserIsNullAndStatusIsActivated() {
        UserBankDetailsModel userBankDetailsModel = new UserBankDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore",
                59109876543211L, "HDFC0000036");

        UserClass userClass = new UserClass();
        userClass.setActivated(true);

        UserBankDetailsModel actual = userBankDetailsService.addBankDetails(userBankDetailsModel);
        assertNull(actual);

    }


    @Test
    void getAllDetails() {
        UserBankDetailsModel userBankDetailsModel = new UserBankDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore",
                59109876543211L, "HDFC0000036");

        List<UserBankDetailsModel> userBankDetailsModels = new ArrayList<>();
        userBankDetailsModels.add(userBankDetailsModel);

        when(repository.findAll()).thenReturn(userBankDetailsModels);

        List<UserBankDetailsModel> actual = userBankDetailsService.getAllDetails();
        assertEquals(userBankDetailsModels, actual);
    }

    @Test
    void getBankDetails() {
        UserBankDetailsModel userBankDetailsModel = new UserBankDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore",
                59109876543211L, "HDFC0000036");

        when(repository.findByUserEmail(userBankDetailsModel.getUserEmail())).thenReturn(userBankDetailsModel);

        UserBankDetailsModel actual = userBankDetailsService.getBankDetails(userBankDetailsModel.getUserEmail());
        assertEquals(userBankDetailsModel, actual);
    }
}