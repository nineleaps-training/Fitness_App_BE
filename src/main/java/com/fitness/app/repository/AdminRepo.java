package com.fitness.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitness.app.entity.AdminClass;

@Repository
public interface AdminRepo extends MongoRepository<AdminClass, String> {

}
