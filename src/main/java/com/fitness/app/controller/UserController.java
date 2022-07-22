package com.fitness.app.controller;

import java.time.Duration;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.MediaType;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import org.springframework.security.core.AuthenticationException;
import com.fitness.app.auth.Authenticate;
import com.fitness.app.dao.UserDAO;
import com.fitness.app.entity.UserClass;
import com.fitness.app.exception.ExceededNumberOfAttemptsException;
import com.fitness.app.model.SignUpResponceModel;
import com.fitness.app.model.UserModel;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Validated
public class UserController {

	@Autowired
	private UserDAO userService;

	private final Bucket bucket;

	public UserController()
	{
		this.bucket=Bucket4j.builder()
		.addLimit(Bandwidth.classic(3, Refill.intervally(3, Duration.ofHours(24))))
		.build();
	}

	/**
	 * This controller is used to register a new user
	 * 
	 * @param user - Details of new user
	 * @return - Response created if user is registered or else bad request
	 */
	@ApiOperation(value = "Registering User", notes = "Registering new user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User Registered", response = SignUpResponceModel.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@PostMapping(value = "/v1/user/register/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@Validated
	public SignUpResponceModel registerUser(@Valid @RequestBody UserModel user) {

		return userService.registerNewUser(user);

	}

	/**
	 * This controller is used to verify the user
	 * 
	 * @param authCredential - Authentication details (email and password) of the user
	 * @return - Response with email and JWT Token
	 */
	@ApiOperation(value = "Verifying user", notes = "Verifying the user from his credentials")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User Verified", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@PutMapping(value = "/v1/user/verify/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Validated
	public ResponseEntity<SignUpResponceModel> verifyTheUser(@Valid @RequestBody Authenticate authCredential) {
		
		UserClass user = userService.verifyUser(authCredential.getEmail());

		if (user != null) {
			return userService.logInFunctionality(authCredential.getEmail(), authCredential.getPassword()); // Verify User
		}

		return ResponseEntity.ok(new SignUpResponceModel(null, null));
	}

	/**
	 * This controller is used for logging in the user
	 * 
	 * @param authCredential - Authentication details fof the user
	 * @return - Response is okay if user is authenticated or else bad credentials
	 * @throws ExceededNumberOfAttemptsException - throws Exception after 3 failed attempts
	 */
	@ApiOperation(value = "Logging in", notes = "User can log in")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User Logged in", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@PostMapping(value = "/v1/user/login/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@Validated
	public ResponseEntity<SignUpResponceModel> authenticateUser(@Valid @RequestBody Authenticate authCredential) throws ExceededNumberOfAttemptsException {

		if(bucket.tryConsume(1))
		{
			return userService.logInFunctionality(authCredential.getEmail(), authCredential.getPassword()); // Log in user
		}
		else
		{
			throw new ExceededNumberOfAttemptsException("Account Locked: Please try after 24 hours");
		}
	}

	/**
	 * This controller is used for logging in the vendor by Google
	 * 
	 * @param user - Details of the vendor
	 * @return - Response is okay after successfull log in
	 */
	@ApiOperation(value = "Google Sign In(Vendor)", notes = "Logging in through google by vendor")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Vendor Logged In", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@PutMapping(value = "/v1/user/googleSignIn/vendor", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Validated
	public ResponseEntity<SignUpResponceModel> googleSignInVendor(@Valid @RequestBody UserModel user) {
		String pass = userService.randomPass();
		user.setPassword(pass);
		UserClass localUser = userService.googleSignInMethod(user);
		if (localUser == null) {
			return ResponseEntity.ok(new SignUpResponceModel(null, "This email is already in use!"));
		} else if (localUser.getRole().equals("USER")) {
			return ResponseEntity.ok(new SignUpResponceModel(null, "This email is already in use as USER!"));
		} else {
			return userService.logInFunctionality(localUser.getEmail(), user.getPassword());
		}
	}

	/**
	 * This controller is used for logging in the user by Google
	 * 
	 * @param user - Details of the user
	 * @return - Response is okay after successfull log in
	 */
	@ApiOperation(value = "Google Sign In(User)", notes = "Logging in through google by user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User Logged In", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@PutMapping(value = "/v1/user/googleSignIn/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Validated
	public ResponseEntity<SignUpResponceModel> googleSignInUser(@Valid @RequestBody UserModel user) {
		String pass = userService.randomPass();
		user.setPassword(pass);
		UserClass localUser = userService.googleSignInMethod(user);
		if (localUser == null) {
			return ResponseEntity.ok(new SignUpResponceModel(null, "This email is already in use!"));
		} else if (localUser.getRole().equals("VENDOR")) {
			return ResponseEntity.ok(new SignUpResponceModel(null, "This email is already in use as VENDOR!"));
		} else {
			return userService.logInFunctionality(localUser.getEmail(), user.getPassword());
		}
	}
}
