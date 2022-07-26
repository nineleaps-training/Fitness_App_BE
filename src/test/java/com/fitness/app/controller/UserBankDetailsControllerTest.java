package com.fitness.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.entity.UserBankDetails;
import com.fitness.app.model.UserBankDetailsModel;
import com.fitness.app.service.UserBankDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserBankDetailsControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mockMvc;

    UserBankDetailsModel userBankDetailsModel;
    List<UserBankDetailsModel> userBankDetailsModelList = new ArrayList<>();
    UserBankDetails userBankDetails;
    List<UserBankDetails> userBankDetailsList = new ArrayList<>();

    @Mock
    UserBankDetailsService userBankDetailsService;


    @InjectMocks
    UserBankDetailsController userBankDetailsController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userBankDetailsController).build();

        userBankDetailsModel = new UserBankDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore",
                59109876543211L, "HDFC0000036");
        userBankDetailsModelList.add(userBankDetailsModel);

        userBankDetails = new UserBankDetails("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore",
                59109876543211L, "HDFC0000036");
        userBankDetailsList.add(userBankDetails);

    }

    @Test
    void addBankDetails() throws Exception {
        String content = objectMapper.writeValueAsString(userBankDetailsModel);

        when(userBankDetailsService.addBankDetails(userBankDetailsModel)).thenReturn(userBankDetails);

        mockMvc.perform(MockMvcRequestBuilders.put("/user-bankdetails/add").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void doNotAddBankDetailsIfUserIsNull() throws Exception {
        UserBankDetailsModel userBankDetailsModelNull = new UserBankDetailsModel();

        String content = objectMapper.writeValueAsString(userBankDetailsModelNull);

        when(userBankDetailsService.addBankDetails(userBankDetailsModelNull)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/user-bankdetails/add").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isBadRequest());

    }

    @Test
    void getBankDetails() throws Exception {
        when(userBankDetailsService.getBankDetails(userBankDetailsModel.getUserEmail())).thenReturn(userBankDetails);

        mockMvc.perform(MockMvcRequestBuilders.get("/user-bankdetails/get/priyanshi.chaturvedi@nineleaps.com").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getAllDetails() throws Exception {
        when(userBankDetailsService.getAllDetails(0, 1)).thenReturn(userBankDetailsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/user-bankdetails/getall/0/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}