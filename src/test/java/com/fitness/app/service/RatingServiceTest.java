package com.fitness.app.service;

import com.fitness.app.entity.GymClass;
import com.fitness.app.model.RatingModel;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.RatingRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RatingServiceTest {

    @MockBean
    private RatingRepo ratingRepo;

    @MockBean
    private AddGymRepository gymRepo;

    @Autowired
    RatingService ratingService;


    @Test
    void ratingService() {
        RatingModel ratingModel = new RatingModel("1", "4", "Priyanshi", 4.2);

        RatingModel actual = ratingService.ratingService(ratingModel);
        assertEquals(ratingModel, actual);
    }

    @Test
    void returnRatingOfGym() {
        RatingModel ratingModel = new RatingModel("1", "4", "Priyanshi", 4.2);
        List<RatingModel> ratings = new ArrayList<>();
        ratings.add(ratingModel);

        List<String> workout = new ArrayList<>();
        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);

        when(gymRepo.findById(ratingModel.getTarget())).thenReturn(Optional.of(gymClass));
        when(ratingRepo.findByTarget(ratingModel.getTarget())).thenReturn(ratings);

        Double actual = ratingService.getRating(ratingModel.getTarget());
        assertEquals(ratingModel.getRate(), actual);
    }

    @Test
    void returnNullIfRatingModelIsNullForGym() {
        RatingModel ratingModel = new RatingModel();
        List<RatingModel> ratings = new ArrayList<>();

        when(ratingRepo.findByTarget(ratingModel.getTarget())).thenReturn(null);

        Double actual = ratingService.getRating(ratingModel.getTarget());
        assertEquals(0.0, actual);

    }

    @Test
    void returnRatingOfGymIfOptionalIsPresent() {
        RatingModel ratingModel = new RatingModel("1", "4", "Priyanshi", 4.2);
        List<RatingModel> ratings = new ArrayList<>();
        ratings.add(ratingModel);

        List<String> workout = new ArrayList<>();
        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);

        Optional<GymClass> optional = Optional.of(new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100));

        when(ratingRepo.findByTarget(ratingModel.getTarget())).thenReturn(ratings);
        when(gymRepo.findById(ratingModel.getTarget())).thenReturn(optional);

        Double actual = ratingService.getRating(ratingModel.getTarget());
        assertEquals(ratingModel.getRate(), actual);
    }

    @Test
    void returnRatingOfPerson() {
        RatingModel ratingModel = new RatingModel("1", "4", "Priyanshi", 4.2);
        List<RatingModel> ratings = new ArrayList<>();
        ratings.add(ratingModel);

        when(ratingRepo.findByTarget(ratingModel.getTarget())).thenReturn(ratings);

        Double actual = ratingService.getRatingOfPerson(ratingModel.getTarget());
        assertEquals(ratingModel.getRate(), actual);

    }

    @Test
    void returnNullIfRatingModelIsNullForPerson() {
        RatingModel ratingModel = new RatingModel();
        List<RatingModel> ratings = new ArrayList<>();

        when(ratingRepo.findByTarget(ratingModel.getTarget())).thenReturn(null);

        Double actual = ratingService.getRatingOfPerson(ratingModel.getTarget());
        assertEquals(0.0, actual);

    }
}