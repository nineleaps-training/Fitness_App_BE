package com.fitness.app.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fitness.app.entity.CustomResponse;

@DisplayName("Exceptions Test")
@SpringBootTest
class ExceptionTest {

    @Autowired
	GlobalExceptionHandler globalexceptionhandler;

	@DisplayName("Data Not Found Exception Test")
	@Test
	void dataNotFoundExceptionTest() {
		CustomResponse customResponse = new CustomResponse();
		customResponse.setMessage("Testing DataNotFound Exception");
		customResponse.setStatus("NOT_FOUND");
		customResponse.setSuccess(false);

		CustomResponse customResponse2 = globalexceptionhandler
				.dataNotFound(new DataNotFoundException("Testing DataNotFound Exception"));
		Assertions.assertEquals(customResponse.getMessage(),customResponse2.getMessage());
		Assertions.assertEquals(customResponse.getStatus(),customResponse2.getStatus());
		Assertions.assertEquals(customResponse.isSuccess(),customResponse2.isSuccess());
	}
    
}
