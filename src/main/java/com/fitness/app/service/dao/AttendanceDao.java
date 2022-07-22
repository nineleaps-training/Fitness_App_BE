package com.fitness.app.service.dao;

import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.dto.request.MarkUserAttModel;

import java.util.List;

public interface AttendanceDao {

    String markUsersAttendance(MarkUserAttModel markUserAttModel) throws DataNotFoundException;

    List<Integer> userPerformance(String email, String gym) throws DataNotFoundException;

    Double calculateRating(String target);
}

