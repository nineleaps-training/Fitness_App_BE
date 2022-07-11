package com.fitness.app.service;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorBankDetails;
import com.fitness.app.model.VendorBankDetailsRequestModel;
import com.fitness.app.repository.BankDetailsRepository;
import com.fitness.app.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class VendorBankDetailsServiceTest {
    @Mock
    BankDetailsRepository bankDetailsRepository;

    @Mock
    UserRepository userRepository;

    VendorBankDetailsService vendorBankDetailsService;

    VendorBankDetails vendorBankDetails;

    VendorBankDetailsRequestModel userBankDetailsModel;

    long l=1234;
   
    MockMvc mockMvc;
    UserClass userClass;

    @BeforeEach
    public void initcase() {
        vendorBankDetailsService=new VendorBankDetailsService(bankDetailsRepository, userRepository);
    }
    
    @Test
    void addBankDetailsIfUserIsNotNullAndStatusIsActivated() {
        userBankDetailsModel = new VendorBankDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Pankaj", "ICICI", "Bangalore",
                1234L, "ICICI2000","weekly");

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj",
                "9685903290", "12345", "Enthusiast", true, true, true);

        when(userRepository.findByEmail(userBankDetailsModel.getEmail())).thenReturn(userClass);

        VendorBankDetailsRequestModel actual = vendorBankDetailsService.addDetails(userBankDetailsModel);

        Assertions.assertEquals(null, actual);

    }

    @Test
    void doNotAddBankDetailsIfUserIsNotNullAndStatusIsNotActivated() {
        VendorBankDetailsRequestModel userBankDetailsModel = new VendorBankDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Pankaj", "ICICI", "Bangalore",
                1234L, "ICICI2000","weekly");

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj",
                "9685903290", "12345", "Enthusiast", false, true, true);

        when(userRepository.findByEmail(userBankDetailsModel.getEmail())).thenReturn(userClass);

        VendorBankDetailsRequestModel actual = vendorBankDetailsService.addDetails(userBankDetailsModel);

        Assertions.assertEquals(null,actual);

    }

    @Test
    void doNotAddBankDetailsIfUserIsNullAndStatusIsNotActivated() {
        VendorBankDetailsRequestModel userBankDetailsModel = new VendorBankDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Pankaj", "ICICI", "Bangalore",
                1234L, "ICICI2000","weekly");

        UserClass userClass = new UserClass();
        userClass.setActivated(false);

        when(userRepository.findByEmail(userBankDetailsModel.getEmail())).thenReturn(userClass);

        VendorBankDetailsRequestModel actual = vendorBankDetailsService.addDetails(userBankDetailsModel);

        Assertions.assertEquals(null,actual);

    }

    @Test
    void doNotAddBankDetailsIfUserIsNullAndStatusIsActivated() {
        VendorBankDetailsRequestModel userBankDetailsModel = new VendorBankDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Pankaj", "ICICI", "Bangalore",
                1234L, "ICICI2000","weekly");

        UserClass userClass = new UserClass();
        userClass.setActivated(true);

        VendorBankDetailsRequestModel actual = vendorBankDetailsService.addDetails(userBankDetailsModel);

        Assertions.assertEquals(null,actual);

    }

    @Test
    void testGetBankDetails() {

        VendorBankDetailsRequestModel vendorBankDetailsRequestModel=new VendorBankDetailsRequestModel("pankaj.jain@nineleaps.com","Pankaj Jain","ICICI","Bhatar",l,"ICICI00008","weekly");
        when(bankDetailsRepository.findByEmail(vendorBankDetailsRequestModel.getEmail())).thenReturn(vendorBankDetailsRequestModel);    
        VendorBankDetailsRequestModel vendorBankDetailsRequestModel2=vendorBankDetailsService.getBankDetails(vendorBankDetailsRequestModel.getEmail());
        Assertions.assertEquals(vendorBankDetailsRequestModel, vendorBankDetailsRequestModel2);
    }

    @Test
    void testGetDetails() {

        VendorBankDetailsRequestModel vendorBankDetailsRequestModel=new VendorBankDetailsRequestModel("pankaj.jain@nineleaps.com","Pankaj Jain","ICICI","Bhatar",l,"ICICI00008","weekly");
        List<VendorBankDetailsRequestModel> vendorBankDetailsRequestModels=new ArrayList<>();
        vendorBankDetailsRequestModels.add(vendorBankDetailsRequestModel);
        when(bankDetailsRepository.findAll()).thenReturn(vendorBankDetailsRequestModels);
        List<VendorBankDetailsRequestModel> vendorBankDetailsRequestModels2= vendorBankDetailsService.getDetails();
        Assertions.assertEquals(vendorBankDetailsRequestModels, vendorBankDetailsRequestModels2); 
    }
}
