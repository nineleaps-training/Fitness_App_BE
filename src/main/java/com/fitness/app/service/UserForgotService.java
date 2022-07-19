package com.fitness.app.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.components.Components;
import com.fitness.app.dao.UserForgotDAO;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserForgotModel;
import com.fitness.app.repository.UserRepo;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class UserForgotService implements UserForgotDAO {
   
	private UserRepo userRepo;

	private Components sendMessage;
   
	private PasswordEncoder passwordEncoder;

    public UserForgotModel userForgot(String email)
    {
		log.info("UserForgotService >> userForgot >> Initiated");
        UserClass userClass = userRepo.findByEmail(email);
		UserForgotModel userForgot = new UserForgotModel();
		if (userClass == null) {
			userForgot.setBool(false);
			userForgot.setOtp(null);
			return userForgot;
		} else {
			String otp = sendMessage.otpBuilder();
			final int code = sendMessage.sendOtpMessage("hello ", otp, userClass.getMobile());
			log.info("Code : {}", code);
			if (code == 200) {
				userForgot.setBool(true);
				userForgot.setOtp(otp);
				return userForgot; // Fetching and verifying user
			} else {
				log.warn("UserForgotService >> userForgot >> Response code not Ok");
				userForgot.setBool(false);
				userForgot.setOtp("Something went wrong..Please try again!!");
				return userForgot;
			}
        }
    }

    public boolean setPassword(Authenticate user) {
		log.info("UserForgotService >> setPassword >> Initiated");
		UserClass localUser = userRepo.findByEmail(user.getEmail());
		localUser.setPassword(passwordEncoder.encode(user.getPassword())); // Setting the new password for the user
		userRepo.save(localUser);
		return true;
    }
}
