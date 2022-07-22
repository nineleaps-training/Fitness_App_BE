package com.fitness.app.service;

import com.fitness.app.dto.request.RatingModel;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.RatingClass;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.RatingRepository;
import com.fitness.app.service.dao.RatingDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Component
public class RatingDaoImpl implements RatingDao {


    final private RatingRepository ratingRepository;
    final private AddGymRepository gymRepo;

    @Override
    public Boolean ratingService(RatingModel rating) {

        RatingClass rtt = new RatingClass();
        rtt.setTarget(rating.getRateTarget());
        rtt.setRater(rating.getRateRatter());
        rtt.setRate(rating.getRate());
        ratingRepository.save(rtt);
        return true;

    }

    @Override
    public Double getRating(String target) {
        List<RatingClass> ratingClasses = ratingRepository.findByTarget(target);
        if (ratingClasses == null || ratingClasses.size() < 0) {
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
        if (ratingClasses == null || ratingClasses.size() < 0) {
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
