package com.fitness.app.dto;

import java.time.LocalDate;
import java.util.List;

import com.fitness.app.entity.GymAddressClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookedGymModel {

	private String id;
	private String gymName;
	private String vendor;
	private List<String> service;
	private String slot;
	private LocalDate endDate;
	private GymAddressClass address;
	private Long contact;
	private Double rating;
}

//some new chamngbes