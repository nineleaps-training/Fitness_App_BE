package com.fitness.app.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fitness.app.entity.UserDetails;
import com.fitness.app.model.UserModel;
import com.fitness.app.security.service.UserDetailsServiceImpl;
import com.fitness.app.service.UserDetailsService;

class JwtAuthenticationFilterTest {
    
    @Mock
	UserDetailsService userService;

	JwtAuthenticationFilter filter;
	
	@Mock
	UserDetails userDetails;

	@Mock
	JwtUtils jutil;

	HttpServletRequest request;
	HttpServletResponse response;
	FilterChain chain;

    @Mock
    private UserDetailsServiceImpl customUserDetailsService;

    @InjectMocks
    private JwtAuthenticationFilter authenticationFilter;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testDoFilterInternal() throws ServletException, IOException {

        String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqaWdtZXQucmluY2hlbkBuaW5lbGVhcHMuY29tIiwiRG9jdG9yRGV0YWlscyI6eyJkb2N0b3JJZCI6NCwiZG9jdG9yTmFtZSI6ImppZ21ldCIsImRvY3RvckVtYWlsIjoiamlnbWV0LnJpbmNoZW5AbmluZWxlYXBzLmNvbSIsInJvbGUiOiJET0NUT1IiLCJwcm9maWxlUGljIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EtL0FGZFp1Y3F1dUExT0FMQUZpVlRIMldxTV9mQ29LR0UzZmlGbk5RSUl1OEE9czk2LWMifSwicm9sZSI6IkRPQ1RPUiIsImV4cCI6MTY1NzQzMTQ5OCwiaWF0IjoxNjU3MzQ1MDk4fQ.5EsBF7HKfTkcpbOTK5ks1IClSHvo0swO8R6cvdv-40q85UgOs1YSIvn9R_iQtlNBlCGMUSLCq96XOOLA-f7Jag";
        HttpServletRequest request = mock(HttpServletRequest.class);

        Mockito.when(request.getHeader("Authorization")).thenReturn(token);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

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

        Mockito.when(jutil.validateToken(Mockito.any(String.class), Mockito.any(org.springframework.security.core.userdetails.UserDetails.class))).thenReturn(true);
        Mockito.when(jutil.extractUsername(Mockito.any(String.class))).thenReturn("jigmet");
        Mockito.when(customUserDetailsService.loadUserByUsername("jigmet")).thenReturn(userDetails);

        authenticationFilter.doFilterInternal(request,response,filterChain);
        authenticationFilter.doFilterInternal(request,response,filterChain);

        verify(filterChain,times(2)).doFilter(request,response);
    }

    @Test
    void returnNullIfTokenIsNotValid() throws ServletException, IOException {

        String token = "Bear eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqaWdtZXQucmluY2hlbkBuaW5lbGVhcHMuY29tIiwiRG9jdG9yRGV0YWlscyI6eyJkb2N0b3JJZCI6NCwiZG9jdG9yTmFtZSI6ImppZ21ldCIsImRvY3RvckVtYWlsIjoiamlnbWV0LnJpbmNoZW5AbmluZWxlYXBzLmNvbSIsInJvbGUiOiJET0NUT1IiLCJwcm9maWxlUGljIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EtL0FGZFp1Y3F1dUExT0FMQUZpVlRIMldxTV9mQ29LR0UzZmlGbk5RSUl1OEE9czk2LWMifSwicm9sZSI6IkRPQ1RPUiIsImV4cCI6MTY1NzQzMTQ5OCwiaWF0IjoxNjU3MzQ1MDk4fQ.5EsBF7HKfTkcpbOTK5ks1IClSHvo0swO8R6cvdv-40q85UgOs1YSIvn9R_iQtlNBlCGMUSLCq96XOOLA-f7Jag";
        HttpServletRequest request = mock(HttpServletRequest.class);

        Mockito.when(request.getHeader("Authorization")).thenReturn(token);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        authenticationFilter.doFilterInternal(request,response,filterChain);

        verify(filterChain,times(1)).doFilter(request,response);

    }

