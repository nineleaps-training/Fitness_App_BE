package com.fitness.app.service;

import java.util.List;
import java.util.Optional;

import com.fitness.app.dao.RatingDAO;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.Rating;
import com.fitness.app.exception.DataNotFoundException;
import com.fitness.app.model.RatingRequestModel;
import com.fitness.app.repository.AddGymRepo;
import com.fitness.app.repository.RatingRepo;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class RatingService implements RatingDAO{
 
    private RatingRepo ratingRepo;

    private AddGymRepo gymRepo;

    // Initializing constructor
    /**
     * This constructor is used to initialize the repositories
     * 
     * @param ratingRepo2      - Rating Repository
     * @param addGymRepository - Gym Repository
     */
    @Autowired
    public RatingService(RatingRepo ratingRepo2, AddGymRepo addGymRepository) {
        this.ratingRepo = ratingRepo2;
        this.gymRepo = addGymRepository;
    }

    /**
     * This function is used to save the rating
     * 
     * @param rating - Actual Rating
     * @return - Rating
     */
    public Rating ratingService(RatingRequestModel rating) {

        log.info("RatingService >> ratingService >> Initiated");
        Rating rating2 = new Rating();
        rating2.setRate(rating.getRating());
        rating2.setRater(rating.getRater());
        rating2.setTarget(rating.getTarget());

        return ratingRepo.save(rating2); // Saving the rating

    }

    /**
     * This function is used to fetch the rating of the gym
     * 
     * @param target - Gym Id
     * @return - Rating
     * @throws DataNotFoundException
     */
    public Double getRating(String target) {
        log.info("RatingService >> getRating >> Initiated");
        List<Rating> ratings = ratingRepo.findByTarget(target);
        if (ratings.isEmpty()) {
            log.error("RatingService >> getRating >> Exception thrown");
            throw new DataNotFoundException("No Gym exists with the provided id");
        } else {
            int n = ratings.size();
            double rate = 0;
            for (Rating rating : ratings) {
                rate += rating.getRate();
            }
            rate = rate / n;
            rate = Math.round(rate * 100) / 100.0d;
            Optional<GymClass> optional = gymRepo.findById(target); // Fetching the rating of gym
            GymClass gym;
            if (optional.isPresent()) {
                gym = optional.get();
                gym.setRating(rate);
                gymRepo.save(gym);
            }
            log.info("RatingService >> getRating >> end");
            return rate % 6;
        }
    }

    /**
     * This function is used to fetch the rating of the user
     * 
     * @param email - Email id of the user
     * @return - Rating
     * @throws DataNotFoundException
     */
    public Double getRatingOfPerson(String email) {
        log.info("RatingService >> getRatingOfPerson >> Initiated");
        List<Rating> ratings = ratingRepo.findByTarget(email); // Fetching the rating of User
        if (ratings.isEmpty()) {
            log.error("RatingService >> getRatingOfPerson >> Exception thrown");
            throw new DataNotFoundException("No User exists with the provided email");
        } else {
            int size = ratings.size();
            double r = 0;
            for (Rating rating : ratings) {
                r += rating.getRate();
            }
            r = r / size;
            r = Math.round(r * 100) / 100.0d;
            log.info("RatingService >> getRatingOfPerson >> end");
            return r % 6;
        }
    }
}
