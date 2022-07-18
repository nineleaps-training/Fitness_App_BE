package com.fitness.app.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.fitness.app.entity.VendorDetailsClass;

@EnableMongoRepositories
@Repository
public interface VendorDetailsRepository  extends  MongoRepository<VendorDetailsClass,String>{

	public VendorDetailsClass findByVendorEmail(String email);
}