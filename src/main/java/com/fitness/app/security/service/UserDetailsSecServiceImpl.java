package com.fitness.app.security.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
@Slf4j
@Service
public class UserDetailsSecServiceImpl implements UserDetailsService {

	
	@Autowired 
	private UserRepository userRepo;


	//Authenticating user information with the email id
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserClass> data=userRepo.findById(email);
		UserClass user=null;
		if(data.isPresent())
		{
			user=data.get();
		}
		else {
			throw new UsernameNotFoundException("User not found at that location: ");
		}
	     if (user == null){
                log.info("User Not Found.");
	            throw new UsernameNotFoundException("Invalid Credentials : "+email);
	        }
	     return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}

	
	
}
