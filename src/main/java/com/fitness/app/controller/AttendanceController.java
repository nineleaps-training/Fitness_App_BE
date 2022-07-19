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
<<<<<<< HEAD
import com.fitness.app.service.AttendanceService;
=======
import com.fitness.app.service.AttendanceServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48

@RestController
public class AttendanceController {
	
	
	@Autowired
<<<<<<< HEAD
	private AttendanceService attendanceService;
=======
	private AttendanceServiceImpl attendanceServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
	
	
	//Mark attendance of the user for a specific fitness center.
	@PutMapping("/mark/users/attendance")
	public String markUserAttendance(@RequestBody MarkUserAttModel userAttendance) throws DataNotFoundException
	{
<<<<<<< HEAD
		return attendanceService.markUsersAttendance(userAttendance);
=======
		return attendanceServiceImpl.markUsersAttendance(userAttendance);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
	}
	


	//Finding the total attendance of the user.
	@GetMapping("/user-performance")

	public ResponseEntity<List<Integer>> userPerformance(@RequestParam String email, @RequestParam String gym) throws DataNotFoundException

	{
	
<<<<<<< HEAD
		return new ResponseEntity<List<Integer>>(attendanceService.userPerfomance(email, gym), HttpStatus.OK);
=======
		return new ResponseEntity<List<Integer>>(attendanceServiceImpl.userPerformance(email, gym), HttpStatus.OK);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
	
	}

	
	
	
}
