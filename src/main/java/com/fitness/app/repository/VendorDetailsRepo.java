package com.fitness.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.fitness.app.entity.VendorDetails;

@EnableMongoRepositories
@Repository
public interface VendorDetailsRepo extends MongoRepository<VendorDetails, String> {

	public VendorDetails findByVEmail(String email);
}