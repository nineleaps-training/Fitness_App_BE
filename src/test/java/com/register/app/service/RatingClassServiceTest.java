package com.register.app.service;

import com.fitness.app.dto.RatingModel;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.RatingClass;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.RatingRepository;
import com.fitness.app.service.RatingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RatingClassServiceTest {


    @Mock

    private RatingRepository ratingRepository;


    @Mock
    private AddGymRepository gymRepository;


    @InjectMocks

    private RatingServiceImpl ratingServiceImpl;


    List<String> workout = new ArrayList<>(Arrays.asList("Zumba", "Dance", "Running"));
    long contact = 7651977515L;
    GymClass FITNESS1 = new GymClass(
            "GM1",
            "Manish",
            "Fitness Center",
            workout,
            contact,
            4.0,
            100
    );
    Optional<GymClass> gymClass = Optional.of(FITNESS1);
    RatingClass RATING_Class_USER = new RatingClass("ID", "Rahul", "Manish", 4.0);
    RatingClass RATING_Class_GYM = new RatingClass("ID", "GM1", "Rahul", 4.0);


    RatingModel RATING_USER_MODEL = new RatingModel("Rahul", "Manish", 4.0);
    RatingModel RATING_GYM_MODEL = new RatingModel("GM1", "Rahul", 4.0);


    @Test
    void ratingService() {


        Boolean returnedRate = ratingServiceImpl.ratingService(RATING_USER_MODEL);

        Assertions.assertNotNull(returnedRate);
        Assertions.assertEquals(returnedRate, RATING_GYM_MODEL.getRate());
    }

    @Test
    void getRating() {
        List<RatingClass> ratingClassList = new ArrayList<>(Arrays.asList(RATING_Class_USER));

        Mockito.when(ratingRepository.findByTarget(RATING_Class_USER.getTarget())).thenReturn(ratingClassList);
        Mockito.when(gymRepository.findById(RATING_Class_USER.getTarget())).thenReturn(gymClass);
        Double returnRate = ratingServiceImpl.getRating(RATING_Class_USER.getTarget());

        Assertions.assertNotNull(returnRate);
        Assertions.assertEquals(returnRate.floatValue(), RATING_Class_USER.getRate());

    }


    @Test
    @DisplayName("Rating getting test for null rating list:")
    void getRatingNullValue() {
        List<RatingClass> ratingClassList = null;

        Mockito.when(ratingRepository.findByTarget(RATING_Class_USER.getTarget())).thenReturn(ratingClassList);
        Double returnRate = ratingServiceImpl.getRating(RATING_Class_USER.getTarget());

        Assertions.assertNotNull(returnRate);
        Assertions.assertEquals(0.0, returnRate.floatValue());

    }

    @Test
    void getRatingOfPerson() {
        List<RatingClass> ratingClassList = new ArrayList<>(Arrays.asList(RATING_Class_USER));


        Mockito.when(ratingRepository.findByTarget(RATING_Class_USER.getTarget())).thenReturn(ratingClassList);
        Double ratingVal = ratingServiceImpl.getRatingOfPerson(RATING_Class_USER.getTarget());


        Assertions.assertNotNull(ratingVal);
        Assertions.assertEquals(ratingVal.floatValue(), RATING_Class_USER.getRate());
    }


    @Test
    void getRatingOfPersonWIthNullRating() {
        List<RatingClass> ratingClassList = null;


        Mockito.when(ratingRepository.findByTarget(RATING_Class_USER.getTarget())).thenReturn(ratingClassList);
        Double ratingVal = ratingServiceImpl.getRatingOfPerson(RATING_Class_USER.getTarget());


        Assertions.assertNotNull(ratingVal);
        Assertions.assertEquals(0.0, ratingVal.floatValue());
    }


}
