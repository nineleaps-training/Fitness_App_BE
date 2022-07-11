package com.fitness.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.fitness.app.entity.UserAttendance;

@Repository
@EnableMongoRepositories
public interface AttendanceRepo extends MongoRepository<UserAttendance, String> {

    public UserAttendance findByEmailAndVendor(String email, String vendor);

    public UserAttendance findByEmailAndVendorAndGym(String email, String vendor, String gym);

    public UserAttendance findByEmailAndGym(String email, String gym);

    public List<UserAttendance> findByVendorAndGym(String vendor, String gym);
}
