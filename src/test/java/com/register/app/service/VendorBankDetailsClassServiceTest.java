package com.register.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorBankDetailsClass;
import com.fitness.app.model.UserBankModel;
import com.fitness.app.repository.BankDetailsRepository;
import com.fitness.app.repository.UserRepository;
<<<<<<< HEAD
import com.fitness.app.service.VendorBankDetailsService;
=======
import com.fitness.app.service.VendorBankDetailsServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
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
class VendorBankDetailsClassServiceTest {



    @Mock
    private BankDetailsRepository bankDetailsRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
<<<<<<< HEAD
    private VendorBankDetailsService vendorBankDetailsService;
=======
    private VendorBankDetailsServiceImpl vendorBankDetailsServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48

    UserBankModel BANK_MODEL=new UserBankModel(
            "rahul01@gmail.com",
            "Rahul Kumar",
            "YES BANK",
            "Nineleaps",
            458658856845L,
            "NLPB00145",
            "Monthly"
    );

    VendorBankDetailsClass BANK=new VendorBankDetailsClass(
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
<<<<<<< HEAD
        VendorBankDetailsClass bankDetails=vendorBankDetailsService.addDetails(BANK_MODEL);
=======
        VendorBankDetailsClass bankDetails= vendorBankDetailsServiceImpl.addDetails(BANK_MODEL);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
        Assertions.assertNotNull(bankDetails);
        Assertions.assertEquals(bankDetails.getVendorAccountNumber(), BANK.getVendorAccountNumber());
    }

    @Test
     void addDetailsWithNull()
    {
        Mockito.when(userRepository.findByEmail(BANK_MODEL.getEmail())).thenReturn(null);
<<<<<<< HEAD
        VendorBankDetailsClass bankDetails=vendorBankDetailsService.addDetails(BANK_MODEL);
=======
        VendorBankDetailsClass bankDetails= vendorBankDetailsServiceImpl.addDetails(BANK_MODEL);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
        Assertions.assertNull(bankDetails);
    }



    @Test
     void getDetails()
    {
        List<VendorBankDetailsClass> lists=new ArrayList<>(Arrays.asList(BANK));
        Mockito.when(bankDetailsRepository.findAll()).thenReturn(lists);

<<<<<<< HEAD
        List<VendorBankDetailsClass> vendorBankDetailsClassList =vendorBankDetailsService.getDetails();
=======
        List<VendorBankDetailsClass> vendorBankDetailsClassList = vendorBankDetailsServiceImpl.getDetails();
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48

        Assertions.assertNotNull(vendorBankDetailsClassList);
        Assertions.assertEquals(vendorBankDetailsClassList.get(0).getVendorAccountNumber(), BANK.getVendorAccountNumber());
    }



    @Test
     void getBankDetails()
    {
        Mockito.when(bankDetailsRepository.findByVendorEmail(BANK_MODEL.getEmail())).thenReturn(BANK);
<<<<<<< HEAD
        VendorBankDetailsClass bankDetails=vendorBankDetailsService.getBankDetails(BANK_MODEL.getEmail());
=======
        VendorBankDetailsClass bankDetails= vendorBankDetailsServiceImpl.getBankDetails(BANK_MODEL.getEmail());
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48

        Assertions.assertNotNull(bankDetails);
        Assertions.assertEquals(bankDetails.getVendorAccountNumber(), BANK.getVendorAccountNumber());
    }
}
