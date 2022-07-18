package com.fitness.app.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import com.fitness.app.entity.UserDetails;
import com.fitness.app.model.UserDetailsRequestModel;
import com.fitness.app.service.UserDetailsService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class UserDetailsControllerTest {

    @InjectMocks
    UserDetailsController userDetailsController;

    @Mock
    UserDetailsService userDetailsService;

    UserDetails userDetails;

    UserDetailsRequestModel userDetailsRequestModel;

    ObjectMapper objectMapper = new ObjectMapper();

    ArrayList<UserDetailsRequestModel> arrayList = new ArrayList<>();

    long l = 123;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userDetailsController).build();
    }

    @Test
    @DisplayName("Testing of adding the user details")
    void testAddUserDetails() throws Exception {

        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Male", "fullAddress", "city", 395007);
        UserDetails userDetails = new UserDetails("pankaj.jain@nineleaps.com", "Male", "fullAddress", "city", 395007);
        ArrayList<UserDetailsRequestModel> arrayList = new ArrayList<>();
        arrayList.add(userDetailsRequestModel);
        String content = objectMapper.writeValueAsString(userDetailsRequestModel);
        Mockito.when(userDetailsService.addUserDetails(userDetailsRequestModel)).thenReturn(userDetails);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/add/user-details").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Testing of adding the user details with null values")
    void testAddUserDetailsNull() throws Exception {

        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel("pankaj.jain@nineleaps.com",
                "Male", "fullAddress", "city", 395007);
        ArrayList<UserDetailsRequestModel> arrayList = new ArrayList<>();
        arrayList.add(userDetailsRequestModel);
        String content = objectMapper.writeValueAsString(userDetailsRequestModel);
        Mockito.when(userDetailsService.addUserDetails(userDetailsRequestModel)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/add/user-details").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Testing of fetching the user details")
    void testGetUserDetails() throws Exception {

        Mockito.when(userDetailsService.getUserDetails("pankaj.jain@nineleaps.com")).thenReturn(userDetails);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/user-details/pankaj.jain@nineleaps.com").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}