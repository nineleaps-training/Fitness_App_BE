package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorDetails;
import com.fitness.app.model.VendorDetailsModel;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.repository.VendorDetailsRepository;
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
class VendorDetailsServiceTest {

    @MockBean
    private VendorDetailsRepository vendordetailsRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    VendorDetailsService vendorDetailsService;

    @Test
    void addVendorDetailsIfUserIsNotNullAndStatusIsActivated() {
        VendorDetailsModel vendorDetailsModel = new VendorDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Female", "80 Feet Road, Koramangala", "Bangalore", 560034L);

        VendorDetails vendorDetails = new VendorDetails("priyanshi.chaturvedi@nineleaps.com",
                "Female", "80 Feet Road, Koramangala", "Bangalore", 560034L);

        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true, true, true);

        when(userRepository.findByEmail(vendorDetailsModel.getEmail())).thenReturn(userClass);

        VendorDetails actual = vendorDetailsService.addVendorDetails(vendorDetailsModel);
        assertEquals(vendorDetails, actual);
    }

    @Test
    void doNotAddDetailsIfUserIsNotNullAndStatusIsNotActivated() {
        VendorDetailsModel vendorDetailsModel = new VendorDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Female", "80 Feet Road, Koramangala", "Bangalore", 560034L);

        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", false, true, true);
        when(userRepository.findByEmail(vendorDetailsModel.getEmail())).thenReturn(userClass);

        VendorDetails actual = vendorDetailsService.addVendorDetails(vendorDetailsModel);
        assertNull(actual);
    }

    @Test
    void doNotAddDetailsIfUserIsNullAndStatusIsNotActivated() {
        VendorDetailsModel vendorDetailsModel = new VendorDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Female", "80 Feet Road, Koramangala", "Bangalore", 560034L);

        UserClass userClass = new UserClass();
        userClass.setActivated(false);

        when(userRepository.findByEmail(vendorDetailsModel.getEmail())).thenReturn(userClass);

        VendorDetails actual = vendorDetailsService.addVendorDetails(vendorDetailsModel);
        assertNull(actual);

    }

    @Test
    void doNotAddDetailsIfUserIsNullAndStatusIsActivated() {
        VendorDetailsModel vendorDetailsModel = new VendorDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Female", "80 Feet Road, Koramangala", "Bangalore", 560034L);

        UserClass userClass = new UserClass();
        userClass.setActivated(true);

        VendorDetails actual = vendorDetailsService.addVendorDetails(vendorDetailsModel);
        assertNull(actual);

    }

    @Test
    void getVendorDetails() {
        VendorDetailsModel vendorDetailsModel = new VendorDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Female", "80 Feet Road, Koramangala", "Bangalore", 560034L);
        VendorDetails vendorDetails = new VendorDetails("priyanshi.chaturvedi@nineleaps.com",
                "Female", "80 Feet Road, Koramangala", "Bangalore", 560034L);

        when(vendordetailsRepository.findByEmail(vendorDetailsModel.getEmail())).thenReturn(vendorDetails);

        VendorDetails actual = vendorDetailsService.getVendorDetails(vendorDetailsModel.getEmail());
        assertEquals(vendorDetails, actual);
    }
}