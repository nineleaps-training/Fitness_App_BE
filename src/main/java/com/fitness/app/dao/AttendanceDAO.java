package com.fitness.app.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fitness.app.model.MarkUserAttModel;

@Component
public interface AttendanceDAO {

    public String markUsersAttendance(MarkUserAttModel userAttendance);

    public List<Integer> userPerformance(String email, String gym);

    public Double calculateRating(String target);

}