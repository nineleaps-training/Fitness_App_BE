package com.fitness.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.entity.VendorBankDetails;
import com.fitness.app.model.VendorBankDetailsModel;
import com.fitness.app.service.VendorBankDetailsService;
import com.fitness.app.service.VendorDetailsService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VendorBankDetailsControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mockMvc;

    VendorBankDetailsModel vendorBankDetailsModel;
    List<VendorBankDetailsModel> vendorBankDetailsModelList = new ArrayList<>();

    VendorBankDetails vendorBankDetails;
    List<VendorBankDetails> vendorBankDetailsList = new ArrayList<>();

    @MockBean
    VendorBankDetailsService vendorBankDetailsService;

    @Autowired
    VendorBankDetailsController vendorBankDetailsController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(vendorBankDetailsController).build();

        vendorBankDetailsModel = new VendorBankDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore", 59109876543211L,
                "HDFC0000036", "Monthly");

        vendorBankDetails = new VendorBankDetails("priyanshi.chaturvedi@nineleaps.com",
                "Priyanshi", "HDFC Bank", "Bangalore", 59109876543211L,
                "HDFC0000036", "Monthly");

    }

    @Test
    void addDetails() throws Exception {
        String content = objectMapper.writeValueAsString(vendorBankDetailsModel);

        when(vendorBankDetailsService.addDetails(vendorBankDetailsModel)).thenReturn(vendorBankDetails);

        mockMvc.perform(MockMvcRequestBuilders.put("/vendor-bankdetails/add").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
    }

    @Test
    void doNotAddDetailsIfVendorIsNull() throws Exception {
        VendorBankDetailsModel vendorBankDetailsModelNull = new VendorBankDetailsModel();

        String content = objectMapper.writeValueAsString(vendorBankDetailsModelNull);

        when(vendorBankDetailsService.addDetails(vendorBankDetailsModelNull)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/vendor-bankdetails/add").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isBadRequest());
    }

    @Test
    void getBankDetails() throws Exception {
        when(vendorBankDetailsService.getBankDetails(vendorBankDetailsModel.getEmail())).thenReturn(vendorBankDetails);

        mockMvc.perform(MockMvcRequestBuilders.get("/vendor-bankdetails/get/priyanshi.chaturvedi@nineleaps.com").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getDetails() throws Exception {
        when(vendorBankDetailsService.getDetails(0, 1)).thenReturn(vendorBankDetailsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/vendor-bankdetails/getall/0/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}