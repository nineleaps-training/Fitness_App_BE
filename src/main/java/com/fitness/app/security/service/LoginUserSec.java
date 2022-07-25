package com.fitness.app.security.service;

import com.fitness.app.config.JwtUtils;
import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.dto.response.ApiResponse;
import com.fitness.app.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginUserSec {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsSecServiceImpl userDetailsSecService;
    final private JwtUtils jwtUtils;

    public ApiResponse logInUserRes(Authenticate authCredential)
    {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authCredential.getEmail(), authCredential.getPassword())
            );
        } catch (DataNotFoundException e) {
            log.error("UserService :: -> loginUser :: Exception found due to: {}", e.getMessage());
            throw new DataNotFoundException("User Not Found");
        }
        final UserDetails usrDetails = userDetailsSecService.loadUserByUsername(authCredential.getEmail());
        final String token = jwtUtils.generateToken(usrDetails);
        return new ApiResponse(HttpStatus.OK, token);
    }
}
