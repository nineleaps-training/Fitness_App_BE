package com.fitness.app.controller;

import java.util.List;

import com.fitness.app.dao.AttendanceDao;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.fitness.app.model.MarkUserAttModel;
import org.springframework.web.client.HttpClientErrorException;

import javax.mail.AuthenticationFailedException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RequestMapping("/attendance")
@RestController
@Validated
public class AttendanceController {

    @Autowired
    private AttendanceDao attendanceDao;

    //Mark attendance of the user for a specific fitness center.
    @PutMapping("/mark/users")
    @ApiOperation(value = "Mark User's Attendance")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "User's Attendance Marked", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = ChangeSetPersister.NotFoundException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = HttpClientErrorException.Forbidden.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationFailedException.class)
    })
    public String markUserAttendance(@Valid @RequestBody MarkUserAttModel userAttendance) {

        return attendanceDao.markUsersAttendance(userAttendance);
    }

    //Finding the total attendance of the user.
    @GetMapping("/userPerformance")
    @ApiOperation(value = "Get User's Performance")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "User's Performance", response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "Not Found", response = ChangeSetPersister.NotFoundException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = HttpClientErrorException.Forbidden.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationFailedException.class)
    })
    public ResponseEntity<List<Integer>> userPerformance(@NotNull @Email @RequestParam String email, @NotNull @NotEmpty @NotBlank @RequestParam String gym) {

        return new ResponseEntity<>(attendanceDao.userPerformance(email, gym), HttpStatus.OK);

    }


}
