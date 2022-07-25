package com.fitness.app.service;

import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.service.dao.ForgetPassDao;
import com.fitness.app.utils.MessageComponents;
import com.fitness.app.dto.response.UserForgot;
import com.fitness.app.entity.UserClass;
import com.fitness.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@Transactional
@Component
public class ForgetPassDaoImpl implements ForgetPassDao {
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
