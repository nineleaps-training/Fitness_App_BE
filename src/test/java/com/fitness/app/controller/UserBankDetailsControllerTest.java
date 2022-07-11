package com.fitness.app.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.entity.UserBankDetails;
import com.fitness.app.model.UserBankDetailsRequestModel;
import com.fitness.app.service.UserBankDetailsService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserBankDetailsControllerTest {

    @InjectMocks
    UserBankDetailsController userBankDetailsController;

    @Mock
    UserBankDetailsService userBankDetailsService;

    UserBankDetailsRequestModel userBankDetailsRequestModel=new UserBankDetailsRequestModel("pankaj.jain@nineleaps.com","Pankaj Jain","ICICI","Bhatar",1234L,"IFSC0022");
    
    ObjectMapper objectMapper=new ObjectMapper();
    MockMvc mockMvc;
    UserBankDetails userBankDetails;

    @BeforeEach
    public void setup()
    {
        this.mockMvc=MockMvcBuilders.standaloneSetup(userBankDetailsController).build();
    }

    @Test
    void testAddBankDetails() throws Exception {

        String content=objectMapper.writeValueAsString(userBankDetailsRequestModel);
        Mockito.when(userBankDetailsService.addBankDetails(userBankDetailsRequestModel)).thenReturn(userBankDetailsRequestModel);
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/user-bankdetails/add").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());


    }

    @Test
    void testAddBankDetailsNull() throws Exception {

        String content=objectMapper.writeValueAsString(userBankDetailsRequestModel);
        Mockito.when(userBankDetailsService.addBankDetails(userBankDetailsRequestModel)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/user-bankdetails/add").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isBadRequest());

    }

    @Test
    void testGetAllDetails() throws Exception {
        List<UserBankDetailsRequestModel> list=new ArrayList<>();
        list.add(userBankDetailsRequestModel);
        Mockito.when(userBankDetailsService.getAllDetails()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/user-bankdetails/getall").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void testGetBankDetails() throws Exception {

        Mockito.when(userBankDetailsService.getBankDetails("pankaj.jain@nineleaps.com")).thenReturn(userBankDetailsRequestModel);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/user-bankdetails/get/pankaj.jain@nineleaps.com").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


    }
}