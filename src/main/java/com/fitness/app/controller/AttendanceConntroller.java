package com.fitness.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.app.entity.UserAttendance;
import com.fitness.app.model.MarkUserAttModel;
import com.fitness.app.service.AttendanceService;

@RestController
public class AttendanceConntroller {
	
	
	@Autowired
	private AttendanceService attendanceService;
	
	
	
	@PutMapping("mark/users/attendance")
	public String markUserAttendance(@RequestBody MarkUserAttModel userAttendance) throws Exception
	{
		return attendanceService.markUsersAttendance(userAttendance);
	}
	
	
	@GetMapping("/user-performance")

	public ResponseEntity<?> userPerformance(@RequestParam String email, @RequestParam String gym) throws Exception

	{
	
		return new ResponseEntity<List<Integer>>(attendanceService.userPerfomance(email, gym), HttpStatus.OK);
	
	}

	
	
	
}
