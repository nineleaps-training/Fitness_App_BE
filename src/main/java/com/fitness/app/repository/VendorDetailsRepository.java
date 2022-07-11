package com.fitness.app.repository;

import com.fitness.app.model.VendorDetailsModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;


@EnableMongoRepositories
@Repository
public interface VendorDetailsRepository extends MongoRepository<VendorDetailsModel, String> {

    public VendorDetailsModel findByEmail(String email);
}