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
import com.fitness.app.entity.UserBankDetails;
import com.fitness.app.model.UserBankDetailsRequestModel;
import com.fitness.app.repository.UserBankDetailsRepo;
import com.fitness.app.service.PagingService;
import com.fitness.app.service.UserBankDetailsService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserBankDetailsControllerTest {

    @InjectMocks
    UserBankDetailsController userBankDetailsController;

    @Mock
    UserBankDetailsService userBankDetailsService;

    @Mock
    UserBankDetailsRepo userBankDetailsRepo;

    @Mock
    PagingService pagingService;

    UserBankDetailsRequestModel userBankDetailsRequestModel = new UserBankDetailsRequestModel(
            "pankaj.jain@nineleaps.com", "Pankaj Jain", "ICICI", "Bhatar", 1234L, "IFSC0022", null);
    UserBankDetails userBankDetails2 = new UserBankDetails("pankaj.jain@nineleaps.com", "Pankaj Jain", "ICICI",
            "Bhatar", 1234L, "IFSC0022");

    ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mockMvc;
    UserBankDetails userBankDetails;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userBankDetailsController).build();
    }

    @Test
    @DisplayName("Testing of adding the User Bank Details")
    void testAddBankDetails() throws Exception {

        String content = objectMapper.writeValueAsString(userBankDetailsRequestModel);
        Mockito.when(userBankDetailsService.addBankDetails(userBankDetailsRequestModel)).thenReturn(userBankDetails2);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/user-bankdetails/add").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Testing of adding the User Bank Details with null values")
    void testAddBankDetailsNull() throws Exception {

        String content = objectMapper.writeValueAsString(userBankDetailsRequestModel);
        Mockito.when(userBankDetailsService.addBankDetails(userBankDetailsRequestModel)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/user-bankdetails/add").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Testing of fetching the user bank details")
    void testGetBankDetails() throws Exception {

        Mockito.when(userBankDetailsService.getBankDetails("pankaj.jain@nineleaps.com")).thenReturn(userBankDetails2);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/user-bankdetails/get/pankaj.jain@nineleaps.com").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testing of fetching the bank details of all the users")
    void testGetAllDetails() throws Exception {

        long l = 1234;
        UserBankDetails uBankDetails = new UserBankDetails("pankaj.jain@nineleaps.com", "Pankaj Jain", "ICICI",
                "Bhatar", l, "ICICI20002");
        List<UserBankDetails> userBankDetails = new ArrayList<>();
        userBankDetails.add(uBankDetails);
        Mockito.when(pagingService.getallDetails(0, 1)).thenReturn(userBankDetails);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/user-bankdetails/getall/0/1")).andExpect(status().isOk());
    }
}