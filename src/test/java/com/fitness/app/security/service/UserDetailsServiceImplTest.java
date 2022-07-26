package com.fitness.app.security.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername() {
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true, true, true);

        when(userRepository.findById(userClass.getEmail())).thenReturn(Optional.of(userClass));

        UserDetails actual = userDetailsService.loadUserByUsername(userClass.getEmail());
        assertEquals(userClass.getEmail(), actual.getUsername());
    }

    @Test
    void loadUserByUsernameException() {
        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi",
                "9685903290", "12345", "Enthusiast", true, true, true);
        try {
            Optional<UserClass> optional;
            optional = Optional.empty();
            when(userRepository.findById(userClass.getEmail())).thenReturn(optional);

            UserDetails actual = userDetailsService.loadUserByUsername(userClass.getEmail());
            assertEquals(userClass.getEmail(), actual.getUsername());
        } catch (UsernameNotFoundException e) {
            assertEquals(e.getMessage(), "Invalid Credentials : " + userClass.getEmail());
        }

    }
}