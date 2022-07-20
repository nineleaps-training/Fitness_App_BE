package com.fitness.app.service;

import com.fitness.app.dto.RatingModel;


public interface RatingService {

    Boolean ratingService(RatingModel ratingModel);

    Double getRating(String target);

    Double getRatingOfPerson(String email);


}
