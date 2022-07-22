package com.fitness.app.security.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.repository.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	public UserDetailsServiceImpl(UserRepo userRepository) {
		this.userRepo = userRepository;
	}

	// Authenticating user information with the email id
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserClass user;
		Optional<UserClass> optional = userRepo.findById(email);
		if (optional.isPresent()) {
			user = optional.get();
		} else {
			log.warn("UserDetailsServiceImpl >> loadUserByUsername >>User Not Found");
			throw new UsernameNotFoundException("Invalid Credentials : " + email);
		}
		return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}
}
