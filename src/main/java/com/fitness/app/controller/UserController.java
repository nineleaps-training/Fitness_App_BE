package com.fitness.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
	 
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 

	 //register a new user by custom option.
	 @PostMapping("/register/user")
	 public SignUpResponce registerUser(@RequestBody UserModel user) 
	 { 
		 
		 
		 UserClass localUser=userRepo.findByEmail(user.getEmail());
		 SignUpResponce responce=new SignUpResponce();
		 if(localUser!=null && localUser.getCustom())
		 {
			 localUser.setPassword(null);
			 
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
	 @PutMapping("/verify/user")
	 public ResponseEntity<?> verifyTheUser(@RequestBody Authenticate authCredential) throws Exception
	 {
		 UserClass user= userService.verifyUser(authCredential.getEmail());
		 
		 if(user!=null)
		 {
			 
			 return logInFunctionality(authCredential.getEmail(), authCredential.getPassword());
		 }
		 
		 return ResponseEntity.ok( new SignUpResponce(null, null));
	 }
	 
	
	 
	 //log in user
	 @PostMapping("/login/user")
	    public ResponseEntity<?> authenticateUser(@RequestBody Authenticate authCredential) throws Exception
	    {
	    	return logInFunctionality(authCredential.getEmail(), authCredential.getPassword() );
	    	
	    }
	 
	 
	 //function to log in and return token
	 public ResponseEntity<?> logInFunctionality(String email, String password) throws Exception
	 {
		 try {    		
	    		authenticationManager.authenticate(
	    				new UsernamePasswordAuthenticationToken(email, password)
	    				);   		
	    	}catch (Exception e) {
	    		System.out.println(e.getMessage());
	    		throw new Exception("Error");
			}
	    	final UserDetails usrDetails=userDetailsService.loadUserByUsername(email);
	    	final String jwt= jwtUtils.generateToken(usrDetails);
	    	final UserClass localUser=userRepo.findByEmail(email);
	    	if(localUser.getRole()!="ADMIN")
	    	{
	    		localUser.setPassword(null);
	    	return ResponseEntity.ok( new SignUpResponce( localUser, jwt));
	    	}
	    	else
	    	{
	    		return ResponseEntity.ok( new SignUpResponce(null, null));
	    	}
	    	
	 }
	 
	 @PutMapping("/google-sign-in/vendor")
	 public ResponseEntity<?> googleSignInVendor(@RequestBody UserModel user) throws Exception
	 {
		 String pass=userService.randomPass();
		 user.setPassword(pass);
		 UserClass localUser=userService.googleSignInMethod(user);
		 if(localUser==null) {
			 return ResponseEntity.ok( new SignUpResponce(null, "This email in use!"));
		 }
		 else if(localUser!=null && localUser.getRole().equals("USER"))
		 {
			 return ResponseEntity.ok( new SignUpResponce(null, "This email already in use as USER! "));
		 }
		 else
		 {
			 return logInFunctionality(localUser.getEmail(), user.getPassword());
		 }
	 }
	 
	 
	 @PutMapping("/google-sign-in/user")
	 public ResponseEntity<?> googleSignInUser(@RequestBody UserModel user) throws Exception
	 {
		 String pass=userService.randomPass();
		 user.setPassword(pass);
		 UserClass localUser=userService.googleSignInMethod(user);
		 if(localUser==null) {
			 return ResponseEntity.ok( new SignUpResponce(null, "This email in use!"));
		 }
		 else if(localUser!=null && localUser.getRole().equals("VENDOR"))
		 {
			 return ResponseEntity.ok( new SignUpResponce(null, "This email already in use as VENDOR! "));
		 }
		 else
		 {
			 return logInFunctionality(localUser.getEmail(), user.getPassword());
		 }
	 }
}


