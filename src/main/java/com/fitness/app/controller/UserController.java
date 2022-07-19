package com.fitness.app.controller;

import com.fitness.app.exceptions.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.componets.MessageComponents;
import com.fitness.app.config.JwtUtils;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.SignUpResponce;
import com.fitness.app.model.UserModel;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.security.service.UserDetailsServiceImpl;
<<<<<<< HEAD
import com.fitness.app.service.UserService;
=======
import com.fitness.app.service.UserServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
@Slf4j
@RestController
public class UserController {

	
	
	@Autowired
<<<<<<< HEAD
	private UserService userService;
=======
	private UserServiceImpl userServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
	
	 @Autowired
	 private AuthenticationManager authenticationManager;
	    
	 @Autowired
	 private UserDetailsServiceImpl userDetailsService;
	    
	 @Autowired
	  private JwtUtils jwtUtils;
	 
	 @Autowired
	 private MessageComponents sendMessage;
	
	 @Autowired
	 private UserRepository userRepo;
	 
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 

	 //Register a new user by custom option.
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
				 final  int code=sendMessage.sendOtpMessage( otp,user.getMobile());
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
		      final  int code=sendMessage.sendOtpMessage( otp,user.getMobile());
		      if(code==200) {
<<<<<<< HEAD
		      responce.setCurrentUser(userService.registerUser(user));
=======
		      responce.setCurrentUser(userServiceImpl.registerUser(user));
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
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


	@PostMapping("/send-mail/{toEmail}")
	public String sendMail(@PathVariable String toEmail)
	{
		String body="Your OTP is: "+ sendMessage.otpBuilder();
		int code= sendMessage.sendMail(toEmail,body ,"Fitness App OTP" );
		if(code==200)
		{
			return "Sent";
		}
		return "Error";
	}
	 
	 //Verify User
	 @PutMapping("/verify/user")
	 public ResponseEntity<SignUpResponce> verifyTheUser(@RequestBody Authenticate authCredential) throws Exception
	 {
<<<<<<< HEAD
		 UserClass user= userService.verifyUser(authCredential.getEmail());
=======
		 UserClass user= userServiceImpl.verifyUser(authCredential.getEmail());
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
		 
		 if(user!=null)
		 {
			 
			 return logInFunctionality(authCredential.getEmail(), authCredential.getPassword());
		 }
		 
		 return ResponseEntity.ok( new SignUpResponce(null, null));
	 }
	 
	
	 
	 //Log in user
	 @PostMapping("/login/user")
	    public ResponseEntity<SignUpResponce> authenticateUser(@RequestBody Authenticate authCredential) throws Exception
	    {
	    	return logInFunctionality(authCredential.getEmail(), authCredential.getPassword() );
	    	
	    }
	 
	 
	 //function to log in and return token
	 public ResponseEntity<SignUpResponce> logInFunctionality(String email, String password) throws DataNotFoundException
	 {
		 try {    		
	    		authenticationManager.authenticate(
	    				new UsernamePasswordAuthenticationToken(email, password)
	    				);   		
	    	}catch (DataNotFoundException e) {
             log.info("Error found: {}", e.getMessage());
			 throw new DataNotFoundException("User Not Found");
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
	 public ResponseEntity<SignUpResponce> googleSignInVendor(@RequestBody UserModel user) throws Exception
	 {
<<<<<<< HEAD
		 String pass=userService.randomPass();
		 user.setPassword(pass);
		 UserClass localUser=userService.googleSignInMethod(user);
=======
		 String pass= userServiceImpl.randomPass();
		 user.setPassword(pass);
		 UserClass localUser= userServiceImpl.googleSignInMethod(user);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
		 if(localUser==null) {
			 return ResponseEntity.ok( new SignUpResponce(null, "This email in use!"));
		 }
		 if(localUser.getRole().equals("USER"))
		 {
			 return ResponseEntity.ok( new SignUpResponce(null, "This email already in use as USER! "));
		 }
		 else
		 {
			 return logInFunctionality(localUser.getEmail(), user.getPassword());
		 }
	 }
	 
	 
	 @PutMapping("/google-sign-in/user")
	 public ResponseEntity<SignUpResponce> googleSignInUser(@RequestBody UserModel user) throws Exception
	 {
<<<<<<< HEAD
		 String pass=userService.randomPass();
		 user.setPassword(pass);
		 UserClass localUser=userService.googleSignInMethod(user);
=======
		 String pass= userServiceImpl.randomPass();
		 user.setPassword(pass);
		 UserClass localUser= userServiceImpl.googleSignInMethod(user);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
		 if(localUser==null) {
			 return ResponseEntity.ok( new SignUpResponce(null, "This email in use!"));
		 }
		 if(localUser.getRole().equals("VENDOR"))
		 {
			 return ResponseEntity.ok( new SignUpResponce(null, "This email already in use as VENDOR! "));
		 }
		 else
		 {
			 return logInFunctionality(localUser.getEmail(), user.getPassword());
		 }
	 }
}


