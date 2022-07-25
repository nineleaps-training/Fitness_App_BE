package com.fitness.app.service;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.componets.Components;
import com.fitness.app.dao.UserForgetPasswordDao;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserForgot;
import com.fitness.app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class UserForgetPasswordService implements UserForgetPasswordDao {

    private UserRepository userRepo;

    private Components sendMessage;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserForgetPasswordService(UserRepository userRepo, Components sendMessage, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.sendMessage = sendMessage;
        this.passwordEncoder = passwordEncoder;
    }

    public UserForgot userForgot(String email) {
        UserClass userClass = userRepo.findByEmail(email);
        UserForgot userForgot = new UserForgot();
        if (userClass == null) {
            userForgot.setBool(false);
            userForgot.setOtp(null);
            return userForgot;
        } else {
            String otp = sendMessage.otpBuilder();
            final int code = sendMessage.sendOtpMessage("hello ", otp, userClass.getMobile());
            if (code == 200) {
                userForgot.setBool(true);
                userForgot.setOtp(otp);
                return userForgot;
            } else {
                userForgot.setBool(false);
                userForgot.setOtp("Something went wrong..Please try again!!");
                return userForgot;
            }

        }
    }

    public boolean setPassword(Authenticate user) {
        UserClass localUser = userRepo.findByEmail(user.getEmail());
        localUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(localUser);
        return true;
    }
}
