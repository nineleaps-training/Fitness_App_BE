package com.fitness.app.service;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorDetails;
import com.fitness.app.model.UserDetailsRequestModel;
import com.fitness.app.repository.UserRepo;
import com.fitness.app.repository.VendorDetailsRepo;

@ExtendWith(MockitoExtension.class)
class VendorDetailsServiceTest {

    @Mock
    VendorDetailsRepo vendorDetailsRepository;

    @Mock
    UserRepo userRepository;

    @InjectMocks
    VendorDetailsService vendorDetailsService;

    UserDetailsRequestModel vendorDetailsRequestModel;

    long l = 1234;

    MockMvc mockMvc;
    UserClass userClass;

    @Test
    @DisplayName("Testing of adding the vendor details")
    void addUserDetailsIfUserIsNotNullAndStatusIsActivated() {
        UserDetailsRequestModel userDetailsModel = new UserDetailsRequestModel("pankaj.jain@nineleaps.com", "Male",
                "80 Feet Road, Koramangala", "Bangalore", 5678L);

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj",
                "9999999999", "12345", "Enthusiast", true, true, true);

        when(userRepository.findByEmail(userDetailsModel.getEmail())).thenReturn(userClass);

        VendorDetails actual = vendorDetailsService.addVendorDetails(userDetailsModel);

        Assertions.assertEquals(null, actual);
    }

    @Test
    @DisplayName("Testing of adding the vendor details")
    void doNotAddUserDetailsIfUserIsNotNullAndStatusIsNotActivated() {
        UserDetailsRequestModel userDetailsModel = new UserDetailsRequestModel("pankaj.jain@nineleaps.com", "Male",
                "80 Feet Road, Koramangala", "Bangalore", 5678L);

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj",
                "9999999999", "12345", "Enthusiast", false, true, true);

        when(userRepository.findByEmail(userDetailsModel.getEmail())).thenReturn(userClass);

        VendorDetails actual = vendorDetailsService.addVendorDetails(userDetailsModel);

        Assertions.assertEquals(null, actual);
    }

    @Test
    @DisplayName("Testing of adding the vendor details")
    void doNotAddUserDetailsIfUserIsNullAndStatusIsNotActivated() {
        UserDetailsRequestModel userDetailsModel = new UserDetailsRequestModel("pankaj.jain@nineleaps.com", "Male",
                "80 Feet Road, Koramangala", "Bangalore", 5678L);

        UserClass userClass = new UserClass();
        userClass.setActivated(false);

        when(userRepository.findByEmail(userDetailsModel.getEmail())).thenReturn(userClass);

        VendorDetails actual = vendorDetailsService.addVendorDetails(userDetailsModel);

        Assertions.assertEquals(null, actual);
    }

    @Test
    @DisplayName("Testing of adding the vendor details")
    void doNotAddUserDetailsIfUserIsNullAndStatusIsActivated() {
        UserDetailsRequestModel userDetailsModel = new UserDetailsRequestModel("pankaj.jain@nineleaps.com", "Male",
                "80 Feet Road, Koramangala", "Bangalore", 5678L);

        UserClass userClass = new UserClass();
        userClass.setActivated(true);

        VendorDetails actual = vendorDetailsService.addVendorDetails(userDetailsModel);

        Assertions.assertEquals(null, actual);
    }

    @Test
    @DisplayName("Testing of fetching the vendor details")
    void testGetVendorDetails() {
        long l = 12345;
        vendorDetailsRequestModel = new UserDetailsRequestModel("pankaj.jain@nineleaps.com", "male", "Bhatar", "Surat",
                l);
        Optional<VendorDetails> optional = Optional
                .of(new VendorDetails("pankaj.jain@nineleaps.com", "male", "Bhatar", "Surat", l));
        when(vendorDetailsRepository.findById(vendorDetailsRequestModel.getEmail())).thenReturn(optional);
        Assertions.assertEquals(vendorDetailsRequestModel.getEmail(),
                vendorDetailsService.getVendorDetails("pankaj.jain@nineleaps.com").getVEmail());
    }

    @Test
    @DisplayName("Testing of fetching the vendor details")
    void testGetVendorDetailsNotFound() {
        long l = 12345;
        vendorDetailsRequestModel = new UserDetailsRequestModel("pankaj.jain@nineleaps.com", "male", "Bhatar", "Surat",
                l);
        try {
            vendorDetailsService.getVendorDetails("pankaj.jain@nineleaps.com").getVEmail();
        } catch (Exception e) {
            Assertions.assertEquals("No Vendor Details Found", e.getMessage());
        }
    }
}
