package com.fitness.app.repository;

import java.util.List;

import com.fitness.app.entity.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableMongoRepositories
public interface RatingRepo extends MongoRepository<Rating, String> {
    public List<Rating> findByRate(double rating);

    public List<Rating> findByTarget(String target);

}
