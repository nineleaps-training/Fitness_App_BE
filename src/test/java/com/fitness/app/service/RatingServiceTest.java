package com.fitness.app.service;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fitness.app.entity.GymClass;
import com.fitness.app.model.RatingRequestModel;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.RatingRepo;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    RatingRepo ratingRepo;

    @Mock
    AddGymRepository addGymRepository;

    RatingService ratingService;

    RatingRequestModel ratingRequestModel;
    List<RatingRequestModel> ratingRequestModels;
    String target;

    @BeforeEach
    public void initcase() {
        ratingService=new RatingService(ratingRepo,addGymRepository);
    }
    @Test
    void testGetRating() {
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        long l=1234;
        Optional<GymClass> optional=Optional.of(new GymClass("id", "email", "name", workoutList, l, 3.5, 20));
        ratingRequestModel=new RatingRequestModel("id","target","user",4.2);
        ratingRequestModels=new ArrayList<>();
        ratingRequestModels.add(ratingRequestModel);
        when(addGymRepository.findById(ratingRequestModel.getTarget())).thenReturn(optional);
        when(ratingRepo.findByTarget(ratingRequestModel.getTarget())).thenReturn(ratingRequestModels);
        Assertions.assertEquals(ratingRequestModel.getRating(), ratingService.getRating(ratingRequestModel.getTarget()));

    }

    @Test
    void testGetRatingwithNull() {
        ratingRequestModel=new RatingRequestModel();
        ratingRequestModels=new ArrayList<>();
        ratingRequestModels.add(ratingRequestModel);
        when(ratingRepo.findByTarget(ratingRequestModel.getTarget())).thenReturn(null);
        Assertions.assertEquals(ratingRequestModel.getRating(), ratingService.getRating(ratingRequestModel.getTarget()));

    }

    @Test

    void testGetRatingOptionalwithNull() {
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        Optional<GymClass> optional=Optional.empty();
        ratingRequestModel=new RatingRequestModel("id","target","user",4.2);
        ratingRequestModels=new ArrayList<>();
        ratingRequestModels.add(ratingRequestModel);
        when(addGymRepository.findById(ratingRequestModel.getTarget())).thenReturn(optional);
        when(ratingRepo.findByTarget(ratingRequestModel.getTarget())).thenReturn(ratingRequestModels);
        Assertions.assertEquals(ratingRequestModel.getRating(), ratingService.getRating(ratingRequestModel.getTarget()));

    }

    @Test
    void testGetRatingOfPerson() {
        ratingRequestModel=new RatingRequestModel("12ab","4","user",4.2);
        ratingRequestModels=new ArrayList<>();
        ratingRequestModels.add(ratingRequestModel);
        when(ratingRepo.findByTarget(ratingRequestModel.getTarget())).thenReturn(ratingRequestModels);
        Assertions.assertEquals(ratingRequestModel.getRating(), ratingService.getRatingOfPerson(ratingRequestModel.getTarget()));
        
    }

    @Test
    void testGetRatingOfPersonwithNull() {
        ratingRequestModel=new RatingRequestModel();
        ratingRequestModels=new ArrayList<>();
        ratingRequestModels.add(ratingRequestModel);
        when(ratingRepo.findByTarget(ratingRequestModel.getTarget())).thenReturn(null);
        Assertions.assertEquals(ratingRequestModel.getRating(), ratingService.getRatingOfPerson(ratingRequestModel.getTarget()));   
    }

    @Test
    void testRatingService() {

        ratingRequestModel = new RatingRequestModel("12ab","4","user",4.2);
        when(ratingRepo.save(ratingRequestModel)).thenReturn(ratingRequestModel);
        Assertions.assertEquals(ratingRequestModel.getRid(), ratingService.ratingService(ratingRequestModel).getRid());

    }
}
