package com.fitness.app.repository;

import com.fitness.app.model.VendorBankDetailsModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;


@EnableMongoRepositories
@Repository
public interface BankDetailsRepository extends MongoRepository<VendorBankDetailsModel, String> {
    public VendorBankDetailsModel findByEmail(String email);
}
