package com.fitness.app.service;

import java.util.List;
import java.util.Optional;

import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.Rating;
import com.fitness.app.model.RatingModel;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.RatingRepo;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
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
    	
        ratingRepo.save(rtt);
        return rtt;
        
    }
    public Double getRating(String target)
    {
        List<Rating> ratings = ratingRepo.findByTarget(target);
        System.out.println(ratings);
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
            GymClass gym=gymRepo.findById(target).get();
    System.out.println(gym);
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
