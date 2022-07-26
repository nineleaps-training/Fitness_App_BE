package com.fitness.app.exception;

import com.fitness.app.model.ApiResponses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    MockMvc mockMvc;

    @InjectMocks
    GlobalExceptionHandler globalExceptionHandler;

    @Test
    void dataNotFound() {
        ApiResponses apiResponses = new ApiResponses("NOT_FOUND", "Data Not Found Exception!", false);

        ApiResponses actual = globalExceptionHandler.dataNotFound(new DataNotFoundException("Data Not Found Exception!"));

        assertEquals(apiResponses.getStatus(), actual.getStatus());
        assertEquals(apiResponses.getMessage(), actual.getMessage());
        assertEquals(apiResponses.isSuccess(), actual.isSuccess());

    }
}