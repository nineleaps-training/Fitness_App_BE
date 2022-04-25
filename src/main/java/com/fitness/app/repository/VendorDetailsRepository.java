package com.fitness.app.repository;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.fitness.app.entity.VendorDetails;

@EnableMongoRepositories
@Repository
public interface VendorDetailsRepository  extends  MongoRepository<VendorDetails,String>{

	public VendorDetails findByEmail(String email);
}