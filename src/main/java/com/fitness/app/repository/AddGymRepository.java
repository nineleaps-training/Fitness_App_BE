package com.fitness.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.fitness.app.entity.GymClass;

@Repository
@EnableMongoRepositories
public interface AddGymRepository extends MongoRepository<GymClass, String> {

	public List<GymClass> findByEmail(String email);

	public GymClass findByName(String name);

}
