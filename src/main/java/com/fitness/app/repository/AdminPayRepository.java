package com.fitness.app.repository;

import java.util.List;

import com.fitness.app.entity.AdminPayClass;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableMongoRepositories
public interface AdminPayRepository extends MongoRepository<AdminPayClass, String> {

	
	public AdminPayClass findByVendorAndAmountAndStatus(String vendor, int amount, String status);
	public AdminPayClass findByOrderId(String orderId);
	public List<AdminPayClass> findByVendor(String vendor);
}
