package com.fitness.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fitness.app.security.service.UserDetailsServiceImpl;

import okhttp3.internal.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class WebConfiguration extends WebSecurityConfigurerAdapter{
   
	private static String[] PUBLIC_API= {
			"/gyms/locality/{locality}",
			"/gym/city/{city}",
			"/gym/id/**",
			
			"/login/user",
			"/verify/user/**",
			"/register/user",
			"/hello",
			"/login/admin",

			"/forget/user/**",
			"/user/set-password",

            "/user-performance",
            "/all-numbers",
			
			

		

			"/swagger-ui/*",
			"/swagger-resources/**",
			"/v2/api-docs/**",
			"/swagger-ui.html",
			"/favicon.ico"


	};
	
	private static String[] ADMIN_API= {
			"/get/enthusiast",
	};
	private static String[] USER_API= {
			"/get/enthusiast"
	};
	private static String[] VENDOR_API= {};
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
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
	        auth.userDetailsService(userDetailsServiceImpl );
	}




	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http
         .csrf()
         .disable()
         .cors()
         .disable()
         .authorizeRequests()
         .antMatchers(PUBLIC_API).permitAll()
         .antMatchers(org.springframework.http.HttpMethod.OPTIONS).permitAll()
         .anyRequest().authenticated()
         .and()
         .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
         .and()
         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

 http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
     
	 
	 
	 
	/*
    @Bean
    public WebMvcConfigurer configur() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedHeaders("Access-Control-Allow-Origin", "Content-Type", "Authorization")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowCredentials(true);
            }

        };
    }*/
}
