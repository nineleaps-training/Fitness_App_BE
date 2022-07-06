package com.register.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorBankDetails;
import com.fitness.app.model.UserBankModel;
import com.fitness.app.repository.BankDetailsRepository;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.service.VendorBankDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith({MockitoExtension.class})
class VendorBankDetailsServiceTest {



    @Mock
    private BankDetailsRepository bankDetailsRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private VendorBankDetailsService vendorBankDetailsService;

    UserBankModel BANK_MODEL=new UserBankModel(
            "rahul01@gmail.com",
            "Rahul Kumar",
            "YES BANK",
            "Nineleaps",
            458658856845L,
            "NLPB00145",
            "Monthly"
    );

    VendorBankDetails BANK=new VendorBankDetails(
            "rahul01@gmail.com",
            "Rahul Kumar",
            "YES BANK",
            "Nineleaps",
            458658856845L,
            "NLPB00145",
            "Monthly"
    );
    UserClass USER1=new UserClass("rahul01@gmail.com", "Rahul Kumar",
            "7651977515","Rahul@123","VENDOR", true, true, true );

    @Test
     void addDetails()
    {
        Mockito.when(userRepository.findByEmail(BANK_MODEL.getEmail())).thenReturn(USER1);
        VendorBankDetails bankDetails=vendorBankDetailsService.addDetails(BANK_MODEL);
        Assertions.assertNotNull(bankDetails);
        Assertions.assertEquals(bankDetails.getVendorAccountNumber(), BANK.getVendorAccountNumber());
    }

    @Test
     void addDetailsWithNull()
    {
        Mockito.when(userRepository.findByEmail(BANK_MODEL.getEmail())).thenReturn(null);
        VendorBankDetails bankDetails=vendorBankDetailsService.addDetails(BANK_MODEL);
        Assertions.assertNull(bankDetails);
    }



    @Test
     void getDetails()
    {
        List<VendorBankDetails> lists=new ArrayList<>(Arrays.asList(BANK));
        Mockito.when(bankDetailsRepository.findAll()).thenReturn(lists);

        List<VendorBankDetails> vendorBankDetailsList=vendorBankDetailsService.getDetails();

        Assertions.assertNotNull(vendorBankDetailsList);
        Assertions.assertEquals(vendorBankDetailsList.get(0).getVendorAccountNumber(), BANK.getVendorAccountNumber());
    }



    @Test
     void getBankDetails()
    {
        Mockito.when(bankDetailsRepository.findByVendorEmail(BANK_MODEL.getEmail())).thenReturn(BANK);
        VendorBankDetails bankDetails=vendorBankDetailsService.getBankDetails(BANK_MODEL.getEmail());

        Assertions.assertNotNull(bankDetails);
        Assertions.assertEquals(bankDetails.getVendorAccountNumber(), BANK.getVendorAccountNumber());
    }
}
