package com.fitness.app.exception;

import com.fitness.app.model.ApiResponses;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DataNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ApiResponses dataNotFound(final DataNotFoundException e) {
        ApiResponses apiResponses = new ApiResponses();
        apiResponses.setStatus("NOT_FOUND");
        apiResponses.setMessage(e.getMessage());
        apiResponses.setSuccess(false);
        return apiResponses;
    }
}
