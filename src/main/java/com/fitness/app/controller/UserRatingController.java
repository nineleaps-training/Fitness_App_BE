package com.fitness.app.controller;


import com.fitness.app.entity.RatingClass;
import com.fitness.app.model.RatingModel;
<<<<<<< HEAD
import com.fitness.app.service.RatingService;
=======
import com.fitness.app.service.RatingServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRatingController {

    @Autowired
<<<<<<< HEAD
    private RatingService ratingService;
=======
    private RatingServiceImpl ratingServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
    //Rating Controller for vendor, user and gym
    @PostMapping("/rating")
    public RatingClass rateVendor(@RequestBody RatingModel rating)
    {
<<<<<<< HEAD
        return ratingService.ratingService(rating);
=======
        return ratingServiceImpl.ratingService(rating);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
    }
    //Fetching the rating of the gym by gymId
    @GetMapping("/get-rating/{gymId}")
    public Double getRating(@PathVariable String gymId) throws myExc
    {
    	try {
<<<<<<< HEAD
        return ratingService.getRating(gymId);
=======
        return ratingServiceImpl.getRating(gymId);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
    	}
    	catch (Exception e) {
			throw new myExc("NO value found: ");
		}
    }
    //Fetching the rating of the user by email id
    @GetMapping("/get-rating-person/{email}")
    public Double getRatingOfPerson(@PathVariable String email)
    {
<<<<<<< HEAD
        return ratingService.getRatingOfPerson(email);
=======
        return ratingServiceImpl.getRatingOfPerson(email);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
    }
    
}


class  myExc extends Exception{
    public myExc(String e)
    {
        super(e);
    }
}