package com.fitness.app.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import static org.mockito.Mockito.mock;

import java.beans.PropertyEditor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.fitness.app.entity.CustomResponse;
import com.fitness.app.model.ResponseModel;

@DisplayName("Exceptions Test")
@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    GlobalExceptionHandler globalExceptionHandler;

    @Mock
    ResponseModel responseModel;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(globalExceptionHandler).build();
    }

    @DisplayName("Data Not Found Exception Test")
    @Test
    void testDataNotFound() {

        CustomResponse customResponse = new CustomResponse();
        customResponse.setMessage("Testing DataNotFound Exception");
        customResponse.setStatus("NOT_FOUND");
        customResponse.setSuccess(false);

        CustomResponse customResponse2 = globalExceptionHandler
                .dataNotFound(new DataNotFoundException("Testing DataNotFound Exception"));
        Assertions.assertEquals(customResponse.getMessage(), customResponse2.getMessage());
        Assertions.assertEquals(customResponse.getStatus(), customResponse2.getStatus());
        Assertions.assertEquals(customResponse.isSuccess(), customResponse2.isSuccess());

    }

    @DisplayName("Exceeded Number of Attempts Test")
    @Test
    void testExceededNumberOfAttempts() {

        CustomResponse customResponse = new CustomResponse();
        customResponse.setMessage("Testing ExceededNumberOfAttempts Exception");
        customResponse.setStatus("EXCEEDED_NUMBER_OF_ATTEMPTS");
        customResponse.setSuccess(false);

        CustomResponse customResponse2 = globalExceptionHandler
                .exceededNumberOfAttempts(new ExceededNumberOfAttemptsException("Testing ExceededNumberOfAttempts Exception"));
        Assertions.assertEquals(customResponse.getMessage(), customResponse2.getMessage());
        Assertions.assertEquals(customResponse.getStatus(), customResponse2.getStatus());
        Assertions.assertEquals(customResponse.isSuccess(), customResponse2.isSuccess());

    }

    @DisplayName("Data Not Found Exception Test")
    @Test
    void testIncorrectFileUpload() {

        CustomResponse customResponse = new CustomResponse();
        customResponse.setMessage("Testing IncorrectFileUpload Exception");
        customResponse.setStatus("FILE_TYPE_ERROR");
        customResponse.setSuccess(false);

        CustomResponse customResponse2 = globalExceptionHandler
                .incorrectFileUpload(new IncorrectFileUploadException("Testing IncorrectFileUpload Exception"));
        Assertions.assertEquals(customResponse.getMessage(), customResponse2.getMessage());
        Assertions.assertEquals(customResponse.getStatus(), customResponse2.getStatus());
        Assertions.assertEquals(customResponse.isSuccess(), customResponse2.isSuccess());

    }

    @Test
    void testHandle() {

        Exception ex = new Exception("Internal Server Error");
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseEntity<Object> responseEntity2 = globalExceptionHandler.handle(ex, request, response);
        Assertions.assertEquals(responseEntity, responseEntity2);
    }

    @Test
    void testHandleFileUploadError() {

        Exception ex = new Exception("File Size should be less than 2MB");
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseEntity<Object> responseEntity2 = globalExceptionHandler.handleFileUploadError(ex, request, response);
        Assertions.assertEquals(responseEntity, responseEntity2);
    }

    @Test
    void testHandleMethodArgumentNotValid() {

        Map<String, String> errors = new HashMap<>();
        errors.put("field", "defaultMessage");
        ObjectError objectError = new FieldError("objectName", "field", "defaultMessage");
        List<ObjectError> objectErrors = new ArrayList<>();
        objectErrors.add(objectError);
        BindingResult bindingResult = new BindingResult() {

            @Override
            public String getObjectName() {
                return null;
            }

            @Override
            public void setNestedPath(String nestedPath) {

            }

            @Override
            public String getNestedPath() {
                return null;
            }

            @Override
            public void pushNestedPath(String subPath) {

            }

            @Override
            public void popNestedPath() throws IllegalStateException {

            }

            @Override
            public void reject(String errorCode) {

            }

            @Override
            public void reject(String errorCode, String defaultMessage) {

            }

            @Override
            public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {

            }

            @Override
            public void rejectValue(String field, String errorCode) {

            }

            @Override
            public void rejectValue(String field, String errorCode, String defaultMessage) {

            }

            @Override
            public void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) {

            }

            @Override
            public void addAllErrors(Errors errors) {

            }

            @Override
            public boolean hasErrors() {
                return false;
            }

            @Override
            public int getErrorCount() {
                return 0;
            }

            @Override
            public List<ObjectError> getAllErrors() {
                return objectErrors;
            }

            @Override
            public boolean hasGlobalErrors() {
                return false;
            }

            @Override
            public int getGlobalErrorCount() {
                return 0;
            }

            @Override
            public List<ObjectError> getGlobalErrors() {
                return null;
            }

            @Override
            public ObjectError getGlobalError() {
                return null;
            }

            @Override
            public boolean hasFieldErrors() {
                return false;
            }

            @Override
            public int getFieldErrorCount() {
                return 0;
            }

            @Override
            public List<FieldError> getFieldErrors() {
                return null;
            }

            @Override
            public FieldError getFieldError() {
                return null;
            }

            @Override
            public boolean hasFieldErrors(String field) {
                return false;
            }

            @Override
            public int getFieldErrorCount(String field) {
                return 0;
            }

            @Override
            public List<FieldError> getFieldErrors(String field) {
                return null;
            }

            @Override
            public FieldError getFieldError(String field) {
                return null;
            }

            @Override
            public Object getFieldValue(String field) {
                return null;
            }

            @Override
            public Class<?> getFieldType(String field) {
                return null;
            }

            @Override
            public Object getTarget() {
                return null;
            }

            @Override
            public Map<String, Object> getModel() {
                return null;
            }

            @Override
            public Object getRawFieldValue(String field) {
                return null;
            }

            @Override
            public PropertyEditor findEditor(String field, Class<?> valueType) {
                return null;
            }

            @Override
            public PropertyEditorRegistry getPropertyEditorRegistry() {
                return null;
            }

            @Override
            public String[] resolveMessageCodes(String errorCode) {
                return null;
            }

            @Override
            public String[] resolveMessageCodes(String errorCode, String field) {
                return null;
            }

            @Override
            public void addError(ObjectError error) {

            }

        };
        MethodParameter methodParameter = null;
        MethodArgumentNotValidException ex1 = new MethodArgumentNotValidException(methodParameter, bindingResult);
        WebRequest webRequest = mock(WebRequest.class);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        HttpHeaders httpHeaders = mock(HttpHeaders.class);
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        ResponseEntity<Object> responseEntity2 = globalExceptionHandler.handleMethodArgumentNotValid(ex1, httpHeaders,
                httpStatus, webRequest);
        Assertions.assertEquals(responseEntity, responseEntity2);

    }
}
