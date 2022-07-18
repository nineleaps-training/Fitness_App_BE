package com.fitness.app.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "ResponseModel")
public class ResponseModel {

    private String status;
    private String message;
    private boolean success;

}