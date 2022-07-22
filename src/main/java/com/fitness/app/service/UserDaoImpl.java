package com.fitness.app.service;

import com.fitness.app.config.JwtUtils;
import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.dto.request.UserModel;
import com.fitness.app.dto.response.ApiResponse;
import com.fitness.app.entity.UserClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.security.service.LoginUserSec;
import com.fitness.app.security.service.UserDetailsSecServiceImpl;
import com.fitness.app.service.dao.UserDao;
import com.fitness.app.utils.MessageComponents;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

/**
 * The type User service.
 */

@Transactional
@RequiredArgsConstructor
@Slf4j
@Component
public class UserDaoImpl implements UserDao {


    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final MessageComponents sendMessage;
    private final LoginUserSec loginUserSec;
    private final UserDetailsSecServiceImpl userDetailsSecService;
    private Random random = new Random();
    final private JwtUtils jwtUtils;
    private String otp ;

    //register user
    @Override
    public ApiResponse registerUser(UserModel user) {

        final String Sub = "Verify YourSelf : Fitness Freak";
        UserClass localUser = userRepo.findByEmail(user.getEmail());
        if (localUser != null && localUser.getCustom() && localUser.getActivated()) {
            return new ApiResponse(HttpStatus.NOT_ACCEPTABLE, "Already User Exist:");
        } else if (localUser != null && localUser.getCustom() && !localUser.getActivated()) {
            otp = sendMessage.otpBuilder();
            String body = "your verification code is: " + otp;
            sendMessage.sendMail(localUser.getEmail(), body, Sub);
            return new ApiResponse(HttpStatus.CONTINUE, "Verify Otp.");
        } else {
            otp = sendMessage.otpBuilder();
            String body = "your verification code is: " + otp;
            int code = sendMessage.sendMail(user.getEmail(), body, Sub);
            if (code == 200) {
                UserClass newUser = new UserClass();
                newUser.setEmail(user.getEmail());
                newUser.setFullName(user.getFullName());
                newUser.setMobile(user.getMobile());
                newUser.setPassword(passwordEncoder.encode(user.getPassword()));
                newUser.setRole(user.getRole());
                newUser.setActivated(false);
                newUser.setLoggedin(false);
                newUser.setCustom(user.getCustom());
                userRepo.save(newUser);
                return new ApiResponse(HttpStatus.OK, "Verify now:");
            } else {
                return new ApiResponse(HttpStatus.RESET_CONTENT, "Email is not correct: ");
            }
        }
    }

    //Verifying user
    @Override
    public ApiResponse verifyUser(String email, String expectedOtp) {
        UserClass user = null;
        Optional<UserClass> userData = userRepo.findById(email);
        if (userData.isPresent() && expectedOtp.equals(otp)) {
            user = userData.get();
            user.setActivated(true);
            userRepo.save(user);
            final UserDetails usrDetails = userDetailsSecService.loadUserByUsername(email);
            String token = jwtUtils.generateToken(usrDetails);
            return new ApiResponse(HttpStatus.OK, token);
        }
        return new ApiResponse(HttpStatus.NO_CONTENT, "No user available with this user id:");
    }


    @Override
    //LogIn user
    public ApiResponse loginUser(Authenticate authCredential) throws DataNotFoundException {
        return loginUserSec.logInUserRes(authCredential);
    }

    //google sign in
    @Override
    public ApiResponse googleSignInMethod(UserModel user) {
        UserClass localUser = userRepo.findByEmail(user.getEmail());
        if (localUser != null && !localUser.getCustom()) {
            localUser.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(localUser);
            final UserDetails usrDetails = userDetailsSecService.loadUserByUsername(user.getEmail());
            final String token = jwtUtils.generateToken(usrDetails);
            return new ApiResponse(HttpStatus.OK, token);
        } else if (localUser != null && localUser.getCustom()) {
            return new ApiResponse(HttpStatus.NOT_ACCEPTABLE, "Email has been already in use with password.");
        } else {

            UserClass newUser = new UserClass();
            newUser.setEmail(user.getEmail());
            newUser.setFullName(user.getFullName());
            newUser.setMobile(user.getMobile());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setRole(user.getRole());
            newUser.setActivated(true);
            newUser.setLoggedin(false);
            newUser.setCustom(user.getCustom());
            userRepo.save(newUser);
            final UserDetails usrDetails = userDetailsSecService.loadUserByUsername(user.getEmail());
            final String token = jwtUtils.generateToken(usrDetails);
            return new ApiResponse(HttpStatus.OK, token);
        }

    }

    //generate random String

    @Override
    public String randomPass() {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

}
