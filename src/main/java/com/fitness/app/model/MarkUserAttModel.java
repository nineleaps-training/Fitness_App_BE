package com.fitness.app.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarkUserAttModel {

	
	private String gym;
	private String vendor;
	private List<String> users;
}
