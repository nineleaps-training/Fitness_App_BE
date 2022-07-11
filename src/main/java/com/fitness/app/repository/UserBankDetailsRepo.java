package com.fitness.app.repository;

import com.fitness.app.model.UserBankDetailsModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@EnableMongoRepositories
@Repository
public interface UserBankDetailsRepo extends MongoRepository<UserBankDetailsModel, String> {
    public UserBankDetailsModel findByUserEmail(String email);

}
