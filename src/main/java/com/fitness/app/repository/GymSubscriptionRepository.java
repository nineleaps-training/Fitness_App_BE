package com.fitness.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.fitness.app.entity.GymSubscriptionClass;

@Repository
@EnableMongoRepositories
public interface GymSubscriptionRepository extends MongoRepository<GymSubscriptionClass, String> {

}
