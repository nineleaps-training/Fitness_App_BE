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
import com.fitness.app.entity.VendorDetails;
import com.fitness.app.model.VendorDetailsRequestModel;
import com.fitness.app.service.VendorDetailsService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class VendorDetailsControllerTest {

    @InjectMocks
    VendorDetailsController vendorDetailsController;

    @Mock
    VendorDetailsService vendorDetailsService;

    VendorDetails vendorDetails;
    
    VendorDetailsRequestModel vendorDetailsRequestModel;
    
    ObjectMapper objectMapper=new ObjectMapper();

    ArrayList<VendorDetailsRequestModel> arrayList=new ArrayList<>();

    MockMvc mockMvc;
    
    @BeforeEach
    public void setup()
    {
        this.mockMvc=MockMvcBuilders.standaloneSetup(vendorDetailsController).build();
    }
    
    @Test
    void testAddVendorDetails() throws Exception {
        VendorDetailsRequestModel vendorDetailsRequestModel=new VendorDetailsRequestModel("pankaj.jain@nineleaps.com", "gender", "fullAddress", "city", 395007);
        ArrayList<VendorDetailsRequestModel> arrayList=new ArrayList<>();
        arrayList.add(vendorDetailsRequestModel);
        String content=objectMapper.writeValueAsString(vendorDetailsRequestModel);
        Mockito.when(vendorDetailsService.addVendorDetails(vendorDetailsRequestModel)).thenReturn(vendorDetailsRequestModel);
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/add/vendor-details").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void testAddVendorDetailsNull() throws Exception {
        VendorDetailsRequestModel vendorDetailsRequestModel=new VendorDetailsRequestModel("pankaj.jain@nineleaps.com", "gender", "fullAddress", "city", 396007);
        ArrayList<VendorDetailsRequestModel> arrayList=new ArrayList<>();
        arrayList.add(vendorDetailsRequestModel);
        String content=objectMapper.writeValueAsString(vendorDetailsRequestModel);
        Mockito.when(vendorDetailsService.addVendorDetails(vendorDetailsRequestModel)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/add/vendor-details").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isBadRequest());

    }

    @Test
    void testGetVendorDetails() throws Exception {

        Mockito.when(vendorDetailsService.getVendorDetails("pankaj.jain@nineleaps.com")).thenReturn(vendorDetailsRequestModel);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/vendor-details/pankaj.jain@nineleaps.com").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


    }
}
