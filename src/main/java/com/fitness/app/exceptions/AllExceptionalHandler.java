package com.fitness.app.exceptions;

import com.fitness.app.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AllExceptionalHandler {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ApiResponse>userNotFound(UserNotFoundException ex)
    {
        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DataNotFoundException.class})
    public ResponseEntity<ApiResponse>dataNotFound(DataNotFoundException ex)
    {
        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({FileNotFoundException.class})
    public ResponseEntity<ApiResponse>fileNotFound(FileNotFoundException ex)
    {
        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({HttpMethodCodeException.class})
    public ResponseEntity<ApiResponse>httpMethodException(HttpMethodCodeException ex)
    {
        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserAuthException.class})
    public ResponseEntity<ApiResponse>userAuthException(UserAuthException ex)
    {
        return new ResponseEntity<>(new ApiResponse(HttpStatus.NO_CONTENT, ex.getMessage()), HttpStatus.NOT_FOUND);
    }


}
