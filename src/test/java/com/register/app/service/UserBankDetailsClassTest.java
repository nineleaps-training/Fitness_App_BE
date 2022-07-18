package com.register.app.service;

import com.fitness.app.entity.UserBankDetailsClass;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserBankModel;
import com.fitness.app.repository.UserBankDetailsRepo;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.service.UserBankDetailsService;
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

@ExtendWith(MockitoExtension.class)
class UserBankDetailsClassTest {



    @Mock
    private UserRepository userRepository;

    @Mock
    private UserBankDetailsRepo userBankDetailsRepo;
    @InjectMocks
    private UserBankDetailsService userBankDetailsService;

    UserBankModel BANK_MODEL=new UserBankModel(
            "rahul01@gmail.com",
            "Rahul Kumar",
            "YES BANK",
            "Nineleaps",
            458658856845L,
            "NLPB00145",
            "Monthly"
    );
    UserBankDetailsClass BANK=new UserBankDetailsClass("rahul01@gmail.com",
            "Rahul Kumar",
            "YES BANK",
            "Nineleaps",
            458658856845L,
            "NLPB00145");



    UserClass USER1=new UserClass("rahul01@gmail.com", "Rahul Kumar",
            "7651977515","Rahul@123","USER", true, true, true );
    @Test
     void addBankDetails()
    {
        Mockito.when(userRepository.findByEmail(BANK_MODEL.getEmail())).thenReturn(USER1);
        UserBankDetailsClass bankDetails=userBankDetailsService.addBankDetails(BANK_MODEL);
        Assertions.assertNotNull(bankDetails);
        Assertions.assertEquals(bankDetails.getEmail(), USER1.getEmail());

    }
    @Test
     void addBankDetailsWithNull()
    {
        Mockito.when(userRepository.findByEmail(BANK_MODEL.getEmail())).thenReturn(null);
        UserBankDetailsClass bankDetails=userBankDetailsService.addBankDetails(BANK_MODEL);
        Assertions.assertNull(bankDetails);


    }

    @Test
    void getAllDetails()
    {
        List<UserBankDetailsClass> theList=new ArrayList<>(Arrays.asList(BANK));
        Mockito.when(userBankDetailsRepo.findAll()).thenReturn(theList);
        List<UserBankDetailsClass> userBankDetailsClassList =userBankDetailsService.getAllDetails();
        Assertions.assertNotNull(userBankDetailsClassList);
        Assertions.assertEquals(userBankDetailsClassList.get(0).getEmail(), BANK_MODEL.getEmail());
    }

    @Test
    void getBankDetails()
    {
        Mockito.when(userBankDetailsRepo.findByEmail(BANK.getEmail())).thenReturn(BANK);
        UserBankDetailsClass theBank=userBankDetailsService.getBankDetails(BANK.getEmail());
        Assertions.assertNotNull(theBank);
        Assertions.assertEquals(theBank.getEmail(), BANK.getEmail());
    }


}
