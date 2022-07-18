package com.fitness.app.repository;

import com.fitness.app.entity.UserDetailsClass;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@EnableMongoRepositories
@Repository
public interface UserDetailsRepository extends MongoRepository<UserDetailsClass, String> {

    UserDetailsClass findByUserEmail(String email);

}
