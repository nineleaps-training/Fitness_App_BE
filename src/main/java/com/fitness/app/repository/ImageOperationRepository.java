package com.fitness.app.repository;

import com.fitness.app.entity.ImageOperationDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableMongoRepositories
public interface ImageOperationRepository extends MongoRepository<ImageOperationDoc, String> {

}
