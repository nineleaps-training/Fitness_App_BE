package com.fitness.app.service;

import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.utils.MessageComponents;
import com.fitness.app.config.JwtUtils;
import com.fitness.app.dto.UserModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.UserClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.security.service.UserDetailsSecServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {


    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final MessageComponents sendMessage;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsSecServiceImpl userDetailsSecService;
    private Random random = new Random();
    final private JwtUtils jwtUtils;
    private String otp;

    //register user
    @Override
    public ApiResponse registerUser(UserModel user) {


        UserClass localUser = userRepo.findByEmail(user.getEmail());
        if (localUser != null && localUser.getCustom() && localUser.getActivated()) {
            return new ApiResponse(HttpStatus.NOT_ACCEPTABLE, "Already User Exist:");
        } else if (localUser != null && localUser.getCustom() && localUser.getActivated()) {
            otp = sendMessage.otpBuilder();
            String body = "your verification code is: " + otp;
            sendMessage.sendMail(localUser.getEmail(), body, "Verify YourSelf : Fitness Freak");
            return new ApiResponse(HttpStatus.CONTINUE, "Verify Otp.");
        } else {
            otp = sendMessage.otpBuilder();
            String body = "your verification code is: " + otp;
            int code = sendMessage.sendMail(localUser.getEmail(), body, "Verify YourSelf : Fitness Freak");
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
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authCredential.getEmail(), authCredential.getPassword())
            );
        } catch (DataNotFoundException e) {
            log.error("Error found: {}", e.getMessage());
            throw new DataNotFoundException("User Not Found");
        }
        final UserDetails usrDetails = userDetailsSecService.loadUserByUsername(authCredential.getEmail());
        final String token = jwtUtils.generateToken(usrDetails);
        return new ApiResponse(HttpStatus.OK, token);

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
