package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorBankDetails;
import com.fitness.app.model.VendorBankDetailsModel;
import com.fitness.app.repository.BankDetailsRepository;
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
class VendorBankDetailsServiceTest {

    @MockBean
    private BankDetailsRepository repository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private  VendorBankDetailsService vendorBankDetailsService;

    @Test
    void addBankDetailsIfUserIsNotNullAndStatusIsActivated() {
        VendorBankDetailsModel vendorBankDetailsModel = new VendorBankDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore", 59109876543211L,
                "HDFC0000036", "Monthly");

        VendorBankDetails vendorBankDetails = new VendorBankDetails("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore", 59109876543211L,
                "HDFC0000036", "Monthly");

        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true, true, true);

        when(userRepository.findByEmail(vendorBankDetailsModel.getEmail())).thenReturn(userClass);

        VendorBankDetails actual = vendorBankDetailsService.addDetails(vendorBankDetailsModel);
        assertEquals(vendorBankDetails, actual);
    }

    @Test
    void doNotAddBankDetailsIfUserIsNotNullAndStatusIsNotActivated() {
        VendorBankDetailsModel vendorBankDetailsModel = new VendorBankDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore", 59109876543211L,
                "HDFC0000036", "Monthly");

        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", false, true, true);

        when(userRepository.findByEmail(vendorBankDetailsModel.getEmail())).thenReturn(userClass);

        VendorBankDetails actual = vendorBankDetailsService.addDetails(vendorBankDetailsModel);
        assertNull(actual);
    }

    @Test
    void doNotAddBankDetailsIfUserIsNullAndStatusIsNotActivated() {
        VendorBankDetailsModel vendorBankDetailsModel = new VendorBankDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore", 59109876543211L,
                "HDFC0000036", "Monthly");

        UserClass userClass = new UserClass();
        userClass.setActivated(false);

        when(userRepository.findByEmail(vendorBankDetailsModel.getEmail())).thenReturn(userClass);

        VendorBankDetails actual = vendorBankDetailsService.addDetails(vendorBankDetailsModel);
        assertNull(actual);
    }

    @Test
    void doNotAddBankDetailsIfUserIsNullAndStatusIsActivated() {
        VendorBankDetailsModel vendorBankDetailsModel = new VendorBankDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore", 59109876543211L,
                "HDFC0000036", "Monthly");

        UserClass userClass = new UserClass();
        userClass.setActivated(true);

        VendorBankDetails actual = vendorBankDetailsService.addDetails(vendorBankDetailsModel);
        assertNull(actual);
    }

    @Test
    void getDetails() {
        VendorBankDetailsModel vendorBankDetailsModel = new VendorBankDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore", 59109876543211L,
                "HDFC0000036", "Monthly");

        VendorBankDetails vendorBankDetails = new VendorBankDetails("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore", 59109876543211L,
                "HDFC0000036", "Monthly");

        List<VendorBankDetails> vendorBankDetailsList = new ArrayList<>();
        vendorBankDetailsList.add(vendorBankDetails);

        when(repository.findAll()).thenReturn(vendorBankDetailsList);

        List<VendorBankDetails> actual = vendorBankDetailsService.getDetails();
        assertEquals(vendorBankDetailsList, actual);

    }

    @Test
    void getBankDetails() {
        VendorBankDetailsModel vendorBankDetailsModel = new VendorBankDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore", 59109876543211L,
                "HDFC0000036", "Monthly");

        VendorBankDetails vendorBankDetails = new VendorBankDetails("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore", 59109876543211L,
                "HDFC0000036", "Monthly");

        when(repository.findByEmail(vendorBankDetailsModel.getEmail())).thenReturn(vendorBankDetails);

        VendorBankDetails actual = vendorBankDetailsService.getBankDetails(vendorBankDetailsModel.getEmail());
        assertEquals(vendorBankDetails, actual);
    }
}