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
	private Double lat;
	@Field
	private Double lng;
	@Field
	private String address;
	@Field
	private String city;

	public GymAddressClass(Double lat, Double lng, String address, String city) {
		this.lat = lat;
		this.lng = lng;
		this.address = address;
		this.city = city;
	}
}
