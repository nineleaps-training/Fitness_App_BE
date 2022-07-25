package com.fitness.app.controller;

import com.fitness.app.dto.request.MarkUserAttModel;
import com.fitness.app.dto.response.ApiResponse;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.service.dao.AttendanceDao;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * The type Attendance controller.
 */
@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    final private AttendanceDao attendanceDao;
    /**
     * Mark user attendance api response.
     *
     * @param userAttendance the user attendance
     * @return the api response
     * @throws DataNotFoundException the data not found exception
     */

    @PutMapping("/mark/users/attendance")
    @Validated
    @ApiOperation(value = "Mark Attendance of user", notes = "Vendor can mark attendance of fitness freaks ")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "total marked and non-marked attendance ", response = ApiResponse.class),
            @io.swagger.annotations.ApiResponse(code = 404, message = "No Data found or wrong access token.", response = DataNotFoundException.class)
    })
    public ApiResponse markUserAttendance(@Valid @RequestBody MarkUserAttModel userAttendance) throws DataNotFoundException {
        return new ApiResponse(HttpStatus.OK, attendanceDao.markUsersAttendance(userAttendance));
    }
    /**
     * User performance response entity.
     *
     * @param email the email
     * @param gym   the gym
     * @return the response entity
     * @throws DataNotFoundException the data not found exception
     */

    @GetMapping("/user/performance")
    @Validated
    @ApiOperation(value = "Performance matrix of user ", notes = "User and Vendor both can get performance matrix of user")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Performance matrix as list", response = ApiResponse.class),
            @io.swagger.annotations.ApiResponse(code = 404, message = "No Data fon or Bad Credentials", response = DataNotFoundException.class)
    })
    public ResponseEntity<List<Integer>> userPerformance(@Valid @RequestParam String email,@Valid @RequestParam String gym) throws DataNotFoundException {
        return new ResponseEntity<List<Integer>>(attendanceDao.userPerformance(email, gym), HttpStatus.OK);
    }

}
