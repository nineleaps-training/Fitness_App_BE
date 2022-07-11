package com.fitness.app.config;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import com.fitness.app.model.UserModel;
import com.fitness.app.security.service.UserDetailsServiceImpl;

@SpringBootTest
class JwtAuthenticationFilterTestCase extends HttpServlet {

	@Mock
	UserDetailsServiceImpl userService;

	JwtAuthenticationFilter filter;
	
	@Mock
	UserDetails userDetails;

	@Mock
	JwtUtils jutil;

	HttpServletRequest request;
	HttpServletResponse response;
	FilterChain chain;

	UserModel user1=new UserModel("pankaj.jain@nineleaps.com","Pankaj Jain","8469492322","Pankaj@123","ADMIN",false);

	@BeforeEach
	void initUseCase() {
		filter = new JwtAuthenticationFilter(userService, jutil);
	}

	@BeforeEach
	public void before() {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		chain = mock(FilterChain.class);
	}

	@Test
	@ExtendWith(MockitoExtension.class)
	void TestJwtFilter() throws ServletException, IOException {
		Mockito.when(request.getHeader("Authorization")).thenReturn(
				"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoYXJzaGl0LnJhajEwYUBnbWFpbC5jb20iLCJleHAiOjE2NTE4ODI2MjQsImlhdCI6MTY1MTUyMjYyNH0.hsMnTM5-k4JWnfMdT7i95Xc1kTHKvtClF1A0OGzigPo");
		Mockito.when(jutil.extractUsername(
				"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoYXJzaGl0LnJhajEwYUBnbWFpbC5jb20iLCJleHAiOjE2NTE4ODI2MjQsImlhdCI6MTY1MTUyMjYyNH0.hsMnTM5-k4JWnfMdT7i95Xc1kTHKvtClF1A0OGzigPo"))
				.thenReturn("harshit.raj10a@gmail.com");
		filter.doFilterInternal(request, response, chain);
        verify(chain,times(1)).doFilter(request,response);
		
	}

	@Test
	@ExtendWith(MockitoExtension.class)
	void TestJwtFilterauthHeaderNull() throws ServletException, IOException {
		Mockito.when(request.getHeader("Authorization")).thenReturn(null);
		filter.doFilterInternal(request, response, chain);
        verify(chain,times(1)).doFilter(request,response);
	}

	@Test
	@ExtendWith(MockitoExtension.class)
	void TestJwtFilterNonBererToken() throws ServletException, IOException {
		Mockito.when(request.getHeader("Authorization")).thenReturn(
				"Beare eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoYXJzaGl0LnJhajEwYUBnbWFpbC5jb20iLCJleHAiOjE2NTE4ODI2MjQsImlhdCI6MTY1MTUyMjYyNH0.hsMnTM5-k4JWnfMdT7i95Xc1kTHKvtClF1A0OGzigPo");
		filter.doFilterInternal(request, response, chain);
        verify(chain,times(1)).doFilter(request,response);
	}
}