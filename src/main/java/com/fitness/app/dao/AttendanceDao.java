package com.fitness.app.dao;

import com.fitness.app.model.MarkUserAttModel;

import java.util.List;

public interface AttendanceDao {

    String markUsersAttendance(MarkUserAttModel userAttendance);
    List<Integer> userPerformance(String email, String gym);
    Double calculateRating(String target);
}
