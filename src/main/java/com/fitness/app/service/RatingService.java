package com.fitness.app.service;
import com.fitness.app.entity.RatingClass;
import com.fitness.app.model.RatingModel;


public interface RatingService {

     RatingClass ratingService(RatingModel ratingModel);
     Double getRating(String target);
     Double getRatingOfPerson(String email);

}
