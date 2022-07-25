package com.fitness.app.service;

import java.util.List;
import java.util.Optional;

import com.fitness.app.dao.RatingDao;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.Rating;
import com.fitness.app.model.RatingModel;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.RatingRepository;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RatingService implements RatingDao {

    private RatingRepository ratingRepository;

    private AddGymRepository gymRepo;

    @Autowired
    public RatingService(RatingRepository ratingRepository, AddGymRepository gymRepo) {
        this.ratingRepository = ratingRepository;
        this.gymRepo = gymRepo;
    }

    public Rating ratingService(RatingModel ratingModel) {
        log.info("RatingService >> ratingService >> Initiated");
        Rating rating = new Rating();
        rating.setRid(ratingModel.getRid());
        rating.setRate(ratingModel.getRate());
        rating.setRater(ratingModel.getRater());
        rating.setTarget(ratingModel.getTarget());
        ratingRepository.save(rating);
        log.info("RatingService >> ratingService >> Ends");
        return rating;

    }

    public Double getRating(String target) {
        log.info("RatingService >> getRating >> Initiated");
        List<Rating> ratings = ratingRepository.findByTarget(target);
        GymClass gym = new GymClass();
        if (ratings == null) {
            log.warn("RatingService >> getRating >> returns 0");
            return 0.0;
        } else {
            int n = ratings.size();
            double rate = 0;
            for (Rating rating : ratings) {
                rate += rating.getRate();
            }
            rate = rate / n;
            rate = Math.round(rate * 100) / 100.0d;

            Optional<GymClass> optional = gymRepo.findById(target);
            if (optional.isPresent()) {
                gym = optional.get();
            }
            gym.setRate(rate);
            gymRepo.save(gym);
            log.info("RatingService >> getRating >> Ends");
            return rate % 6;
        }
    }

    public Double getRatingOfPerson(String email) {
        log.info("RatingService >> getRatingOfPerson >> Initiated");
        List<Rating> ratingsOfPerson = ratingRepository.findByTarget(email);
        if (ratingsOfPerson == null) {
            log.warn("RatingService >> getRatingOfPerson >> returns 0");
            return 0.0;
        } else {
            int size = ratingsOfPerson.size();
            double rate = 0;
            for (Rating rating : ratingsOfPerson) {
                rate += rating.getRate();
            }
            rate = rate / size;
            rate = Math.round(rate * 100) / 100.0d;
            log.info("RatingService >> getRatingOfPerson >> Ends");
            return rate % 6;
        }
    }
}
