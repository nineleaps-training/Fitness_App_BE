package com.fitness.app.service;

import java.security.SecureRandom;
import java.util.Optional;

import com.fitness.app.config.JwtUtils;
import com.fitness.app.dao.UserDao;
import com.fitness.app.model.SignUpResponse;
import com.fitness.app.security.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fitness.app.componets.Components;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserModel;
import com.fitness.app.repository.UserRepository;


@Slf4j
@Component
public class UserService implements UserDao {

    private UserRepository userRepository;

    private UserRepository userRepo;

    private PasswordEncoder passwordEncoder;

    private Components sendMessage;

    private AuthenticationManager authenticationManager;

    private UserDetailsServiceImpl userDetailsService;

    private JwtUtils jwtUtils;

    String hello = "hello ";

    @Autowired
    public UserService(UserRepository userRepository, UserRepository userRepo, PasswordEncoder passwordEncoder, Components sendMessage, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.sendMessage = sendMessage;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    private final SecureRandom random = new SecureRandom();

    public SignUpResponse registerNewUser(UserModel user) {

        UserClass localUser = userRepo.findByEmail(user.getEmail());
        SignUpResponse responce = new SignUpResponse();
        if (localUser != null && localUser.getCustom()) {
            localUser.setPassword(null);

            if (localUser.getActivated()) {

                responce.setCurrentUser(localUser);
                responce.setMessage("Already In Use!");
                return responce;
            } else {
                String otp = sendMessage.otpBuilder();
                final int code = sendMessage.sendOtpMessage(hello, otp, user.getMobile());
                if (code == 200) {
                    responce.setCurrentUser(localUser);
                    responce.setMessage(otp);
                    return responce;
                } else {
                    responce.setCurrentUser(null);
                    responce.setMessage("Something went wrong");
                    return responce;
                }
            }
        } else {

            String otp = sendMessage.otpBuilder();
            final int code = sendMessage.sendOtpMessage(hello, otp, user.getMobile());
            if (code == 200) {
                responce.setCurrentUser(registerUser(user));
                responce.setMessage(otp);
                return responce;
            } else {
                responce.setCurrentUser(null);
                responce.setMessage("Something went wrong");
                return responce;
            }
        }

    }

    //register user
    public UserClass registerUser(UserModel user) {
        log.info("UserService >> registerNewUser >> Initiated");

        String otp = sendMessage.otpBuilder();
        final int code = sendMessage.sendOtpMessage(hello, otp, user.getMobile());
        if (code == 200) {
            log.info("UserService >> registerNewUser >> Status code is 200");
            UserClass newUser = new UserClass();
            newUser.setEmail(user.getEmail());
            newUser.setFullName(user.getFullName());
            newUser.setMobile(user.getMobile());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setRole(user.getRole());
            newUser.setActivated(false);
            newUser.setLoggedIn(false);
            newUser.setCustom(user.getCustom());
            userRepo.save(newUser);
            return newUser;
        } else {
            log.warn("UserService >> registerNewUser >> returns null");
            return null;
        }

    }

    //Verifying user
    public UserClass verifyUser(String email) {
        log.info("UserService >> verifyUser >> Initiated");
        UserClass user = new UserClass();
        Optional<UserClass> optional = userRepository.findById(email);
        if (optional.isPresent()) {
            user = optional.get();
        }

        user.setActivated(true);
        userRepository.save(user);
        log.info("UserService >> verifyUser >> Ends");
        return user;
    }

    //LogIn user
    public void loginUser(String email) {
        log.info("UserService >> loginUser >> Initiated");

        UserClass user = new UserClass();
        Optional<UserClass> optional = userRepository.findById(email);
        if (optional.isPresent()) {
            user = optional.get();
        }

        user.setLoggedIn(true);
        log.info("UserService >> loginUser >> Ends");
        userRepository.save(user);
    }


    //google sign in
    public UserClass googleSignInMethod(UserModel user) {
        log.info("UserService >> googleSignInMethod >> Initiated");

        UserClass localUser = userRepo.findByEmail(user.getEmail());

        if (localUser != null && !localUser.getCustom()) {
            localUser.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(localUser);
            return localUser;
        } else if (localUser != null && localUser.getCustom()) {
            log.warn("UserService >> googleSignInMethod >> Returns null");

            return null;
        } else {

            UserClass newUser = new UserClass();
            newUser.setEmail(user.getEmail());
            newUser.setFullName(user.getFullName());
            newUser.setMobile(user.getMobile());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setRole(user.getRole());
            newUser.setActivated(true);
            newUser.setLoggedIn(false);
            newUser.setCustom(user.getCustom());
            userRepo.save(newUser);
            log.info("UserService >> googleSignInMethod >> Ends");
            return newUser;

        }

    }


    //generate random String
    public String randomPass() {
        log.info("UserService >> randomPass >> Initiated");

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        log.info(generatedString);
        log.info("UserService >> randomPass >> Ends");
        return generatedString;
    }

    public ResponseEntity<SignUpResponse> logInFunctionality(String email, String password) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
        final UserDetails usrDetails = userDetailsService.loadUserByUsername(email);
        final String jwt = jwtUtils.generateToken(usrDetails);
        final UserClass localUser = userRepo.findByEmail(email);
        if (localUser.getRole() != "ADMIN") {
            localUser.setPassword(null);
            return ResponseEntity.ok(new SignUpResponse(localUser, jwt));
        } else {
            return ResponseEntity.ok(new SignUpResponse(null, null));
        }

    }

}
