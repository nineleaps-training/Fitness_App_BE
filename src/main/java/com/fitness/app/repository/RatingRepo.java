package com.fitness.app.repository;

import java.util.List;


import com.fitness.app.model.RatingModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableMongoRepositories
public interface RatingRepo extends MongoRepository<RatingModel, String> {
    public List<RatingModel> findByRate(double rate);

    public List<RatingModel> findByTarget(String target);

}

