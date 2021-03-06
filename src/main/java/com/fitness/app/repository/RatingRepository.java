package com.fitness.app.repository;

import java.util.List;

import com.fitness.app.entity.RatingClass;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableMongoRepositories
public interface RatingRepository extends MongoRepository<RatingClass,String> {
    public List<RatingClass> findByRate(double rate);
    public List<RatingClass> findByTarget(String target);

}

