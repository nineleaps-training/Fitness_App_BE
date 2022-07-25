package com.fitness.app.service.dao;

import com.fitness.app.dto.request.RatingModel;


public interface RatingDao {

    Boolean ratingService(RatingModel ratingModel);

    Double getRating(String target);

    Double getRatingOfPerson(String email);


}
