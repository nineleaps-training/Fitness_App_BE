package com.fitness.app.repository;

import java.util.List;

import com.fitness.app.model.AdminPayModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableMongoRepositories
public interface AdminPayRepo extends MongoRepository<AdminPayModel, String> {


    public AdminPayModel findByVendorAndAmountAndStatus(String vendor, int amount, String status);

    public AdminPayModel findByOrderId(String orderId);

    public List<AdminPayModel> findByVendor(String vendor);
}
