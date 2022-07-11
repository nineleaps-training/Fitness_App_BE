package com.fitness.app.repository;

import com.fitness.app.model.VendorBankDetailsRequestModel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;


@EnableMongoRepositories
@Repository
public interface BankDetailsRepository extends MongoRepository<VendorBankDetailsRequestModel, String> {
    public VendorBankDetailsRequestModel findByEmail(String email);
}
