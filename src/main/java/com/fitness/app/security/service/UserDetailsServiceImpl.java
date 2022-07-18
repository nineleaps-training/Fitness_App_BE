package com.fitness.app.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.fitness.app.entity.UserClass;
import com.fitness.app.repository.UserRepository;
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	
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
