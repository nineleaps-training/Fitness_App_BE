package com.fitness.app.repository;

import com.fitness.app.entity.VendorDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;


@EnableMongoRepositories
@Repository
public interface VendorDetailsRepository extends MongoRepository<VendorDetails, String> {

    public VendorDetails findByEmail(String email);
}