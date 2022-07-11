package com.fitness.app.service;

import java.security.SecureRandom;
import java.util.Optional;

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
	private PasswordEncoder passwordEncoder;
	 
	 
	@Autowired
	private Components sendMessage;

	public UserService(UserRepository userRepository2, Components componets,PasswordEncoder passwordEncoder) {
		this.userRepository=userRepository2;
		this.sendMessage=componets;
		this.passwordEncoder=passwordEncoder;
	}

	//register user
	public UserClass registerUser(UserModel user)
	{

		String otp= sendMessage.otpBuilder();
		final int code=sendMessage.sendOtpMessage("hello ", otp,user.getMobile()); 
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
			 	userRepository.save(newUser);
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
		UserClass user = null;
		Optional<UserClass> optional=userRepository.findById(email);
		if(optional.isPresent())
		{
			user=optional.get();
			user.setActivated(true);
			userRepository.save(user);
			return user;
		}
		else
		{
			return user;
		}
	}	
	
	//google sign in
	public UserClass googleSignInMethod(UserModel user)
	{
		UserClass localUser=userRepository.findByEmail(user.getEmail());
		if(localUser == null)
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
			 return userRepository.save(newUser);		
			
		}
		else if(Boolean.FALSE.equals(localUser.getCustom()))
		{
			localUser.setPassword(passwordEncoder.encode(user.getPassword()));
			return userRepository.save(localUser);
		}
		else
		{
			return null;
		}		
	}
	
	//generate random String
	public String randomPass()
	{
		int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
		SecureRandom secureRandom=new SecureRandom();

	    return secureRandom.ints(leftLimit, rightLimit + 1)
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	}
	
}
