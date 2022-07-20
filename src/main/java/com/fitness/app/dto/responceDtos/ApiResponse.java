package com.fitness.app.dto.responceDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiResponse {
      private HttpStatus status;
      private Object body;
}
