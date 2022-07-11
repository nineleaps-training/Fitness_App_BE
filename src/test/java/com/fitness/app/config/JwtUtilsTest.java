package com.fitness.app.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fitness.app.model.UserModel;

class JwtUtilsTest {

    JwtUtils jwtutil = new JwtUtils();

    UserModel loginDetails=new UserModel("pankaj.jain@nineleaps.com","Pankaj Jain","8469492322","Pankaj@123","ADMIN",false);

        org.springframework.security.core.userdetails.UserDetails userDetails = new org.springframework.security.core.userdetails.UserDetails()  {
            
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                String roles = loginDetails.getRole();
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(roles));
                return authorities;
            }

            @Override
            public String getPassword() {
                return loginDetails.getFullName();
            }

            @Override
            public String getUsername() {
                return loginDetails.getEmail();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };

    @Test
    void testExtractExpiration() {

        String token = jwtutil.generateToken(userDetails);
        Date d= jwtutil.extractExpiration(token);
        Assertions.assertEquals(Date.class,d.getClass());

    }

    @Test
    void testExtractUsername() {

        String token = jwtutil.generateToken(userDetails);
		String Username = jwtutil.extractUsername(token);
		Assertions.assertEquals("pankaj.jain@nineleaps.com", Username);

    }

    @Test
    void testGenerateToken() {

        String token = jwtutil.generateToken(userDetails);
		String actualToken = token;
		Assertions.assertEquals(actualToken, token);

    }

    @Test
    void testValidateToken() {

        String token = jwtutil.generateToken(userDetails);
		Boolean validate = jwtutil.validateToken(token, userDetails);
		Assertions.assertEquals(true, validate);

    }
    
    @Test
    void testValidateTokenExpired() {
        
		Assertions.assertEquals(false, jwtutil.validateToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYW5rYWouamFpbkBuaW5lbGVhcHMuY29tIiwiZXhwIjoxNjU3NTMxNzY4LCJpYXQiOjE2NTc1MzE3Mzh9.pj2x625PfTndZ3PISV4dIpNIBzE-jZ7rShR16i0dwYs", userDetails));
    }

}
