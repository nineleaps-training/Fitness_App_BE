package com.fitness.app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fitness.app.model.Response;
import com.fitness.app.service.GymService;

@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    @InjectMocks
    LocationController locationController;

    @Mock
    GymService gymService;

    @Mock
    RestTemplate restTemplate;

    MockMvc mockMvc;

    @Mock
    Response response;

    @Mock
    UriComponentsBuilder uriComponentsBuilder;

    @Mock
    UriComponents uriComponents;

    @Mock
    Response response2;


    @BeforeEach
    public void setup()
    {
        this.mockMvc=MockMvcBuilders.standaloneSetup(locationController).build();
    }

    @Test
    void testGetAddress() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/get-fitness-center-by-location?latlng=27.345436,74.253456")).andExpect(status().isOk()); 

    }

    @Test
    void testGetDetails() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/getLocation?address=Nineleaps")).andExpect(status().isOk());
    }  
}
