package com.fitness.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.fitness.app.entity.VendorPaymentClass;

@Repository
@EnableMongoRepositories
public interface VendorPayRepo extends MongoRepository<VendorPaymentClass, String> {
  
	
	public List<VendorPaymentClass> findByVendor(String vendor);
	
	public List<VendorPaymentClass> findByVendorAndStatus(String vendor, String status);
}
