package com.fitness.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteGymModel {

	
	private String email;
	private String id;
	private String password;
}
