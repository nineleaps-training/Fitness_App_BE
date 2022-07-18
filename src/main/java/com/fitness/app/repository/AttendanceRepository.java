package com.fitness.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.fitness.app.entity.UserAttendanceClass;

@Repository
@EnableMongoRepositories
public interface AttendanceRepository extends MongoRepository<UserAttendanceClass,String>{

	public UserAttendanceClass findByEmailAndVendor(String email, String vendor);
	public UserAttendanceClass findByEmailAndVendorAndGym(String email, String vendor, String gym);
	public UserAttendanceClass findByEmailAndGym(String eamil, String gym);
	public List<UserAttendanceClass> findByVendorAndGym(String vendor, String gym);
}
