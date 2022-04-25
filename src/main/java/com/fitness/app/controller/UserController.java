package com.fitness.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.app.auth.AuthToken;
import com.fitness.app.auth.Authenticate;
import com.fitness.app.componets.Components;
import com.fitness.app.config.JwtUtils;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.SignUpResponce;
import com.fitness.app.model.UserModel;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.security.service.UserDetailsServiceImpl;
import com.fitness.app.service.UserService;

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
	 
	 @GetMapping("/hello")
	 public String hello()
	 {
		 return "working";
	 }
	 
	 //register user
	 @PostMapping("/register/user")
	 public SignUpResponce registerUser(@RequestBody UserModel user) 
	 { 
		 
		 
		 UserClass localUser=userRepo.findByEmail(user.getEmail());
		 SignUpResponce responce=new SignUpResponce();
		 if(localUser!=null)
		 {
			 
			 if(localUser.getActivated())
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
			      String otp= sendMessage.otpBuilder();
			      final  int code=sendMessage.sendOtpMessage("hello ", otp,user.getMobile());
			      if(code==200) {
			      responce.setCurrentUser(userService.registerUser(user));
		          responce.setMessage(otp);
		          return responce;
			     }
			
				responce.setCurrentUser(null);
				responce.setMessage("Somthing went wrong");
				return responce;
			 
		 
			 
		 } 
	 
	 
	 //Verify User
	 @PutMapping("/verify/user/{email}")
	 public Boolean verifyTheUser(@PathVariable String email)
	 {
		 return userService.verifyUser(email);
	 }
	 
	
	 
	 //log in user
	 @PostMapping("/login/user")
	    public ResponseEntity<?> authenticateUser(@RequestBody Authenticate authCredential) throws Exception
	    {
	    	try {    		
	    		authenticationManager.authenticate(
	    				new UsernamePasswordAuthenticationToken(authCredential.getEmail(), authCredential.getPassword())
	    				);   		
	    	}catch (Exception e) {
	    		System.out.println(e.getMessage());
	    		throw new Exception("Error");
			}
	    	final UserDetails usrDetails=userDetailsService.loadUserByUsername(authCredential.getEmail());
	    	final String jwt= jwtUtils.generateToken(usrDetails);
	    	final UserClass localUser=userRepo.findByEmail(authCredential.getEmail());
	    	if(localUser.getRole()!="ADMIN")
	    	{
	    	return ResponseEntity.ok( new SignUpResponce( localUser, jwt));
	    	}
	    	else
	    	{
	    		return ResponseEntity.ok( new SignUpResponce(null, null));
	    	}
	    	
	    }
}


