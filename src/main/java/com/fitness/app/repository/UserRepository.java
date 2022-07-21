package com.fitness.app.repository;

import com.fitness.app.entity.UserClass;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableMongoRepositories

public interface UserRepository extends PagingAndSortingRepository<UserClass, String> {


    public UserClass findByEmail(String email);

    public List<UserClass> findByRoleContaining(String role, Pageable pageable);

}
