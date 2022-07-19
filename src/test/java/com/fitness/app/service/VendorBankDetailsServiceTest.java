package com.fitness.app.service;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorBankDetails;
import com.fitness.app.model.UserBankDetailsRequestModel;
import com.fitness.app.repository.BankDetailsRepo;
import com.fitness.app.repository.UserRepo;

@ExtendWith(MockitoExtension.class)
class VendorBankDetailsServiceTest {
    @Mock
    BankDetailsRepo bankDetailsRepository;

    @Mock
    UserRepo userRepository;

    @InjectMocks
    VendorBankDetailsService vendorBankDetailsService;

    VendorBankDetails vendorBankDetails;

    UserBankDetailsRequestModel userBankDetailsModel;

    long l = 1234;

    MockMvc mockMvc;
    UserClass userClass;

    @Test
    @DisplayName("Testing of adding the vendor details")
    void addBankDetailsIfUserIsNotNullAndStatusIsActivated() {
        userBankDetailsModel = new UserBankDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Pankaj", "ICICI", "Bangalore",
                1234L, "ICICI2000", "weekly");

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj",
                "9685903290", "12345", "Enthusiast", true, true, true);

        when(userRepository.findByEmail(userBankDetailsModel.getEmail())).thenReturn(userClass);

        VendorBankDetails actual = vendorBankDetailsService.addDetails(userBankDetailsModel);

        Assertions.assertEquals(null, actual);

    }

    @Test
    @DisplayName("Testing of adding the vendor details")
    void doNotAddBankDetailsIfUserIsNotNullAndStatusIsNotActivated() {
        UserBankDetailsRequestModel userBankDetailsModel = new UserBankDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Pankaj", "ICICI", "Bangalore",
                1234L, "ICICI2000", "weekly");

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj",
                "9685903290", "12345", "Enthusiast", false, true, true);

        when(userRepository.findByEmail(userBankDetailsModel.getEmail())).thenReturn(userClass);

        VendorBankDetails actual = vendorBankDetailsService.addDetails(userBankDetailsModel);

        Assertions.assertEquals(null, actual);

    }

    @Test
    @DisplayName("Testing of adding the vendor details")
    void doNotAddBankDetailsIfUserIsNullAndStatusIsNotActivated() {
        UserBankDetailsRequestModel userBankDetailsModel = new UserBankDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Pankaj", "ICICI", "Bangalore",
                1234L, "ICICI2000", "weekly");

        UserClass userClass = new UserClass();
        userClass.setActivated(false);

        when(userRepository.findByEmail(userBankDetailsModel.getEmail())).thenReturn(userClass);

        VendorBankDetails actual = vendorBankDetailsService.addDetails(userBankDetailsModel);

        Assertions.assertEquals(null, actual);

    }

    @Test
    @DisplayName("Testing of adding the vendor details")
    void doNotAddBankDetailsIfUserIsNullAndStatusIsActivated() {
        UserBankDetailsRequestModel userBankDetailsModel = new UserBankDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Pankaj", "ICICI", "Bangalore",
                1234L, "ICICI2000", "weekly");

        UserClass userClass = new UserClass();
        userClass.setActivated(true);

        VendorBankDetails actual = vendorBankDetailsService.addDetails(userBankDetailsModel);

        Assertions.assertEquals(null, actual);

    }

    @Test
    @DisplayName("Testing of fetching the vendor bank details")
    void testGetBankDetails() {

        UserBankDetailsRequestModel vendorBankDetailsRequestModel = new UserBankDetailsRequestModel(
                "pankaj.jain@nineleaps.com", "Pankaj Jain", "ICICI", "Bhatar", l, "ICICI00008", "weekly");
        VendorBankDetails vendorBankDetails = new VendorBankDetails("pankaj.jain@nineleaps.com", "Pankaj Jain", "ICICI",
                "Bhatar", l, "ICICI00008", "weekly");
        when(bankDetailsRepository.findByEmail(vendorBankDetailsRequestModel.getEmail())).thenReturn(vendorBankDetails);
        VendorBankDetails vendorBankDetailsRequestModel2 = vendorBankDetailsService
                .getBankDetails(vendorBankDetailsRequestModel.getEmail());
        Assertions.assertEquals(vendorBankDetails, vendorBankDetailsRequestModel2);
    }

    @Test
    @DisplayName("Testing of fetching the vendor bank details")
    void testGetBankDetailsNotFound() {

        UserBankDetailsRequestModel vendorBankDetailsRequestModel = new UserBankDetailsRequestModel(
                "pankaj.jain@nineleaps.com", "Pankaj Jain", "ICICI", "Bhatar", l, "ICICI00008", "weekly");
        when(bankDetailsRepository.findByEmail(vendorBankDetailsRequestModel.getEmail())).thenReturn(null);
        try {
            vendorBankDetailsService.getBankDetails(vendorBankDetailsRequestModel.getEmail());
        } catch (Exception e) {
            Assertions.assertEquals("No vendor found with this email", e.getMessage());
        }

    }

    @Test
    @DisplayName("Testing of fetching the vendor bank details")
    void testGetDetails() {

        UserBankDetailsRequestModel vendorBankDetailsRequestModel = new UserBankDetailsRequestModel(
                "pankaj.jain@nineleaps.com", "Pankaj Jain", "ICICI", "Bhatar", l, "ICICI00008", "weekly");
        VendorBankDetails vendorBankDetails = new VendorBankDetails("pankaj.jain@nineleaps.com", "Pankaj Jain", "ICICI",
                "Bhatar", l, "ICICI00008", "weekly");
        List<UserBankDetailsRequestModel> vendorBankDetailsRequestModels = new ArrayList<>();
        vendorBankDetailsRequestModels.add(vendorBankDetailsRequestModel);
        List<VendorBankDetails> vendorBankDetails2 = new ArrayList<>();
        vendorBankDetails2.add(vendorBankDetails);
        when(bankDetailsRepository.findAll()).thenReturn(vendorBankDetails2);
        List<VendorBankDetails> vendorBankDetailsRequestModels2 = vendorBankDetailsService.getDetails();
        Assertions.assertEquals(vendorBankDetails2, vendorBankDetailsRequestModels2);
    }
}
