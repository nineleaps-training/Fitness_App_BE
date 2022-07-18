package com.fitness.app.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fitness.app.entity.UserBankDetails;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserBankDetailsRequestModel;
import com.fitness.app.repository.UserBankDetailsRepo;
import com.fitness.app.repository.UserRepo;

@ExtendWith(MockitoExtension.class)
class UserBankDetailsServiceTest {

    long l = 1234;

    MockMvc mockMvc;

    UserClass userClass;

    @Mock
    UserBankDetailsRepo userBankDetailsRepo;

    @Mock
    UserRepo userRepository;

    UserBankDetailsService userBankDetailsService;

    @BeforeEach
    public void initcase() {
        userBankDetailsService = new UserBankDetailsService(userBankDetailsRepo, userRepository);
    }

    @Test
    @DisplayName("Testing of adding the User Bank Details")
    void addBankDetailsIfUserIsNotNullAndStatusIsActivated() {
        UserBankDetailsRequestModel userBankDetailsModel = new UserBankDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Pankaj", "ICICI", "Bangalore",
                1234L, "ICICI00008", null);

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj",
                "9999999999", "12345", "Enthusiast", true, true, true);

        when(userRepository.findByEmail(userBankDetailsModel.getEmail())).thenReturn(userClass);

        UserBankDetails actual = userBankDetailsService.addBankDetails(userBankDetailsModel);

        Assertions.assertEquals(null, actual);

    }

    @Test
    @DisplayName("Testing of adding the User Bank Details")
    void doNotAddBankDetailsIfUserIsNotNullAndStatusIsNotActivated() {
        UserBankDetailsRequestModel userBankDetailsModel = new UserBankDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Pankaj", "ICICI", "Bangalore",
                1234L, "ICICI00008", null);

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj",
                "9999999999", "12345", "Enthusiast", false, true, true);

        when(userRepository.findByEmail(userBankDetailsModel.getEmail())).thenReturn(userClass);

        UserBankDetails actual = userBankDetailsService.addBankDetails(userBankDetailsModel);

        Assertions.assertEquals(null, actual);

    }

    @Test
    @DisplayName("Testing of adding the User Bank Details")
    void doNotAddBankDetailsIfUserIsNullAndStatusIsNotActivated() {
        UserBankDetailsRequestModel userBankDetailsModel = new UserBankDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Pankaj", "ICICI", "Bangalore",
                1234L, "ICICI00008", null);

        UserClass userClass = new UserClass();
        userClass.setActivated(false);

        when(userRepository.findByEmail(userBankDetailsModel.getEmail())).thenReturn(userClass);

        UserBankDetails actual = userBankDetailsService.addBankDetails(userBankDetailsModel);

        Assertions.assertEquals(null, actual);

    }

    @Test
    @DisplayName("Testing of adding the User Bank Details")
    void doNotAddBankDetailsIfUserIsNullAndStatusIsActivated() {
        UserBankDetailsRequestModel userBankDetailsModel = new UserBankDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Pankaj", "ICICI", "Bangalore",
                1234L, "ICICI00008", null);

        UserClass userClass = new UserClass();
        userClass.setActivated(true);

        UserBankDetails actual = userBankDetailsService.addBankDetails(userBankDetailsModel);

        Assertions.assertEquals(null, actual);

    }

    @Test
    @DisplayName("Testing of fetching the user bank details")
    void testGetBankDetailsOptional() {

        UserBankDetailsRequestModel userBankDetailsRequestModel = new UserBankDetailsRequestModel(
                "pankaj.jain@nineleaps.com", "Pankaj Jain", "ICICI", "Bhatar", l, "ICICI00008", null);
        try {
            userBankDetailsService.getBankDetails(userBankDetailsRequestModel.getEmail());
        } catch (Exception e) {
            Assertions.assertEquals("No User Bank Details Found", e.getMessage());
        }

    }

    @Test
    @DisplayName("Testing of fetching the user bank details")
    void testGetBankDetails() {

        Optional<UserBankDetails> optional = Optional.of(
                new UserBankDetails("pankaj.jain@nineleaps.com", "Pankaj Jain", "ICICI", "Bhatar", l, "ICICI00008"));
        UserBankDetailsRequestModel userBankDetailsRequestModel = new UserBankDetailsRequestModel(
                "pankaj.jain@nineleaps.com", "Pankaj Jain", "ICICI", "Bhatar", l, "ICICI00008", null);
        UserBankDetails userBankDetails = new UserBankDetails("pankaj.jain@nineleaps.com", "Pankaj Jain", "ICICI",
                "Bhatar", l, "ICICI00008");
        when(userBankDetailsRepo.findById(userBankDetailsRequestModel.getEmail())).thenReturn(optional);
        UserBankDetails userBankDetails2 = userBankDetailsService
                .getBankDetails(userBankDetailsRequestModel.getEmail());
        Assertions.assertEquals(userBankDetails, userBankDetails2);

    }
}
