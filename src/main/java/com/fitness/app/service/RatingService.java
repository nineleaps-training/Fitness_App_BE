package com.fitness.app.service;

import java.util.List;
import java.util.Optional;

import com.fitness.app.entity.GymClass;
import com.fitness.app.model.RatingRequestModel;
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

    public RatingService(RatingRepo ratingRepo2, AddGymRepository addGymRepository) {
        this.ratingRepo=ratingRepo2;
        this.gymRepo=addGymRepository;
    }
    public RatingRequestModel ratingService(RatingRequestModel rating)
    {
    	
        return ratingRepo.save(rating);  
        
    }
    public Double getRating(String target)
    {
        List<RatingRequestModel> ratings = ratingRepo.findByTarget(target);
        if(ratings==null)
        {
            return 0.0;
        }
        else
        {
        	int n=ratings.size();
            double rate=0;
            for (RatingRequestModel rating : ratings) {
                rate+=rating.getRating();
            }
            rate=rate/n;
            rate=Math.round(rate* 100) / 100.0d;
            Optional<GymClass> optional=gymRepo.findById(target);
            GymClass gym;
		    if(optional.isPresent())
            {
                gym=optional.get();
                gym.setRating(rate);
                gymRepo.save(gym);
            }
            return rate%6;
        }
    }
    
    public Double getRatingOfPerson(String email)
    {
        List<RatingRequestModel> ratings = ratingRepo.findByTarget(email);
        if(ratings==null)
        {
            return 0.0;
        }
        else
        {
        	int n=ratings.size();
            double rate=0;
            for (RatingRequestModel rating : ratings) {
                rate+=rating.getRating();
            }
            rate=rate/n;
            rate=Math.round(rate* 100) / 100.0d;
          
            return rate%6;
        }
    }
}
