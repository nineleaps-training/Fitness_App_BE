package com.fitness.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitness.app.entity.UserOrder;

@Repository
public interface UserOrderRepo extends MongoRepository<UserOrder, String> {

	public UserOrder findByEmailAndBooked(String email, String booked);

	public List<UserOrder> findByEmail(String email);

	public List<UserOrder> findByGym(String gymId);
}
