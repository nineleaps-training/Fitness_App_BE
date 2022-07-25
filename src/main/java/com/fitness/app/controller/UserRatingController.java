package com.fitness.app.controller;


import com.fitness.app.dao.RatingDao;
import com.fitness.app.entity.Rating;
import com.fitness.app.model.RatingModel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@Validated
public class UserRatingController {

    @Autowired
    private RatingDao ratingDao;

    //Rating Controller for vendor, user and gym
    @PostMapping("/rating")
    @Validated
    public Rating rateVendor(@Valid @RequestBody RatingModel rating) {

        return ratingDao.ratingService(rating);
    }

    //Fetching the rating of the gym by gymId
    @GetMapping("/get-rating/{gymId}")
    public Double getRating(@NotBlank @NotNull @NotEmpty @PathVariable String gymId) {

        return ratingDao.getRating(gymId);

    }

    //Fetching the rating of the user by email id
    @GetMapping("/get-rating-person/{email}")
    public Double getRatingOfPerson(@NotBlank @NotNull @NotEmpty @PathVariable String email) {

        return ratingDao.getRatingOfPerson(email);
    }

}
