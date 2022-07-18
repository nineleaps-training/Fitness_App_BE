package com.fitness.app.repository;

import com.fitness.app.entity.VendorBankDetailsClass;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;


@EnableMongoRepositories
@Repository
public interface BankDetailsRepository extends MongoRepository<VendorBankDetailsClass, String> {
    public VendorBankDetailsClass findByVendorEmail(String email);
}
