package com.fitness.app.repository;

import com.fitness.app.entity.VendorBankDetails;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@EnableMongoRepositories
@Repository
public interface BankDetailsRepo extends MongoRepository<VendorBankDetails, String> {
    public VendorBankDetails findByEmail(String email);
}
