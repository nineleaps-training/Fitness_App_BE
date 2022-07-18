package com.fitness.app.controller;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.componets.MessageComponents;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserForgot;
import com.fitness.app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class UserForgetPasswordController {
    @Autowired
	 private UserRepository userRepo;

    @Autowired
	 private MessageComponents sendMessage;

    @Autowired
    private PasswordEncoder passwordEncoder;
	//Fetching and verifying user 
    @GetMapping("/forget/user/{email}")
	 public UserForgot userForgot(@PathVariable String email) 
	 { 
		 UserClass userClass=userRepo.findByEmail(email);
         UserForgot userForgot=new UserForgot();
		 if(userClass==null)
		 {
            userForgot.setBool(false);
            userForgot.setOtp(null);
			return userForgot;
		}
        else{
            String otp= sendMessage.otpBuilder();
				 final  int code=sendMessage.sendOtpMessage( otp,userClass.getMobile());
				 if(code==200)
				 {
					  userForgot.setBool(true);
				      userForgot.setOtp(otp);
				      return userForgot;
				 }
				 else
				 {
					 userForgot.setBool(false);
					 userForgot.setOtp("Something went wrong..Please try again!!");
					 return userForgot;
				 }

        }
    }
	//Setting the new password for the user
    @PutMapping("/user/set-password")
    public boolean setPassword(@RequestBody Authenticate user){
        UserClass localUser=userRepo.findByEmail(user.getEmail());
        localUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(localUser);
        return true;
	}
    
}
    
