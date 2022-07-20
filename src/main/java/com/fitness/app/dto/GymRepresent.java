package com.fitness.app.dto;

import java.util.List;

import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.entity.GymTimeClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GymRepresent {

	private String id;
	private String email;
	private String gym_name;
	private GymAddressClass gymAddress;
	private List<String> workoutList;
	private GymTimeClass timing;
	private GymSubscriptionClass subscription;
	private Long contact;
	private Double rating;
	private int capacity;
}
