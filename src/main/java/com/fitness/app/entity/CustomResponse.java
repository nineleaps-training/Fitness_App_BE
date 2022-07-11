package com.fitness.app.entity;

import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse {
    private String status;
    private String message;
    private boolean success;
}