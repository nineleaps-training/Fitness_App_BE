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
import com.fitness.app.componets.Components;
import com.fitness.app.config.JwtUtils;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.SignUpResponce;
import com.fitness.app.model.UserModel;
import com.fitness.app.repository.UserRepository;
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
	private UserRepository userRepo;
	 

	 //Register a new user by custom option.
	 @ApiOperation(value = "Registering User", notes = "Registering new user")
	 @ApiResponses(value = { @ApiResponse(code=200, message = "User Registered", response = SignUpResponce.class),
	 @ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	 @ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
	 @PostMapping(value = "/v1/register/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	 @ResponseStatus(HttpStatus.CREATED)
	 @Validated
	 public SignUpResponce registerUser(@Valid @RequestBody UserModel user) 
	 { 
		 UserClass localUser=userRepo.findByEmail(user.getEmail());
		 SignUpResponce responce=new SignUpResponce();
		 if(localUser!=null && localUser.getCustom())
		 {
			 localUser.setPassword(null);
			 
			 if(Boolean.TRUE.equals(localUser.getActivated()))
			 {
			      
			      responce.setCurrentUser(localUser);
			      responce.setMessage("Already In Use!");
			      return responce;
			 }
			 else
			 {
				 String otp= sendMessage.otpBuilder();
				 final  int code=sendMessage.sendOtpMessage("hello ", otp,user.getMobile()); 
				 if(code==200)
				 {
					  responce.setCurrentUser(localUser);
				      responce.setMessage(otp);
				      return responce;
				 }
				 else
				 {
					 responce.setCurrentUser(null);
					 responce.setMessage("Somting went wrong");
					 return responce;
				 }
			  }
			 }
		 else
		 {

		      String otp= sendMessage.otpBuilder();
		      final  int code=sendMessage.sendOtpMessage("hello ", otp,user.getMobile());
		      if(code==200) {
		      responce.setCurrentUser(userService.registerUser(user));
	          responce.setMessage(otp);
	          return responce;
		     }
		     else {
				  responce.setCurrentUser(null);
				  responce.setMessage("Somthing went wrong");
				  return responce;
			  }
		 }
			 
		 
			 
		 } 
	 
	 
	 //Verify User
	 @ApiOperation(value = "Verifying user", notes = "Verifying the user from his credentials")
	 @ApiResponses(value = { @ApiResponse(code=200, message = "User Verified", response = ResponseEntity.class),
	 @ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	 @ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
	 @PutMapping(value = "/v1/verify/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	 @ResponseStatus(HttpStatus.OK)
	 @Retryable(value = AuthException.class, maxAttempts = 2)
	 @Validated
	 public ResponseEntity<SignUpResponce> verifyTheUser(@Valid @RequestBody Authenticate authCredential)
	 {
		 UserClass user= userService.verifyUser(authCredential.getEmail());
		 
		 if(user!=null)
		 { 
			 return logInFunctionality(authCredential.getEmail(), authCredential.getPassword());
		 }
		 
		 return ResponseEntity.ok( new SignUpResponce(null, null));
	 }
	 
	
	 
	 //Log in user
	 @ApiOperation(value = "Logging in", notes = "User can log in")
	 @ApiResponses(value = { @ApiResponse(code=200, message = "User Logged in", response = ResponseEntity.class),
	 @ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),
	 @ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
	 @PostMapping(value = "/v1/login/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	 @ResponseStatus(HttpStatus.CREATED)
	 @Retryable(value = AuthException.class, maxAttempts = 2)
	 @Validated
	    public ResponseEntity<SignUpResponce> authenticateUser(@Valid @RequestBody Authenticate authCredential)
	    {
	    	return logInFunctionality(authCredential.getEmail(), authCredential.getPassword() );   	
	    }
	 
	 
	 //function to log in and return token
	 public ResponseEntity<SignUpResponce> logInFunctionality(String email, String password) 
	 {	
	    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));	
	    final UserDetails usrDetails=userDetailsService.loadUserByUsername(email);
	    final String jwt= jwtUtils.generateToken(usrDetails);
	    final UserClass localUser=userRepo.findByEmail(email);
	    if(!"ADMIN".equals(localUser.getRole()))
	    {
	    	localUser.setPassword(null);
	    	return ResponseEntity.ok( new SignUpResponce( localUser, jwt));
	    }
	    else
	    {
	    	return ResponseEntity.ok( new SignUpResponce(null, null));
	    }
	    	
	 }
	 
	 @ApiOperation(value = "Google Sign In(Vendor)", notes = "Logging in through google by vendor")
	 @ApiResponses(value = { @ApiResponse(code=200, message = "Vendor Logged In", response = ResponseEntity.class),
	 @ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	 @ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
	 @PutMapping(value = "/v1/google-sign-in/vendor", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	 @ResponseStatus(HttpStatus.OK)
	 @Retryable(value = AuthException.class, maxAttempts = 2)
	 @Validated
	 public ResponseEntity<SignUpResponce> googleSignInVendor(@Valid @RequestBody UserModel user)
	 {
		 String pass=userService.randomPass();
		 user.setPassword(pass);
		 UserClass localUser=userService.googleSignInMethod(user);
		 if(localUser==null) {
			 return ResponseEntity.ok( new SignUpResponce(null, "This email in use!"));
		 }
		 else if(localUser.getRole().equals("USER"))
		 {
			 return ResponseEntity.ok( new SignUpResponce(null, "This email already in use as USER! "));
		 }
		 else
		 {
			 return logInFunctionality(localUser.getEmail(), user.getPassword());
		 }
	 }
	 
	 @ApiOperation(value = "Google Sign In(User)", notes = "Logging in through google by user")
	 @ApiResponses(value = { @ApiResponse(code=200, message = "User Logged In", response = ResponseEntity.class),
	 @ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	 @ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
	 @PutMapping(value = "/v1/google-sign-in/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	 @ResponseStatus(HttpStatus.OK)
	 @Retryable(value = AuthException.class, maxAttempts = 2)
	 @Validated
	 public ResponseEntity<SignUpResponce> googleSignInUser(@Valid @RequestBody UserModel user)
	 {
		 String pass=userService.randomPass();
		 user.setPassword(pass);
		 UserClass localUser=userService.googleSignInMethod(user);
		 if(localUser==null) {
			 return ResponseEntity.ok( new SignUpResponce(null, "This email in use!"));
		 }
		 else if(localUser.getRole().equals("VENDOR"))
		 {
			 return ResponseEntity.ok( new SignUpResponce(null, "This email already in use as VENDOR! "));
		 }
		 else
		 {
			 return logInFunctionality(localUser.getEmail(), user.getPassword());
		 }
	 }
}


