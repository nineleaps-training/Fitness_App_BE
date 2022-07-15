package com.register.app.service;

import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.Rating;
import com.fitness.app.model.RatingModel;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.RatingRepo;
import com.fitness.app.service.RatingService;
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
class RatingServiceTest {


    @Mock
    private RatingRepo ratingRepo;

    @Mock
    private AddGymRepository gymRepository;


    @InjectMocks
    private RatingService ratingService;



    List<String> workout=new ArrayList<>(Arrays.asList("Zumba", "Dance", "Running"));
    long contact=7651977515L;
    GymClass FITNESS1=new GymClass(
            "GM1",
            "Manish",
            "Fitness Center",
            workout,
            contact,
            4.0,
            100
    );
    Optional<GymClass> gymClass= Optional.of(FITNESS1);
    Rating RATING_USER=new Rating("ID","Rahul", "Manish",4.0);
    Rating RATING_GYM=new Rating("ID","GM1", "Rahul",4.0);


    RatingModel RATING_USER_MODEL=new RatingModel("Rahul", "Manish",4.0);
    RatingModel RATING_GYM_MODEL=new RatingModel("GM1", "Rahul",4.0);



    @Test
     void ratingService()
    {
        Rating returnedRate=ratingService.ratingService(RATING_USER_MODEL);
        Assertions.assertNotNull(returnedRate);
        Assertions.assertEquals(returnedRate.getRate(), RATING_GYM_MODEL.getRate());
    }

   @Test
    void getRating()
   {
       List<Rating> ratingList=new ArrayList<>(Arrays.asList(RATING_USER));

       Mockito.when(ratingRepo.findByTarget(RATING_USER.getTarget())).thenReturn(ratingList);
       Mockito.when(gymRepository.findById(RATING_USER.getTarget())).thenReturn(gymClass);
       Double returnRate=ratingService.getRating(RATING_USER.getTarget());
       Assertions.assertNotNull(returnRate);
       Assertions.assertEquals(returnRate.floatValue(), RATING_USER.getRate());

   }


   @Test
   @DisplayName("Rating getting test for null rating list:")
     void getRatingNullValue()
    {
        List<Rating> ratingList=null;
        Mockito.when(ratingRepo.findByTarget(RATING_USER.getTarget())).thenReturn(ratingList);
        Double returnRate=ratingService.getRating(RATING_USER.getTarget());
        Assertions.assertNotNull(returnRate);
        Assertions.assertEquals(0.0, returnRate.floatValue());

    }

   @Test
     void getRatingOfPerson()
   {
       List<Rating> ratingList=new ArrayList<>(Arrays.asList(RATING_USER));

       Mockito.when(ratingRepo.findByTarget(RATING_USER.getTarget())).thenReturn(ratingList);
       Double ratingVal=ratingService.getRatingOfPerson(RATING_USER.getTarget());

       Assertions.assertNotNull(ratingVal);
       Assertions.assertEquals(ratingVal.floatValue(), RATING_USER.getRate());
   }


    @Test
    void getRatingOfPersonWIthNullRating()
    {
        List<Rating> ratingList=null;

        Mockito.when(ratingRepo.findByTarget(RATING_USER.getTarget())).thenReturn(ratingList);
        Double ratingVal=ratingService.getRatingOfPerson(RATING_USER.getTarget());

        Assertions.assertNotNull(ratingVal);
        Assertions.assertEquals(0.0, ratingVal.floatValue());
    }



}
