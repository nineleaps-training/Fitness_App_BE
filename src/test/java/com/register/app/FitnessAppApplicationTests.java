package com.register.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringBootConfiguration
class FitnessAppApplicationTests {

	@Test
	void contextLoads() {
        Assertions.assertDoesNotThrow(this::doNotThrowException);
	}

    private void doNotThrowException(){
        //This method will never throw exception
    }

}
