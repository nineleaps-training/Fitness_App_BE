package com.fitness.app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderModel {

	
	private String email;
	private String gym;
	private List<String> services;
	private String subscription;
	private int amount;
	private String slot;
}
