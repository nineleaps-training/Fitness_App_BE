package com.fitness.app.controller;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.components.Components;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserForgot;
import com.fitness.app.repository.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.validation.Valid;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import org.springframework.security.core.AuthenticationException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserForgetPasswordController {
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private Components sendMessage;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * This controller is used for verifying the user
	 * 
	 * @param email - Email id of the user
	 * @return - Response is okay after success or else something went wrong
	 */
	@ApiOperation(value = "User Forgot Password", notes = "Verfiying the user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User verified", response = UserForgot.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@GetMapping(value = "/v1/forget/user/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public UserForgot userForgot(@PathVariable String email) {
		UserClass userClass = userRepo.findByEmail(email);
		UserForgot userForgot = new UserForgot();
		if (userClass == null) {
			userForgot.setBool(false);
			userForgot.setOtp(null);
			return userForgot;
		} else {
			String otp = sendMessage.otpBuilder();
			final int code = sendMessage.sendOtpMessage("hello ", otp, userClass.getMobile());
			if (code == 200) {
				userForgot.setBool(true);
				userForgot.setOtp(otp);
				return userForgot; // Fetching and verifying user
			} else {
				userForgot.setBool(false);
				userForgot.setOtp("Something went wrong..Please try again!!");
				return userForgot;
			}
		}
	}

	/**
	 * This controller is used for setting the new password of the user after
	 * verification
	 * 
	 * @param user - Email id and password of the user
	 * @return - True if success or else false
	 */
	@ApiOperation(value = "Setting New Password", notes = "Setting new password for the user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User Fetched", response = Boolean.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@PutMapping(value = "/v1/user/set-password", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Validated
	public boolean setPassword(@Valid @RequestBody Authenticate user) {
		UserClass localUser = userRepo.findByEmail(user.getEmail());
		localUser.setPassword(passwordEncoder.encode(user.getPassword())); // Setting the new password for the user
		userRepo.save(localUser);
		return true;
	}

}
