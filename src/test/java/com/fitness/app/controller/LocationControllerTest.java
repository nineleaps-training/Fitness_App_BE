package com.fitness.app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fitness.app.model.Response;
import com.fitness.app.service.GymService;
import com.fitness.app.service.LocationService;

@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    @InjectMocks
    LocationController locationController;

    @Mock
    GymService gymService;

    @Mock
    RestTemplate restTemplate;

    @Mock
    LocationService locationService;

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
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();
    }

    @Test
    @DisplayName("Testing of fetching the gym address from it's latitude and longitude")
    void testGetAddress() throws Exception {
        
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("Bengaluru");
        Map<String,List<String>> address = new HashMap<>();
        address.put("data",list);
        Mockito.when(locationService.getAddress("27.345436,74.253456")).thenReturn(address);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/location/getFitnessCenterByLocation?latlng=27.345436,74.253456")).andExpect(status().isOk());

    }

    @Test
    @DisplayName("Testing of fetching the latitude and longitude from gym address")
    void testGetDetails() throws Exception {

        String address="Nineleaps Pasta Street";
        Mockito.when(locationService.getDetails(address)).thenReturn("12.930779,77.633151");
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/location/getLocation?address=Nineleaps Pasta Street")).andExpect(status().isOk());
    }
}
