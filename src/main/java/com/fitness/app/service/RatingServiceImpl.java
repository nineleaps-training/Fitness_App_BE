package com.fitness.app.service;

import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.RatingClass;
import com.fitness.app.model.RatingModel;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RatingServiceImpl implements RatingService {


    final private RatingRepository ratingRepository;
    final private AddGymRepository gymRepo;

    @Override
    public RatingClass ratingService(RatingModel rating) {

        RatingClass rtt = new RatingClass();
        rtt.setTarget(rating.getRateTaget());
        rtt.setRater(rating.getRateRatter());
        rtt.setRate(rating.getRate());
        ratingRepository.save(rtt);
        return rtt;

    }

    @Override
    public Double getRating(String target) {
        List<RatingClass> ratingClasses = ratingRepository.findByTarget(target);
        System.out.println(ratingClasses);
        if (ratingClasses == null) {
            return 0.0;
        } else {
            int n = ratingClasses.size();
            double rate = 0;
            for (RatingClass ratingClass : ratingClasses) {
                rate += ratingClass.getRate();
            }
            rate = rate / n;
            rate = Math.round(rate * 100) / 100.0d;
            GymClass gym = gymRepo.findById(target).get();
            System.out.println(gym);
            gym.setRating(rate);
            gymRepo.save(gym);
            return rate % 6;
        }
    }

    @Override
    public Double getRatingOfPerson(String email) {
        List<RatingClass> ratingClasses = ratingRepository.findByTarget(email);
        if (ratingClasses == null) {
            return 0.0;
        } else {
            int n = ratingClasses.size();
            double rate = 0;
            for (RatingClass ratingClass : ratingClasses) {
                rate += ratingClass.getRate();
            }
            rate = rate / n;
            rate = Math.round(rate * 100) / 100.0d;

            return rate % 6;
        }
    }
}
