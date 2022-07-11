package com.fitness.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;
import com.fitness.app.model.AdminPayRequestModel;

@Repository
@EnableMongoRepositories
public interface AdminPayRepo extends MongoRepository<AdminPayRequestModel, String> {

	
	public AdminPayRequestModel findByVendorAndAmountAndStatus(String vendor, int amount, String status);
	public AdminPayRequestModel findByOrderId(String orderId);
	public List<AdminPayRequestModel> findByVendor(String vendor);
}
