package com.fitness.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.model.MarkUserAttModel;
import com.fitness.app.service.AttendanceService;

@RestController
public class AttendanceController {
	
	
	@Autowired
	private AttendanceService attendanceService;
	
	
	//Mark attendance of the user for a specific fitness center.
	@PutMapping("/mark/users/attendance")
	public String markUserAttendance(@RequestBody MarkUserAttModel userAttendance) throws DataNotFoundException
	{
		return attendanceService.markUsersAttendance(userAttendance);
	}
	


	//Finding the total attendance of the user.
	@GetMapping("/user-performance")

	public ResponseEntity<List<Integer>> userPerformance(@RequestParam String email, @RequestParam String gym) throws DataNotFoundException

	{
	
		return new ResponseEntity<List<Integer>>(attendanceService.userPerfomance(email, gym), HttpStatus.OK);
	
	}

	
	
	
}
