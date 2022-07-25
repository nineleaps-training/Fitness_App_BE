package com.fitness.app.dao;

import org.springframework.stereotype.Component;

import com.fitness.app.entity.Rating;
import com.fitness.app.model.RatingRequestModel;

@Component
public interface RatingDAO {

    public Rating ratingService(RatingRequestModel rating);

    public Double getRating(String target);

    public Double getRatingOfPerson(String email);

}
