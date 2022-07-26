package com.fitness.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.entity.VendorDetails;
import com.fitness.app.model.VendorDetailsModel;
import com.fitness.app.service.VendorDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
class VendorDetailsControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mockMvc;

    VendorDetailsModel vendorDetailsModel;
    List<VendorDetailsModel> vendorDetailsModelList = new ArrayList<>();
    VendorDetails vendorDetails;
    List<VendorDetails> vendorDetailsList = new ArrayList<>();


    @Mock
    VendorDetailsService vendorDetailsService;

    @InjectMocks
    VendorDetailsController vendorDetailsController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(vendorDetailsController).build();

        vendorDetailsModel = new VendorDetailsModel("priyanshi.chaturvedi@nineleaps.com",
                "Female", "80 Feet Road, Koramangala", "Bangalore", 560034L);
        vendorDetailsModelList.add(vendorDetailsModel);

        vendorDetails = new VendorDetails("priyanshi.chaturvedi@nineleaps.com",
                "Female", "80 Feet Road, Koramangala", "Bangalore", 560034L);
        vendorDetailsList.add(vendorDetails);


    }

    @Test
    void addVendorDetails() throws Exception {
        String content = objectMapper.writeValueAsString(vendorDetailsModel);

        when(vendorDetailsService.addVendorDetails(vendorDetailsModel)).thenReturn(vendorDetails);

        mockMvc.perform(MockMvcRequestBuilders.put("/add/vendor-details").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
    }

    @Test
    void doNotAddVendorDetailsIfVendorIsNull() throws Exception {
        VendorDetailsModel vendorDetailsModelNull = new VendorDetailsModel();

        String content = objectMapper.writeValueAsString(vendorDetailsModelNull);

        when(vendorDetailsService.addVendorDetails(vendorDetailsModelNull)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/add/vendor-details").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isBadRequest());
    }

    @Test
    void getVendorDetails() throws Exception {
        when(vendorDetailsService.getVendorDetails(vendorDetailsModel.getEmail())).thenReturn(vendorDetails);

        mockMvc.perform(MockMvcRequestBuilders.get("/vendor-details/priyanshi.chaturvedi@nineleaps.com").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}