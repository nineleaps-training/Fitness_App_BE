package com.fitness.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.model.UserBankDetailsModel;
import com.fitness.app.service.UserBankDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @MockBean
    UserBankDetailsService userBankDetailsService;

    @Autowired
    UserBankDetailsController userBankDetailsController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userBankDetailsController).build();

        userBankDetailsModel = new UserBankDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore",
                59109876543211L, "HDFC0000036");
        userBankDetailsModelList.add(userBankDetailsModel);

    }

    @Test
    void addBankDetails() throws Exception {
        String content = objectMapper.writeValueAsString(userBankDetailsModel);

        when(userBankDetailsService.addBankDetails(userBankDetailsModel)).thenReturn(userBankDetailsModel);

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
        when(userBankDetailsService.getBankDetails(userBankDetailsModel.getUserEmail())).thenReturn(userBankDetailsModel);

        mockMvc.perform(MockMvcRequestBuilders.get("/user-bankdetails/get/priyanshi.chaturvedi@nineleaps.com").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getAllDetails() throws Exception {
        when(userBankDetailsService.getAllDetails()).thenReturn(userBankDetailsModelList);

        mockMvc.perform(MockMvcRequestBuilders.get("/user-bankdetails/getall").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}