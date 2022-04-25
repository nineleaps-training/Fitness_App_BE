package com.fitness.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.fitness.app.entity.VendorPayment;

@Repository
@EnableMongoRepositories
public interface VendorPayRepo extends MongoRepository<VendorPayment, String> {
  
	
	public List<VendorPayment> findByVendor(String vendor);
	
	public List<VendorPayment> findByVendorAndStatus(String vendor, String status);
}
