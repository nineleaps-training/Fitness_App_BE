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
@Document(value = "subscription")
public class GymSubscriptionClass {

	@Id
	@Field
	private String id;
	@Field
	private int oneWorkout;
	@Field
	private int monthly;
	@Field
	private int quarterly;
	@Field
	private int half;
	@Field
	private int yearly;
	@Field
	private int offer;

	public GymSubscriptionClass(int oneWorkout, int monthly, int quarterly, int half, int yearly, int offer) {
		super();
		this.oneWorkout = oneWorkout;
		this.monthly = monthly;
		this.quarterly = quarterly;
		this.half = half;
		this.yearly = yearly;
		this.offer = offer;
	}

}
