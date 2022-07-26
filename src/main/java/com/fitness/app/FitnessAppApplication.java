package com.fitness.app;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class FitnessAppApplication {

	@Autowired
	Environment environment;

	//Main Method to run the application
	public static void main(String[] args) {
		SpringApplication.run(FitnessAppApplication.class, args);
	}

	//Integration of Swagger for API Documentation
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.fitness.app")).build();
	}

	@Bean
	public RazorpayClient razorpayClient() throws RazorpayException {
		return new RazorpayClient(environment.getProperty("razorPayKey"), environment.getProperty("razorpaySecret"));
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
