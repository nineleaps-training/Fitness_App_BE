package com.fitness.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.model.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mockMvc;

    @Autowired
    LocationController locationController;

    @BeforeEach
    public  void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();

    }

    @Test
    void getDetails() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/getLocation?address=Bangalore").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void getDetailsWhenResponseIsNull() throws Exception {
        Response response = null;

        mockMvc.perform(MockMvcRequestBuilders.get("/getLocation?address=Bangalore").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void getAddress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/get-fitness-center-by-location?latlng=12.934533,77.626579").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }
}