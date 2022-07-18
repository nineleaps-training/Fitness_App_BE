package com.fitness.app.service;

import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.model.MarkUserAttModel;

import java.util.List;


public interface AttendanceService {

    String markUsersAttendance(MarkUserAttModel markUserAttModel) throws DataNotFoundException;

    List<Integer> userPerformance(String email, String gym) throws DataNotFoundException;

    Double calculateRating(String target);
}
