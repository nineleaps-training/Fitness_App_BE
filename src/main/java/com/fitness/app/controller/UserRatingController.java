package com.fitness.app.controller;


import com.fitness.app.entity.Rating;
import com.fitness.app.service.RatingService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRatingController {

    @Autowired
    private RatingService ratingService;
    //Rating Controller for vendor, user and gym
    @PostMapping("/rating")
    public Rating rateVendor(@RequestBody Rating rating)
    {
        return ratingService.ratingService(rating);
    }
    
    @GetMapping("/get-rating/{gymId}")
    public Double getRating(@PathVariable String gymId) throws Exception
    {
    	try {
        return ratingService.getRating(gymId);
    	}
    	catch (Exception e) {
			throw new Exception("NO value found: ");
		}
    }
    
    @GetMapping("/get-rating-person/{email}")
    public Double getRatingOfPerson(@PathVariable String email)
    {
        return ratingService.getRatingOfPerson(email);
    }
    
}
