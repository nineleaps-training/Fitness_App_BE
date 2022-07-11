package com.fitness.app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import com.fitness.app.model.VendorBankDetailsRequestModel;
import com.fitness.app.service.VendorBankDetailsService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class VendorBankDetailsControllerTest {

    @InjectMocks
    VendorBankDetailsController vendorBankDetailsController;

    @Mock
    VendorBankDetailsService vendorBankDetailsService;
    long l=1234;
    VendorBankDetailsRequestModel vendorBankDetailsRequestModel=new VendorBankDetailsRequestModel("pankaj.jain@nineleaps.com","Pankaj Jain","ICICI","Bhatar",l,"IFSC0022","weeekly");
    ObjectMapper objectMapper=new ObjectMapper();
    MockMvc mockMvc;
    
    @BeforeEach
    public void setup()
    {
        this.mockMvc=MockMvcBuilders.standaloneSetup(vendorBankDetailsController).build();
    }

    @Test
    void testAddDetails() throws Exception {

        String content=objectMapper.writeValueAsString(vendorBankDetailsRequestModel);
        Mockito.when(vendorBankDetailsService.addDetails(vendorBankDetailsRequestModel)).thenReturn(vendorBankDetailsRequestModel);
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/vendor-bankdetails/add").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void testAddDetailsNull() throws Exception {

        String content=objectMapper.writeValueAsString(vendorBankDetailsRequestModel);
        Mockito.when(vendorBankDetailsService.addDetails(vendorBankDetailsRequestModel)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/vendor-bankdetails/add").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isBadRequest());

    }

    @Test
    void testGetBankDetails() throws Exception {

        Mockito.when(vendorBankDetailsService.getBankDetails("pankaj.jain@nineleaps.com")).thenReturn(vendorBankDetailsRequestModel);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/vendor-bankdetails/get/pankaj.jain@nineleaps.com").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


    }
}
