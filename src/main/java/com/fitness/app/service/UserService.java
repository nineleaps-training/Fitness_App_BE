package com.fitness.app.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

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
		 String message="Hello";
		 final  int code=sendMessage.sendOtpMessage(otp,user.getMobile());
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
			 newUser.setCustom(user.getCustom());
			 userRepo.save(newUser);
			 return newUser;
		 }
		 else
		 {
			 return null;
		 }
			 
	 
	}
	
	//Verifying user
	public UserClass verifyUser(String email)
	{
		UserClass user=new UserClass();
		Optional<UserClass> userData=userRepo.findById(email);
		if(userData.isPresent())
		{
			user=userData.get();
			user.setActivated(true);
			userRepo.save(user);
			return user;
		}
		
		return null;
	}
	
	//LogIn user
	public void loginUser(String email)
	{
		UserClass user= new UserClass();
		Optional<UserClass> userData=userRepo.findById(email);
		if(userData.isPresent())
		{
			user=userData.get();
		}
		user.setLoggedin(true);
		userRepo.save(user);
	}
	
	
	//google sign in
	public UserClass googleSignInMethod(UserModel user)
	{
		UserClass localUser=userRepo.findByEmail(user.getEmail());
		
		if(localUser!=null && !localUser.getCustom())
		{
			localUser.setPassword(passwordEncoder.encode(user.getPassword()));
		     userRepo.save(localUser);
		     return localUser;
		}
		else if(localUser!=null && localUser.getCustom())
		{
			return null;
		}
		else
		{
			
			UserClass newUser=new UserClass();
			 newUser.setEmail(user.getEmail());
			 newUser.setFullName(user.getFullName());
			 newUser.setMobile(user.getMobile());
			 newUser.setPassword(passwordEncoder.encode(user.getPassword()));
			 newUser.setRole(user.getRole());
			 newUser.setActivated(true);
			 newUser.setLoggedin(false);
			 newUser.setCustom(user.getCustom());
			 userRepo.save(newUser);
			 return newUser;
			
		}
		
	}

	//generate random String
	private Random random = new Random();
	public String randomPass()
	{

		int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;


	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();


	    return generatedString;
	}
	
}