    @Test
    void returnNullIfBearerTokenIsEmpty() throws ServletException, IOException {

        String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqaWdtZXQucmluY2hlbkBuaW5lbGVhcHMuY29tIiwiRG9jdG9yRGV0YWlscyI6eyJkb2N0b3JJZCI6NCwiZG9jdG9yTmFtZSI6ImppZ21ldCIsImRvY3RvckVtYWlsIjoiamlnbWV0LnJpbmNoZW5AbmluZWxlYXBzLmNvbSIsInJvbGUiOiJET0NUT1IiLCJwcm9maWxlUGljIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EtL0FGZFp1Y3F1dUExT0FMQUZpVlRIMldxTV9mQ29LR0UzZmlGbk5RSUl1OEE9czk2LWMifSwicm9sZSI6IkRPQ1RPUiIsImV4cCI6MTY1NzQzMTQ5OCwiaWF0IjoxNjU3MzQ1MDk4fQ.5EsBF7HKfTkcpbOTK5ks1IClSHvo0swO8R6cvdv-40q85UgOs1YSIvn9R_iQtlNBlCGMUSLCq96XOOLA-f7Jag";
        HttpServletRequest request = mock(HttpServletRequest.class);

        Mockito.when(request.getHeader("Authorize")).thenReturn(token);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        authenticationFilter.doFilterInternal(request,response,filterChain);

        verify(filterChain,times(1)).doFilter(request,response);

    }

    @Test
    void returnFailureIfValidateTokenIsFalse() throws ServletException, IOException {

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

        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYW5rYWouamFpbkBuaW5lbGVhcHMuY29tIiwiZXhwIjoxNjU3NTMxNzY4LCJpYXQiOjE2NTc1MzE3Mzh9.pj2x625PfTndZ3PISV4dIpNIBzE-jZ7rShR16i0dwYs";
        HttpServletRequest request = mock(HttpServletRequest.class);

        Mockito.when(request.getHeader("Authorization")).thenReturn(token);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        Mockito.when(jutil.validateToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYW5rYWouamFpbkBuaW5lbGVhcHMuY29tIiwiZXhwIjoxNjU3NTMxNzY4LCJpYXQiOjE2NTc1MzE3Mzh9.pj2x625PfTndZ3PISV4dIpNIBzE-jZ7rShR16i0dwYs",userDetails)).thenReturn(false);
        Mockito.when(jutil.extractUsername(Mockito.any(String.class))).thenReturn("jigmet");

        authenticationFilter.doFilterInternal(request,response,filterChain);

        verify(filterChain,times(1)).doFilter(request,response);

    }



	@Test
	@ExtendWith(MockitoExtension.class)
	void TestJwtFilter() throws ServletException, IOException {

        filter = new JwtAuthenticationFilter(customUserDetailsService, jutil);
        FilterChain filterChain = mock(FilterChain.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
		Mockito.when(request.getHeader("Authorization")).thenReturn(
				"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoYXJzaGl0LnJhajEwYUBnbWFpbC5jb20iLCJleHAiOjE2NTE4ODI2MjQsImlhdCI6MTY1MTUyMjYyNH0.hsMnTM5-k4JWnfMdT7i95Xc1kTHKvtClF1A0OGzigPo");
		Mockito.when(jutil.extractUsername(
				"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoYXJzaGl0LnJhajEwYUBnbWFpbC5jb20iLCJleHAiOjE2NTE4ODI2MjQsImlhdCI6MTY1MTUyMjYyNH0.hsMnTM5-k4JWnfMdT7i95Xc1kTHKvtClF1A0OGzigPo"))
				.thenReturn("harshit.raj10a@gmail.com");
		filter.doFilterInternal(request, response, filterChain);
		verify(filterChain,times(1)).doFilter(request,response);
	}

	@Test
	@ExtendWith(MockitoExtension.class)
	void TestJwtFilterauthHeaderNull() throws ServletException, IOException {

        FilterChain filterChain = mock(FilterChain.class);
        filter = new JwtAuthenticationFilter(customUserDetailsService, jutil);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
		Mockito.when(request.getHeader("Authorization")).thenReturn(null);
		filter.doFilterInternal(request, response, filterChain);
        verify(filterChain,times(1)).doFilter(request,response);
	}

	@Test
	@ExtendWith(MockitoExtension.class)
	void TestJwtFilterNonBererToken() throws ServletException, IOException {

        FilterChain filterChain = mock(FilterChain.class);
        filter = new JwtAuthenticationFilter(customUserDetailsService, jutil);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
		Mockito.when(request.getHeader("Authorization")).thenReturn(
				"Beare eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoYXJzaGl0LnJhajEwYUBnbWFpbC5jb20iLCJleHAiOjE2NTE4ODI2MjQsImlhdCI6MTY1MTUyMjYyNH0.hsMnTM5-k4JWnfMdT7i95Xc1kTHKvtClF1A0OGzigPo");
		filter.doFilterInternal(request, response, filterChain);
        verify(filterChain,times(1)).doFilter(request,response);
	}
}
