package com.fitness.app.controller;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.fitness.app.components.Components;
import com.fitness.app.config.JwtUtils;
import com.fitness.app.entity.UserClass;
import com.fitness.app.exception.ExceededNumberOfAttemptsException;
import com.fitness.app.model.SignUpResponce;
import com.fitness.app.model.UserModel;
import com.fitness.app.repository.UserRepo;
import com.fitness.app.security.service.UserDetailsServiceImpl;
import com.fitness.app.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private Components sendMessage;

	@Autowired
	private UserRepo userRepo;

	/**
	 * This controller is used to register a new user
	 * 
	 * @param user - Details of new user
	 * @return - Response created if user is registered or else bad request
	 */
	@ApiOperation(value = "Registering User", notes = "Registering new user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User Registered", response = SignUpResponce.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@PostMapping(value = "/v1/register/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@Validated
	public SignUpResponce registerUser(@Valid @RequestBody UserModel user) {
		UserClass localUser = userRepo.findByEmail(user.getEmail());
		SignUpResponce responce = new SignUpResponce();
		if (localUser != null && localUser.getCustom()) {
			localUser.setPassword(null);

			if (Boolean.TRUE.equals(localUser.getActivated())) {

				responce.setCurrentUser(localUser);
				responce.setMessage("Already In Use!");
				return responce;
			} else {
				String otp = sendMessage.otpBuilder();
				final int code = sendMessage.sendOtpMessage("hello ", otp, user.getMobile());
				if (code == 200) {
					responce.setCurrentUser(localUser);
					responce.setMessage(otp);
					return responce;
				} else {
					responce.setCurrentUser(null);
					responce.setMessage("Somting went wrong");
					return responce;
				}
			}
		} else {

			String otp = sendMessage.otpBuilder();
			final int code = sendMessage.sendOtpMessage("hello ", otp, user.getMobile());
			if (code == 200) {
				responce.setCurrentUser(userService.registerUser(user)); // Register a new user by custom option.
				responce.setMessage(otp);
				return responce;
			} else {
				responce.setCurrentUser(null);
				responce.setMessage("Somthing went wrong");
				return responce;
			}
		}

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
	@PutMapping(value = "/v1/verify/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Retryable(value = AuthException.class, maxAttempts = 2)
	@Validated
	public ResponseEntity<SignUpResponce> verifyTheUser(@Valid @RequestBody Authenticate authCredential) {
		UserClass user = userService.verifyUser(authCredential.getEmail());

		if (user != null) {
			return logInFunctionality(authCredential.getEmail(), authCredential.getPassword()); // Verify User
		}

		return ResponseEntity.ok(new SignUpResponce(null, null));
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
	@PostMapping(value = "/v1/login/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@Retryable(value = AuthException.class, maxAttempts = 2)
	@Validated
	public ResponseEntity<SignUpResponce> authenticateUser(@Valid @RequestBody Authenticate authCredential) {

		return logInFunctionality(authCredential.getEmail(), authCredential.getPassword()); // Log in user
	}

	/**
	 * This function is used for authenticating the user
	 * 
	 * @param email    - Email id of the user
	 * @param password - Password of the user
	 * @return - Response is okay or else bad request
	 */
	public ResponseEntity<SignUpResponce> logInFunctionality(String email, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password)); // Authenticating the user
		final UserDetails usrDetails = userDetailsService.loadUserByUsername(email);
		final String jwt = jwtUtils.generateToken(usrDetails); // Generating the token for the user
		final UserClass localUser = userRepo.findByEmail(email);
		if (!"ADMIN".equals(localUser.getRole())) {
			localUser.setPassword(null);
			return ResponseEntity.ok(new SignUpResponce(localUser, jwt));
		} else {
			return ResponseEntity.ok(new SignUpResponce(null, null));
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
	@PutMapping(value = "/v1/google-sign-in/vendor", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Retryable(value = AuthException.class, maxAttempts = 2)
	@Validated
	public ResponseEntity<SignUpResponce> googleSignInVendor(@Valid @RequestBody UserModel user) {
		String pass = userService.randomPass();
		user.setPassword(pass);
		UserClass localUser = userService.googleSignInMethod(user);
		if (localUser == null) {
			return ResponseEntity.ok(new SignUpResponce(null, "This email in use!"));
		} else if (localUser.getRole().equals("USER")) {
			return ResponseEntity.ok(new SignUpResponce(null, "This email already in use as USER! "));
		} else {
			return logInFunctionality(localUser.getEmail(), user.getPassword());
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
	@PutMapping(value = "/v1/google-sign-in/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Retryable(value = AuthException.class, maxAttempts = 2)
	@Validated
	public ResponseEntity<SignUpResponce> googleSignInUser(@Valid @RequestBody UserModel user) {
		String pass = userService.randomPass();
		user.setPassword(pass);
		UserClass localUser = userService.googleSignInMethod(user);
		if (localUser == null) {
			return ResponseEntity.ok(new SignUpResponce(null, "This email in use!"));
		} else if (localUser.getRole().equals("VENDOR")) {
			return ResponseEntity.ok(new SignUpResponce(null, "This email already in use as VENDOR! "));
		} else {
			return logInFunctionality(localUser.getEmail(), user.getPassword());
		}
	}
}
