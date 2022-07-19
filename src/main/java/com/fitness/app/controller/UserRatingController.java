package com.fitness.app.controller;


import com.fitness.app.entity.RatingClass;
import com.fitness.app.model.RatingModel;

import com.fitness.app.service.RatingServiceImpl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRatingController {

    @Autowired

    private RatingServiceImpl ratingServiceImpl;

    //Rating Controller for vendor, user and gym
    @PostMapping("/rating")
    public RatingClass rateVendor(@RequestBody RatingModel rating)
    {

        return ratingServiceImpl.ratingService(rating);

    }
    //Fetching the rating of the gym by gymId
    @GetMapping("/get-rating/{gymId}")
    public Double getRating(@PathVariable String gymId) throws myExc
    {
    	try {

        return ratingServiceImpl.getRating(gymId);

    	}
    	catch (Exception e) {
			throw new myExc("NO value found: ");
		}
    }
    //Fetching the rating of the user by email id
    @GetMapping("/get-rating-person/{email}")
    public Double getRatingOfPerson(@PathVariable String email)
    {

        return ratingServiceImpl.getRatingOfPerson(email);

    }
    
}


class  myExc extends Exception{
    public myExc(String e)
    {
        super(e);
    }
}