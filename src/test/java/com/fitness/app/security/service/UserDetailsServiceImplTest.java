package com.fitness.app.security.service;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;

import com.fitness.app.entity.UserClass;
import com.fitness.app.model.UserDetailsRequestModel;
import com.fitness.app.repository.UserRepo;

@DisplayName("User Details Service Implementation Test")
@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    UserRepo userRepository;

    UserDetailsServiceImpl userDetailsServiceImpl;

    long l = 1234;

    MockMvc mockMvc;
    UserDetailsRequestModel userDetailsRequestModel;
    UserClass userClass;
    UsernameNotFoundException usernameNotFoundException;

    @BeforeEach
    public void initcase() {
        userDetailsServiceImpl = new UserDetailsServiceImpl(userRepository);
    }

    @DisplayName("Load by username test")
    @Test
    void testLoadUserByUsername() {

        Optional<UserClass> optional;
        optional = Optional.of(new UserClass("pankaj.jain@nineleps.com", "Pankaj Jain", "8469492322", "Hello@123",
                "VENDOR", false, false, false));
        userClass = new UserClass("pankaj.jain@nineleps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR", false,
                false, false);
        when(userRepository.findById(userClass.getEmail())).thenReturn(optional);
        org.springframework.security.core.userdetails.UserDetails userDetails1 = userDetailsServiceImpl
                .loadUserByUsername(userClass.getEmail());
        Assertions.assertEquals(userClass.getEmail(), userDetails1.getUsername());

    }

    @DisplayName("Load by username exception test")
    @Test
    void testLoadUserByUsernameException() {
        try {
            Optional<UserClass> optional;
            optional = Optional.empty();
            userClass = new UserClass("pankaj.jain@nineleps.com", "Pankaj Jain", "8469492322", "Hello@123", "VENDOR",
                    false, false, false);
            when(userRepository.findById(userClass.getEmail())).thenReturn(optional);
            org.springframework.security.core.userdetails.UserDetails userDetails1 = userDetailsServiceImpl
                    .loadUserByUsername(userClass.getEmail());
            Assertions.assertEquals(userClass.getEmail(), userDetails1.getUsername());
        } catch (UsernameNotFoundException e) {
            Assertions.assertEquals(e.getMessage(), "Invalid Credentials : " + userClass.getEmail());
        }

    }
}
