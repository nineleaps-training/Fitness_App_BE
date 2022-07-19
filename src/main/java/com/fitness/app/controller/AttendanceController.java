package com.fitness.app.controller;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.fitness.app.exception.DataNotFoundException;
import com.fitness.app.model.MarkUserAttModel;
import com.fitness.app.service.AttendanceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;

	/**
	 * This controller is used to mark the attendance of the user by the vendor
	 * 
	 * @param userAttendance - Attendance of the user
	 * @return - Marked attendance
	 */
	@PutMapping(value = "/v1/mark/users/attendance", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Mark User's Attendance", notes = "Vendor can mark his user's attendance")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User Marked", response = String.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@Validated
	public String markUserAttendance(@Valid @RequestBody MarkUserAttModel userAttendance) {
		return attendanceService.markUsersAttendance(userAttendance); // Mark attendance of the user for a specific fitness center by the vendor.
	}

	/**
	 * This controller is used to provide the total attendance of the user
	 * 
	 * @param email - Email id of the user
	 * @param gym   - Gym id of the fitness center
	 * @return - Response with user's total attendance
	 * @throws DataNotFoundException
	 */
	@ApiOperation(value = "Get User's Performance", notes = "User can view his or her performance")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Performance of the user", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@GetMapping(value = "/v1/user-performance", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> userPerformance(@Email @RequestParam String email,
			@NotNull @NotBlank @RequestParam String gym) throws DataNotFoundException {
		return new ResponseEntity<>(attendanceService.userPerfomance(email, gym), HttpStatus.OK); // Finding the total attendance of the user.

	}
}
