package com.fitness.app.controller;

import static org.mockito.ArgumentMatchers.any;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.model.RatingRequestModel;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.RatingRepo;
import com.fitness.app.service.RatingService;

@ExtendWith(MockitoExtension.class)
class UserRatingControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    UserRatingController userRatingController;

    @Mock
    RatingService ratingService;

    @Mock
    RatingRepo ratingRepo;

    @Mock
    AddGymRepository addGymRepository;

    ObjectMapper objectMapper=new ObjectMapper();

    @BeforeEach
    public void setup()
    {
        this.mockMvc=MockMvcBuilders.standaloneSetup(userRatingController).build();
    }
    
    @Test
    void testGetRating() throws Exception {
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        RatingRequestModel ratingRequestModel=new RatingRequestModel("GM6", "target", "rater", 3.5);
        List<RatingRequestModel> ratingRequestModels=new ArrayList<>();
        ratingRequestModels.add(ratingRequestModel);
        Mockito.when(ratingService.getRating(ratingRequestModel.getRid())).thenReturn(any());
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/get-rating/GM6")).andExpect(status().isOk());


    }

    @Test
    void testGetRatingOfPerson() throws Exception {

        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");       
        RatingRequestModel ratingRequestModel=new RatingRequestModel("GM6", "target", "rater", 3.5);
        List<RatingRequestModel> ratingRequestModels=new ArrayList<>();
        ratingRequestModels.add(ratingRequestModel);
        Mockito.when(ratingService.getRatingOfPerson(ratingRequestModel.getTarget())).thenReturn(any());
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/get-rating-person/target")).andExpect(status().isOk());


    }

    @Test
    void testRateVendor() throws Exception {

        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        RatingRequestModel ratingRequestModel=new RatingRequestModel("GM6", "target", "rater", 3.5);
        List<RatingRequestModel> ratingRequestModels=new ArrayList<>();
        ratingRequestModels.add(ratingRequestModel);
        String content=objectMapper.writeValueAsString(ratingRequestModel);
        Mockito.when(ratingService.ratingService(ratingRequestModel)).thenReturn(ratingRequestModel);
        mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/rating").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isCreated());

    }
}
