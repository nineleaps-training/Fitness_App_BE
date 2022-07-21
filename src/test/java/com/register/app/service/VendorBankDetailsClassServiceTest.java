package com.register.app.service;

import com.fitness.app.dto.requestDtos.UserBankModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorBankDetailsClass;
import com.fitness.app.repository.BankDetailsRepository;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.service.VendorBankDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith({MockitoExtension.class})
class VendorBankDetailsClassServiceTest {


    @Mock
    private BankDetailsRepository bankDetailsRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks

    private VendorBankDetailsServiceImpl vendorBankDetailsServiceImpl;

    UserBankModel BANK_MODEL = new UserBankModel(
            "rahul01@gmail.com",
            "Rahul Kumar",
            "YES BANK",
            "Nineleaps",
            458658856845L,
            "NLPB00145",
            "Monthly"
    );

    VendorBankDetailsClass BANK = new VendorBankDetailsClass(
            "rahul01@gmail.com",
            "Rahul Kumar",
            "YES BANK",
            "Nineleaps",
            458658856845L,
            "NLPB00145",
            "Monthly"
    );
    UserClass USER1 = new UserClass("rahul01@gmail.com", "Rahul Kumar",
            "7651977515", "Rahul@123", "VENDOR", true, true, true);

    @Test
    void addDetails() {
        Mockito.when(userRepository.findByEmail(BANK_MODEL.getEmail())).thenReturn(USER1);
        ApiResponse bankDetails = vendorBankDetailsServiceImpl.addDetails(BANK_MODEL);
        Assertions.assertNotNull(bankDetails);
        Assertions.assertEquals(HttpStatus.OK, bankDetails.getStatus());
    }

    @Test
    void addDetailsWithNull() {
        Mockito.when(userRepository.findByEmail(BANK_MODEL.getEmail())).thenReturn(null);
        ApiResponse bankDetails = vendorBankDetailsServiceImpl.addDetails(BANK_MODEL);
        Assertions.assertNotNull(bankDetails);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, bankDetails.getStatus());
    }


    @Test
    void getDetails() {
        List<VendorBankDetailsClass> lists = new ArrayList<>(Arrays.asList(BANK));
        Mockito.when(bankDetailsRepository.findAll()).thenReturn(lists);


        List<VendorBankDetailsClass> vendorBankDetailsClassList = vendorBankDetailsServiceImpl.getDetails();


        Assertions.assertNotNull(vendorBankDetailsClassList);
        Assertions.assertEquals(vendorBankDetailsClassList.get(0).getVendorAccountNumber(), BANK.getVendorAccountNumber());
    }


    @Test
    void getBankDetails() {
        Mockito.when(bankDetailsRepository.findByVendorEmail(BANK_MODEL.getEmail())).thenReturn(BANK);

        VendorBankDetailsClass bankDetails = vendorBankDetailsServiceImpl.getBankDetails(BANK_MODEL.getEmail());


        Assertions.assertNotNull(bankDetails);
        Assertions.assertEquals(bankDetails.getVendorAccountNumber(), BANK.getVendorAccountNumber());
    }
}
