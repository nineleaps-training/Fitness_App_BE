package com.fitness.app.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserBankDetailsRequestModel;
import com.fitness.app.repository.UserBankDetailsRepo;
import com.fitness.app.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserBankDetailsServiceTest {

    long l=1234;
   
    MockMvc mockMvc;
    
    UserClass userClass;

    @Mock
    UserBankDetailsRepo userBankDetailsRepo;

    @Mock
    UserRepository userRepository;

    UserBankDetailsService userBankDetailsService;

    @BeforeEach
    public void initcase() {
        userBankDetailsService=new UserBankDetailsService(userBankDetailsRepo, userRepository);
    }

    @Test
    void addBankDetailsIfUserIsNotNullAndStatusIsActivated() {
        UserBankDetailsRequestModel userBankDetailsModel = new UserBankDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Pankaj", "ICICI", "Bangalore",
                1234L, "ICICI00008");

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj",
                "9999999999", "12345", "Enthusiast", true, true, true);

        when(userRepository.findByEmail(userBankDetailsModel.getUEmail())).thenReturn(userClass);

        UserBankDetailsRequestModel actual = userBankDetailsService.addBankDetails(userBankDetailsModel);

        Assertions.assertEquals(null, actual);

    }

    @Test
    void doNotAddBankDetailsIfUserIsNotNullAndStatusIsNotActivated() {
        UserBankDetailsRequestModel userBankDetailsModel = new UserBankDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Pankaj", "ICICI", "Bangalore",
                1234L, "ICICI00008");

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj",
                "9999999999", "12345", "Enthusiast", false, true, true);

        when(userRepository.findByEmail(userBankDetailsModel.getUEmail())).thenReturn(userClass);

        UserBankDetailsRequestModel actual = userBankDetailsService.addBankDetails(userBankDetailsModel);

        Assertions.assertEquals(null,actual);

    }

    @Test
    void doNotAddBankDetailsIfUserIsNullAndStatusIsNotActivated() {
        UserBankDetailsRequestModel userBankDetailsModel = new UserBankDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Pankaj", "ICICI", "Bangalore",
                1234L, "ICICI00008");

        UserClass userClass = new UserClass();
        userClass.setActivated(false);

        when(userRepository.findByEmail(userBankDetailsModel.getUEmail())).thenReturn(userClass);

        UserBankDetailsRequestModel actual = userBankDetailsService.addBankDetails(userBankDetailsModel);

        Assertions.assertEquals(null,actual);

    }

    @Test
    void doNotAddBankDetailsIfUserIsNullAndStatusIsActivated() {
        UserBankDetailsRequestModel userBankDetailsModel = new UserBankDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Pankaj", "ICICI", "Bangalore",
                1234L, "ICICI00008");

        UserClass userClass = new UserClass();
        userClass.setActivated(true);

        UserBankDetailsRequestModel actual = userBankDetailsService.addBankDetails(userBankDetailsModel);

        Assertions.assertEquals(null,actual);

    }

    @Test
    void testGetAllDetails() {

        UserBankDetailsRequestModel userBankDetailsRequestModel=new UserBankDetailsRequestModel("pankaj.jain@nineleaps.com","Pankaj Jain","ICICI","Bhatar",l,"ICICI00008");
        List<UserBankDetailsRequestModel> userBankDetailsRequestModels=new ArrayList<>();
        userBankDetailsRequestModels.add(userBankDetailsRequestModel);
        when(userBankDetailsRepo.findAll()).thenReturn(userBankDetailsRequestModels);
        List<UserBankDetailsRequestModel> userBankDetailsRequestModels2= userBankDetailsService.getAllDetails();
        Assertions.assertEquals(userBankDetailsRequestModels, userBankDetailsRequestModels2); 

    }

    @Test
    void testGetBankDetails() {
        
        UserBankDetailsRequestModel userBankDetailsRequestModel=new UserBankDetailsRequestModel("pankaj.jain@nineleaps.com","Pankaj Jain","ICICI","Bhatar",l,"ICICI00008");
        when(userBankDetailsRepo.findByUEmail(userBankDetailsRequestModel.getUEmail())).thenReturn(userBankDetailsRequestModel);    
        UserBankDetailsRequestModel userBankDetailsRequestModel2=userBankDetailsService.getBankDetails(userBankDetailsRequestModel.getUEmail());
        Assertions.assertEquals(userBankDetailsRequestModel, userBankDetailsRequestModel2);
 

    }
}
