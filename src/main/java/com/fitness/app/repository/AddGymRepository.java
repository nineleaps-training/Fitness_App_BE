package com.fitness.app.repository;

import com.fitness.app.entity.GymClass;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableMongoRepositories

public interface AddGymRepository extends PagingAndSortingRepository<GymClass, String> {

    public List<GymClass> findByEmailContaining(String email, Pageable pageable);

    public GymClass findByName(String name);
}
