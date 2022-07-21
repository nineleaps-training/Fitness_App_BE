package com.fitness.app.dto.responceDtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForgot {
    private String otp;
	private boolean bool;
	
}
