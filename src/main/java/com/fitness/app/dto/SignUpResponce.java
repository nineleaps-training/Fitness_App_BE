package com.fitness.app.dto;

import com.fitness.app.entity.UserClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponce {

	private UserClass CurrentUser;
	private String message;
	
}
