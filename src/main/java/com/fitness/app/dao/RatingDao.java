package com.fitness.app.dao;

import com.fitness.app.entity.Rating;
import com.fitness.app.model.RatingModel;

public interface RatingDao {

    Rating ratingService(RatingModel ratingModel);
    Double getRating(String target);
    Double getRatingOfPerson(String email);
}
