package com.fitness.app.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.fitness.app.entity.UserClass;
import com.fitness.app.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	
	@Autowired 
	private UserRepository userRepo;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	     UserClass user=	userRepo.findById(email).get();
	     
	     if (user == null){
	            System.out.println("User Not Found.");
	            throw new UsernameNotFoundException("Invalid Credentials : "+email);
	        }
	     
	     
	     return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}

	
	private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles){
		
		List<GrantedAuthority> authorities=new ArrayList<>();
		for(String role:roles)
		{
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}
	
	
	
}
