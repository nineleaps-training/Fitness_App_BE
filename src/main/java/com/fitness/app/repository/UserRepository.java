package com.fitness.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;


import com.fitness.app.entity.UserClass;

@Repository
@EnableMongoRepositories
public interface UserRepository extends MongoRepository<UserClass, String> {
	
	
	public UserClass findByEmail(String email); 
}
