package com.fitness.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.fitness.app.model.VendorDetailsRequestModel;

@EnableMongoRepositories
@Repository
public interface VendorDetailsRepository  extends  MongoRepository<VendorDetailsRequestModel,String>{

	public VendorDetailsRequestModel findByVEmail(String email);
}