package com.fitness.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fitness.app.componets.Components;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserModel;
import com.fitness.app.repository.UserRepository;

@Service
public class UserService {

	
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private UserRepository userRepo;
	
	 
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	 
	 @Autowired
	 private Components sendMessage;
	 
	 
     
	
	//register user
	public UserClass registerUser(UserModel user)
	{
		
		 
		 String otp= sendMessage.otpBuilder();
		 final  int code=sendMessage.sendOtpMessage("hello ", otp,user.getMobile()); 
		 if(code==200)
		 {
			 UserClass newUser=new UserClass();
			 newUser.setEmail(user.getEmail());
			 newUser.setFullName(user.getFullName());
			 newUser.setMobile(user.getMobile());
			 newUser.setPassword(passwordEncoder.encode(user.getPassword()));
			 newUser.setRole(user.getRole());
			 newUser.setActivated(false);
			 newUser.setLoggedin(false);
			 return userRepo.save(newUser);
		 }
		 else
		 {
			 return null;
		 }
			 
	 
	}
	
	//Verifying user
	public boolean verifyUser(String email)
	{
		UserClass user=userRepository.findById(email).get();
		user.setActivated(true);
		userRepository.save(user);
		return true;
	}
	
	//LogIn user
	public void loginUser(String email)
	{
		UserClass user=userRepository.findById(email).get();
		user.setLoggedin(true);
		userRepository.save(user);
	}
	
	
	
	
}
