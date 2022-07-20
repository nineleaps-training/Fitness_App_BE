package com.fitness.app.service;

import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.utils.MessageComponents;
import com.fitness.app.dto.UserForgot;
import com.fitness.app.entity.UserClass;
import com.fitness.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class ForgetPassServiceImpl implements ForgetPassService {
    private final UserRepository userRepo;
    private final MessageComponents sendMessage;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserForgot userForgot(@PathVariable String email) {
        UserClass userClass = userRepo.findByEmail(email);
        UserForgot userForgot = new UserForgot();
        if (userClass == null) {
            userForgot.setBool(false);
            userForgot.setOtp(null);
            return userForgot;
        } else {
            String otp = sendMessage.otpBuilder();
            final int code = sendMessage.sendOtpMessage(otp, userClass.getMobile());
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

    public Boolean setPassword(@RequestBody Authenticate user) {
        UserClass localUser = userRepo.findByEmail(user.getEmail());
        localUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(localUser);
        return true;
    }

}
