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
@Document(value = "gym_time")
public class GymTime {

	@Id
	@Field
	private String id;
	@Field
	private String morning;
	@Field
	private String evening;
	@Field
	private String weeklyOff;

	public GymTime(String morning, String evening, String weeklyOff) {
		super();
		this.morning = morning;
		this.evening = evening;
		this.weeklyOff = weeklyOff;
	}

}
