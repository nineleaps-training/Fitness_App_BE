package com.fitness.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailsModel {

	private String email;
	private String gender;
	private String fullAddress;
	private String city;
	private long postal;
}
