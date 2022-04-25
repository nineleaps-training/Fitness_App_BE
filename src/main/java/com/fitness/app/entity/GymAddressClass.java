package com.fitness.app.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "gym_address")
public class GymAddressClass {

	@Id
	@Field
	private String id;
	@Field
	private String building;
	@Field
	private String locality;
	@Field
	private String city;
	@Field
	private int postal;

	public GymAddressClass(String building, String loaclity, String city, int postal) {
		super();
		this.building = building;
		this.locality = loaclity;
		this.city = city;
		this.postal = postal;
	}

}
