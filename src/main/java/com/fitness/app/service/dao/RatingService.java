package com.fitness.app.service.dao;

import com.fitness.app.dto.requestDtos.RatingModel;


public interface RatingService {

    Boolean ratingService(RatingModel ratingModel);

    Double getRating(String target);

    Double getRatingOfPerson(String email);


}
