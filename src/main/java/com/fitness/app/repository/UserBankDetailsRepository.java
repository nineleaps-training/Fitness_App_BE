package com.fitness.app.repository;

import com.fitness.app.entity.UserBankDetailsClass;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@EnableMongoRepositories
@Repository
public interface UserBankDetailsRepository extends MongoRepository<UserBankDetailsClass, String> {
   public UserBankDetailsClass findByEmail(String email);

}
