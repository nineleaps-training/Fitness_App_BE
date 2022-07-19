package com.fitness.app.service;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.Rating;
import com.fitness.app.model.RatingRequestModel;
import com.fitness.app.repository.AddGymRepo;
import com.fitness.app.repository.RatingRepo;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    RatingRepo ratingRepo;

    @Mock
    AddGymRepo addGymRepository;

    @InjectMocks
    RatingService ratingService;

    RatingRequestModel ratingRequestModel;
    List<RatingRequestModel> ratingRequestModels;
    Rating rating;
    List<Rating> ratings;
    String target;

    @Test
    @DisplayName("Testing of fetching the rating of the gym")
    void testGetRating() {
        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        long l = 1234;
        Optional<GymClass> optional = Optional.of(new GymClass("id", "email", "name", workoutList, l, 3.5, 20));
        ratingRequestModel = new RatingRequestModel("target", "user", 4.2);
        ratingRequestModels = new ArrayList<>();
        ratingRequestModels.add(ratingRequestModel);
        rating = new Rating("id", "target", "user", 4.2);
        ratings = new ArrayList<>();
        ratings.add(rating);
        when(addGymRepository.findById(ratingRequestModel.getTarget())).thenReturn(optional);
        when(ratingRepo.findByTarget(ratingRequestModel.getTarget())).thenReturn(ratings);
        Assertions.assertEquals(ratingRequestModel.getRating(),
                ratingService.getRating(ratingRequestModel.getTarget()));

    }

    @Test
    @DisplayName("Testing of fetching the rating of the gym when Optional is empty")
    void testGetRatingwithEmpty() {
        ratingRequestModel = new RatingRequestModel();
        ratingRequestModels = new ArrayList<>();
        ratingRequestModels.add(ratingRequestModel);
        when(ratingRepo.findByTarget(ratingRequestModel.getTarget())).thenReturn(Collections.emptyList());
        try {
            ratingService.getRating(ratingRequestModel.getTarget());
        } catch (Exception e) {
            Assertions.assertEquals("No Gym exists with the provided id", e.getMessage());
        }

    }

    @Test
    @DisplayName("Testing of fetching the rating of the gym when returned null")
    void testGetRatingOptionalwithNull() {
        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        Optional<GymClass> optional = Optional.empty();
        ratingRequestModel = new RatingRequestModel("target", "user", 4.2);
        rating = new Rating("id", "target", "user", 4.2);
        ratings = new ArrayList<>();
        ratings.add(rating);
        ratingRequestModels = new ArrayList<>();
        ratingRequestModels.add(ratingRequestModel);
        when(addGymRepository.findById(ratingRequestModel.getTarget())).thenReturn(optional);
        when(ratingRepo.findByTarget(ratingRequestModel.getTarget())).thenReturn(ratings);
        Assertions.assertEquals(ratingRequestModel.getRating(),
                ratingService.getRating(ratingRequestModel.getTarget()));

    }

    @Test
    @DisplayName("Testing of fetching the rating of the user")
    void testGetRatingOfPerson() {
        ratingRequestModel = new RatingRequestModel("4", "user", 4.2);
        ratingRequestModels = new ArrayList<>();
        ratingRequestModels.add(ratingRequestModel);
        rating = new Rating("12ab", "4", "user", 4.2);
        ratings = new ArrayList<>();
        ratings.add(rating);
        when(ratingRepo.findByTarget(ratingRequestModel.getTarget())).thenReturn(ratings);
        Assertions.assertEquals(ratingRequestModel.getRating(),
                ratingService.getRatingOfPerson(ratingRequestModel.getTarget()));

    }

    @Test
    @DisplayName("Testing of fetching the rating of the user when null returned")
    void testGetRatingOfPersonwithNull() {
        ratingRequestModel = new RatingRequestModel();
        ratingRequestModels = new ArrayList<>();
        ratingRequestModels.add(ratingRequestModel);
        when(ratingRepo.findByTarget(ratingRequestModel.getTarget())).thenReturn(Collections.emptyList());
        try {
            ratingService.getRatingOfPerson(ratingRequestModel.getTarget());
        } catch (Exception e) {
            Assertions.assertEquals("No User exists with the provided email", e.getMessage());
        }
    }

    @Test
    @DisplayName("Testing of rating of the user")
    void testRatingService() {

        ratingRequestModel = new RatingRequestModel("4", "user", 4.2);
        rating = new Rating(null, "4", "user", 4.2);
        when(ratingRepo.save(rating)).thenReturn(rating);
        Assertions.assertEquals(rating.getTarget(), ratingService.ratingService(ratingRequestModel).getTarget());

    }
}
