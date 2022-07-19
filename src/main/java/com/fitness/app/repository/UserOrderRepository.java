package com.fitness.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitness.app.entity.UserOrderClass;

@Repository
public interface UserOrderRepository extends MongoRepository<UserOrderClass, String> {

	public UserOrderClass findByEmailAndBooked(String email, String booked);

	public List<UserOrderClass> findByEmail(String email);

	public List<UserOrderClass> findByGym(String gymId);
}
