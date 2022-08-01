package com.fitness.app.service;

import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fitness.app.components.Components;
import com.fitness.app.config.JwtUtils;
import com.fitness.app.dao.UserDAO;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.SignUpResponseModel;
import com.fitness.app.model.UserModel;
import com.fitness.app.repository.UserRepo;
import com.fitness.app.security.service.UserDetailsServiceImpl;

import lombok.extern.slf4j.Slf4j;

import static com.fitness.app.components.Constants.*;

@Slf4j
@Service
public class UserService implements UserDAO {

	private UserRepo userRepository;

	private PasswordEncoder passwordEncoder;

	private Components sendMessage;

	private UserRepo userRepo;

	private AuthenticationManager authenticationManager;

	private UserDetailsServiceImpl userDetailsService;

	private JwtUtils jwtUtils;

	// Initializing constructor
	/**
	 * This constructor is used to initialize the repositories
	 * 
	 * @param userRepository2 - User Repository
	 * @param components      - Components
	 * @param passwordEncoder - Password Encoder
	 */
	public UserService(UserRepo userRepository2, Components components, PasswordEncoder passwordEncoder,
			UserRepo userRepo, AuthenticationManager authenticationManager,
			UserDetailsServiceImpl userDetailsServiceImpl, JwtUtils jwtUtils) {
		this.userRepository = userRepository2;
		this.sendMessage = components;
		this.passwordEncoder = passwordEncoder;
		this.userRepo = userRepo;
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsServiceImpl;
		this.jwtUtils = jwtUtils;
	}

	public SignUpResponseModel registerNewUser(UserModel user) {
		log.info("UserService >> registerNewUser >> Initiated");
		UserClass localUser = userRepo.findByEmail(user.getEmail());
		SignUpResponseModel response = new SignUpResponseModel();
		if (localUser != null && localUser.getCustom()) {
			localUser.setPassword(null);

			if (Boolean.TRUE.equals(localUser.getActivated())) {

				response.setCurrentUser(localUser);
				response.setMessage(ALREADY_IN_USE);
				return response;
			} else {
				String otp = sendMessage.otpBuilder();
				final int code = sendMessage.sendOtpMessage(HELLO, otp, user.getMobile());
				log.info("UserService >> registerNewUser >> Code: {}", code);
				if (code == 200) {
					response.setCurrentUser(localUser);
					response.setMessage(otp);
					return response;
				} else {
					log.error("UserService >> registerNewUser >> Response Code is not Ok");
					response.setCurrentUser(null);
					response.setMessage(WRONG);
					return response;
				}
			}
		} else {

			String otp = sendMessage.otpBuilder();
			final int code = sendMessage.sendOtpMessage(HELLO, otp, user.getMobile());
			log.info("UserService >> registerNewUser >> Code : {}", code);
			if (code == 200) {
				response.setCurrentUser(registerUser(user)); // Register a new user by custom option.
				response.setMessage(otp);
				return response;
			} else {
				log.error("UserService >> registerNewUser >> Response Code is not okay");
				response.setCurrentUser(null);
				response.setMessage(WRONG);
				return response;
			}
		}
	}

	/**
	 * This function is used for authenticating the user
	 * 
	 * @param email    - Email id of the user
	 * @param password - Password of the user
	 * @return - Response is okay or else bad request
	 */
	public ResponseEntity<SignUpResponseModel> logInFunctionality(String email, String password) {
		log.info("UserService >> logInFunctionality >> Initiated");
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password)); // Authenticating
																										// the user
		final UserDetails usrDetails = userDetailsService.loadUserByUsername(email);
		final String jwt = jwtUtils.generateToken(usrDetails); // Generating the token for the user
		final UserClass localUser = userRepo.findByEmail(email);
		if (!"ADMIN".equals(localUser.getRole())) {
			localUser.setPassword(null);
			return ResponseEntity.ok(new SignUpResponseModel(localUser, jwt));
		} else {
			log.warn("UserService >> logInFunctionality >> Null values are returned");
			return ResponseEntity.ok(new SignUpResponseModel(null, null));
		}

	}

	/**
	 * This function is used to register the user
	 * 
	 * @param user - User Details
	 * @return - Details of the user
	 */
	public UserClass registerUser(UserModel user) {
		log.info("UserService >> registerUser >> Initiated");
		String otp = sendMessage.otpBuilder();
		final int code = sendMessage.sendOtpMessage(HELLO, otp, user.getMobile());
		log.info(CODE, code);
		if (code == 200) {
			UserClass newUser = new UserClass();
			newUser.setEmail(user.getEmail());
			newUser.setFullName(user.getFullName());
			newUser.setMobile(user.getMobile());
			newUser.setPassword(passwordEncoder.encode(user.getPassword()));
			newUser.setRole(user.getRole());
			newUser.setActivated(false);
			newUser.setLoggedIn(false);
			newUser.setCustom(user.getCustom());
			userRepository.save(newUser); // Register user
			return newUser;
		} else {
			log.warn("UserService >> registerUser >> Null is returned");
			return null;
		}
	}

	/**
	 * This function is used to verify the user
	 * 
	 * @param email - Email id of the user
	 * @return - Details of the user
	 */
	public UserClass verifyUser(String email) {
		log.info("UserService >> verifyUser >> Initiated");
		UserClass user = null;
		Optional<UserClass> optional = userRepository.findById(email); // Verifying user
		if (optional.isPresent()) {
			user = optional.get();
			user.setActivated(true);
			userRepository.save(user);
			return user;
		} else {
			log.warn(OPTIONAL_NOT_PRESENT);
			return user;
		}
	}

	/**
	 * This function is used for logging in the user by Google
	 * 
	 * @param user - Details of the user
	 * @return - User Details
	 */
	public UserClass googleSignInMethod(UserModel user) {
		log.info("UserService >> googleSignInMethod >> Initiated");
		UserClass localUser = userRepository.findByEmail(user.getEmail());
		if (localUser == null) {

			UserClass newUser = new UserClass();
			newUser.setEmail(user.getEmail());
			newUser.setFullName(user.getFullName());
			newUser.setMobile(user.getMobile());
			newUser.setPassword(passwordEncoder.encode(user.getPassword()));
			newUser.setRole(user.getRole());
			newUser.setActivated(true);
			newUser.setLoggedIn(false);
			newUser.setCustom(user.getCustom());
			return userRepository.save(newUser);

		} else if (Boolean.FALSE.equals(localUser.getCustom())) { // Google sign in
			log.info(CUSTOM_FALSE);
			localUser.setPassword(passwordEncoder.encode(user.getPassword()));
			return userRepository.save(localUser);
		} else {
			log.warn(NULL_RETURNED);
			return null;
		}
	}

	/**
	 * This function is used to generate random string
	 * 
	 * @return - Random String
	 */
	public String randomPass() {
		log.info("UserService >> randomPass >> Initiated");
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		SecureRandom secureRandom = new SecureRandom();

		return secureRandom.ints(leftLimit, rightLimit + 1)
				.limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString(); // Generate random String
	}

}
