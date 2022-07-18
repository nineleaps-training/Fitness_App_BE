package com.fitness.app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import com.fitness.app.model.UserDetailsRequestModel;
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

    UserDetailsRequestModel vendorDetailsRequestModel;

    VendorDetails vendorDetails2;

    ObjectMapper objectMapper = new ObjectMapper();

    ArrayList<UserDetailsRequestModel> arrayList = new ArrayList<>();

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(vendorDetailsController).build();
    }

    @Test
    @DisplayName("Testing of adding the vendor details")
    void testAddVendorDetails() throws Exception {
        long l = 395007;
        UserDetailsRequestModel vendorDetailsRequestModel = new UserDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Male", "fullAddress", "city", 395007);
        VendorDetails vendorDetails = new VendorDetails("pankaj.jain@nineleaps.com", "Male", "fullAddress", "city", l);
        ArrayList<UserDetailsRequestModel> arrayList = new ArrayList<>();
        arrayList.add(vendorDetailsRequestModel);
        String content = objectMapper.writeValueAsString(vendorDetailsRequestModel);
        Mockito.when(vendorDetailsService.addVendorDetails(vendorDetailsRequestModel)).thenReturn(vendorDetails);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/add/vendor-details").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Testing of adding the vendor details with null values")
    void testAddVendorDetailsNull() throws Exception {
        UserDetailsRequestModel vendorDetailsRequestModel = new UserDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Male", "fullAddress", "city", 395007);
        VendorDetails vendorDetails = null;
        ArrayList<UserDetailsRequestModel> arrayList = new ArrayList<>();
        arrayList.add(vendorDetailsRequestModel);
        String content = objectMapper.writeValueAsString(vendorDetailsRequestModel);
        Mockito.when(vendorDetailsService.addVendorDetails(vendorDetailsRequestModel)).thenReturn(vendorDetails);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/add/vendor-details").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Testing of fetching the vendor details")
    void testGetVendorDetails() throws Exception {

        Mockito.when(vendorDetailsService.getVendorDetails("pankaj.jain@nineleaps.com")).thenReturn(vendorDetails2);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/vendor-details/pankaj.jain@nineleaps.com").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
