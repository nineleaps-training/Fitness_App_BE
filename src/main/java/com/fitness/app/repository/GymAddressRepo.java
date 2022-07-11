package com.fitness.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.fitness.app.entity.GymAddressClass;

@Repository
@EnableMongoRepositories
public interface GymAddressRepo extends MongoRepository<GymAddressClass, String> {

    public List<GymAddressClass> findByCity(String city);

}
