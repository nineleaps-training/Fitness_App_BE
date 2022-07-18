package com.fitness.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.fitness.app.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableRetry
public class WebConfiguration extends WebSecurityConfigurerAdapter {

	private static String[] publicApi = {
			"/v1/gyms/locality/{locality}",
			"/v1/gym/city/{city}",
			"/v1/gym/id/**",

			"/v1/verify/user/**",
			"/v1/register/user",
			"/v1/login/admin",

			"/v1/forget/user/**",
			"/v1/user/set-password",

			"/v1/user-performance",
			"/v1/all-numbers",
			"/v1/downloadFile/**",
			"/v1/google-sign-in/**",

			"/swagger-ui/*",
			"/swagger-resources/**",
			"/v2/api-docs/**",
			"/swagger-ui.html",
			"/favicon.ico",
			"/v1/get-rating/{gymId}",
			"/v1/add/vendor-details",
			"/v1/vendor-details/{email}",
			"/v1/vendor-bankdetails/add",
			"/v1/vendor-bankdetails/get/{email}",
			"/v1/add/user-details",
			"/v1/user-details/{email}",
			"/v1/user-bankdetails/add",
			"/v1/user-bankdetails/get/{email}",
			"/v1/user-bankdetails/getall",
			"/v1/user-bankdetails/getall/{pageNo}/{pageSize}",
			"/v1/get-fitness-center-by-location",
			"/v1/getLocation",
			"/v1/rating",
			"/v1/get-rating-person/{email}",
			"/v1/uploadFile/{id}",
			"/v1/downloadFile/{id}",
			"/v1/user/set-password",
			"/v1/forget/user/{email}",
			"/v1/get-all-users/{pageNo}/{pageSize}",
			"/v1/mark/users/attendance",
			"/v1/user-performance",
			"/v1/login/admin",
			"/v1/get-all-vendors/{pageNo}/{pageSize}",
			"/v1/get-all-gyms/{pageNo}/{pageSize}",
			"/v1/get-all-gyms-by-email/{email}",
			"/v1/vendor-payment/{vendor}",
			"/v1/get-data-pay",
			"/v1/pay-vendor-now",
			"/v1/update-vendor-payment",
			"/v1/paid-history/{vendor}",
			"/v1/all-numbers",
			"/health",
			"/actuator/**",
			"/env",
			"/metrics"
	};

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
				.authorizeRequests()
				.antMatchers(publicApi).permitAll()
				.antMatchers(org.springframework.http.HttpMethod.OPTIONS).permitAll()
				.anyRequest().authenticated()
				.and()
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
