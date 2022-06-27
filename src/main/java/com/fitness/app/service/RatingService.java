package com.fitness.app.service;

import java.util.List;
import java.util.Optional;

import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.Rating;
import com.fitness.app.model.RatingModel;
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

    public Rating ratingService(RatingModel rating)
    {
    	
    	Rating rtt=new Rating();
    	rtt.setTarget(rating.getRateTaget());
    	rtt.setRater(rating.getRateRatter());
    	rtt.setRate(rating.getRate());
    	
        return ratingRepo.save(rtt);  
        
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
            GymClass gym =new GymClass();
            Optional<GymClass>dataGym=gymRepo.findById(target);
            if(dataGym.isPresent())
            {
            	gym=dataGym.get();
            }
            else
            {
            	return 0.0; 
            }
           
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
