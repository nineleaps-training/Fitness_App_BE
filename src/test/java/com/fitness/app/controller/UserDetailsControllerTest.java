package com.fitness.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.model.UserDetailsModel;
import com.fitness.app.service.UserDetailsService;
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
class UserDetailsControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mockMvc;

    UserDetailsModel userDetailsModel;

    List<UserDetailsModel> userDetailsModelList = new ArrayList<>();

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    UserDetailsController userDetailsController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userDetailsController).build();

        userDetailsModel = new UserDetailsModel("priyanshi.chaturvedi@nineleaps.com", "Female",
                "80 Feet Road, Koramangala", "Bangalore", 560034L);
        userDetailsModelList.add(userDetailsModel);

    }

    @Test
    void addUserDetails() throws Exception {
        String content = objectMapper.writeValueAsString(userDetailsModel);

        when(userDetailsService.addUserDetails(userDetailsModel)).thenReturn(userDetailsModel);

        mockMvc.perform(MockMvcRequestBuilders.put("/add/user-details").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void doNotAddUserDetailsIfUserIsNull() throws Exception {
        UserDetailsModel userDetailsModelNull = new UserDetailsModel();

        String content = objectMapper.writeValueAsString(userDetailsModelNull);

        when(userDetailsService.addUserDetails(userDetailsModelNull)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/add/user-details").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isBadRequest());

    }

    @Test
    void getUserDetails() throws Exception {
        when(userDetailsService.getUserDetails(userDetailsModel.getUserEmail())).thenReturn(userDetailsModel);

        mockMvc.perform(MockMvcRequestBuilders.get("/user-details/priyansi.chaturvedi@nineleaps.com").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }
}