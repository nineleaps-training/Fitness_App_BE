package com.fitness.app.service;

import java.util.List;

import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.Rating;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.RatingRepo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    private RatingRepo ratingRepo;
    
    @Autowired
    private AddGymRepository gymRepo;

    public Rating ratingService(Rating rating)
    {
    	
        return ratingRepo.save(rating);  
        
    }
    public Double getRating(String target)
    {
        List<Rating> ratings = ratingRepo.findByTarget(target);
        if(ratings==null)
        {
            return 0.0;
        }
        else
        {
        	int n=ratings.size();
            double rate=0;
            for (Rating rating : ratings) {
                rate+=rating.getRate();
            }
            rate=rate/n;
            rate=Math.round(rate* 100) / 100.0d;
            GymClass gym =gymRepo.findById(target).get();
            gym.setRating(rate);
            gymRepo.save(gym);
            return rate%6;
        }
    }
    
    public Double getRatingOfPerson(String email)
    {
        List<Rating> ratings = ratingRepo.findByTarget(email);
        if(ratings==null)
        {
            return 0.0;
        }
        else
        {
        	int n=ratings.size();
            double rate=0;
            for (Rating rating : ratings) {
                rate+=rating.getRate();
            }
            rate=rate/n;
            rate=Math.round(rate* 100) / 100.0d;
          
            return rate%6;
        }
    }
}
