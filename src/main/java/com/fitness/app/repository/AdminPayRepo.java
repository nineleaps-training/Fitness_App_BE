package com.fitness.app.repository;

import java.util.List;

import com.fitness.app.entity.AdminPay;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableMongoRepositories
public interface AdminPayRepo extends MongoRepository<AdminPay, String> {


    public AdminPay findByVendorAndAmountAndStatus(String vendor, int amount, String status);

    public AdminPay findByOrderId(String orderId);

    public List<AdminPay> findByVendor(String vendor);
}
