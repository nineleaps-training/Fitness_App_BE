package com.fitness.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fitness.app.entity.CustomResponse;
import com.fitness.app.model.ResponseModel;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    ResponseModel responseModel;

    public GlobalExceptionHandler(ResponseModel responseModel) {
        this.responseModel = responseModel;
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody CustomResponse dataNotFound(final DataNotFoundException exception) {
        return responseModel.changeResponse(false, exception.getMessage(), "NOT_FOUND");
    }
}