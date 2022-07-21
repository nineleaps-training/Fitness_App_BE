package com.fitness.app.controller;

import com.fitness.app.dto.requestDtos.MarkUserAttModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.service.AttendanceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * The type Attendance controller.
 */
@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {


    final private AttendanceServiceImpl attendanceServiceImpl;


    /**
     * Mark user attendance api response.
     *
     * @param userAttendance the user attendance
     * @return the api response
     * @throws DataNotFoundException the data not found exception
     */
//Mark attendance of the user for a specific fitness center.
    @PutMapping("/private/mark-users-attendance")
    public ApiResponse markUserAttendance(@RequestBody MarkUserAttModel userAttendance) throws DataNotFoundException {
        return new ApiResponse(HttpStatus.OK, attendanceServiceImpl.markUsersAttendance(userAttendance));
    }


    /**
     * User performance response entity.
     *
     * @param email the email
     * @param gym   the gym
     * @return the response entity
     * @throws DataNotFoundException the data not found exception
     */
//Finding the total attendance of the user.
    @GetMapping("/private/user-performance")
    public ResponseEntity<List<Integer>> userPerformance(@RequestParam String email, @RequestParam String gym) throws DataNotFoundException {
        return new ResponseEntity<List<Integer>>(attendanceServiceImpl.userPerformance(email, gym), HttpStatus.OK);
    }

}
