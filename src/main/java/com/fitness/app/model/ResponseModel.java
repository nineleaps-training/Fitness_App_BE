package com.fitness.app.model;



import org.springframework.stereotype.Component;

import com.fitness.app.entity.CustomResponse;
@Component
public class ResponseModel {
    public CustomResponse changeResponse(boolean success, String message, String status) {
        CustomResponse response = new CustomResponse();
        response.setMessage(message);
        response.setStatus(status);
        response.setSuccess(success);
        return response;
    }
}