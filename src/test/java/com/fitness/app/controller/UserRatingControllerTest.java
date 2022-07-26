package com.fitness.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.entity.Rating;
import com.fitness.app.model.RatingModel;
import com.fitness.app.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UserRatingControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mockMvc;

    RatingModel ratingModel;
    Rating rating;

    @Mock
    private RatingService ratingService;

    @InjectMocks
    UserRatingController userRatingController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userRatingController).build();

        ratingModel = new RatingModel("1", "4", "Priyanshi", 4.2);

        rating = new Rating("1", "4", "Priyanshi", 4.2);

    }

    @Test
    void rateVendor() throws Exception {
        String content = objectMapper.writeValueAsString(ratingModel);

        when(ratingService.ratingService(ratingModel)).thenReturn(rating);

        mockMvc.perform(MockMvcRequestBuilders.post("/rating").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
    }

    @Test
    void getRating() throws Exception {
        when(ratingService.getRating(ratingModel.getTarget())).thenReturn(4.2);

        mockMvc.perform(MockMvcRequestBuilders.get("/get-rating/4").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getRatingOfPerson() throws Exception {
        when(ratingService.getRatingOfPerson(ratingModel.getTarget())).thenReturn(4.2);

        mockMvc.perform(MockMvcRequestBuilders.get("/get-rating-person/4").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}