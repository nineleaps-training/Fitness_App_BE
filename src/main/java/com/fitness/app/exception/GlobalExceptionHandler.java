package com.fitness.app.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.validation.FieldError;

import com.fitness.app.entity.CustomResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Handling Data Not Found Exceptions
    /**
     * 
     * @param exception
     * @return
     */
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody CustomResponse dataNotFound(final DataNotFoundException exception) {
        CustomResponse customResponse = new CustomResponse();
        customResponse.setMessage(exception.getMessage());
        customResponse.setStatus("NOT_FOUND");
        customResponse.setSuccess(false);
        return customResponse;
    }

    // Handling Exceeded Number of Attempt Exceptions
    /**
     * 
     * @param exception
     * @return
     */
    @ExceptionHandler(ExceededNumberOfAttemptsException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody CustomResponse exceededNumberOfAttempts(final ExceededNumberOfAttemptsException exception) {
        CustomResponse customResponse = new CustomResponse();
        customResponse.setMessage(exception.getMessage());
        customResponse.setStatus("EXCEEDED_NUMBER_OF_ATTEMPTS");
        customResponse.setSuccess(false);
        return customResponse;
    }
    
    // Handling Field Error Exceptions
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders header, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Handling Internal Server Exceptions
    /**
     * 
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex, HttpServletRequest request, HttpServletResponse response) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handling Maximum Upload Size Exceeded Exceptions
    /**
     * 
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleFileUploadError(Exception ex, HttpServletRequest request,
            HttpServletResponse response) {

        return new ResponseEntity<>("File Size should be less than 2MB", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handling Incorrect File Upload Exceptions
    /**
     * 
     * @param exception
     * @return
     */
    @ExceptionHandler(IncorrectFileUploadException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody CustomResponse incorrectFileUpload(final IncorrectFileUploadException exception) {
        CustomResponse customResponse = new CustomResponse();
        customResponse.setMessage(exception.getMessage());
        customResponse.setStatus("FILE_TYPE_ERROR");
        customResponse.setSuccess(false);
        return customResponse;
    }
}