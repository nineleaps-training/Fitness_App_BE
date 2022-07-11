package com.fitness.app.repository;

import java.util.List;
import com.fitness.app.model.RatingRequestModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableMongoRepositories
public interface RatingRepo extends MongoRepository<RatingRequestModel,String> {
    public List<RatingRequestModel> findByRating(double rating);
    public List<RatingRequestModel> findByTarget(String target);

}

