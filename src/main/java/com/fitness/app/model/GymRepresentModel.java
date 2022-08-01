package com.fitness.app.model;

import java.util.List;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.entity.GymTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "GymRepresent")
public class GymRepresentModel {
	@ApiModelProperty(name = "Gid", notes = "Gym id")
	@NotNull
	private String id;
	@ApiModelProperty(name = "VEmail", notes = "Email of Vendor")
	@NotNull
	@NotBlank
	@Email
	@NotEmpty
	private String email;
	@NotNull
	@NotEmpty
	@ApiModelProperty(name = "RGymName", notes = "Name of Gym")
	@NotBlank
	private String gymName;
	@ApiModelProperty(name = "RGymAddress", notes = "Address of gym")
	private GymAddressClass gymAddress;
	@ApiModelProperty(name = "RWorkoutList", notes = "List of workouts")
	private List<String> workoutList;
	@ApiModelProperty(name = "RTiming", notes = "Timings of gym")
	private GymTime timing;
	@ApiModelProperty(name = "RSubscription", notes = "Subscription of gym")
	private GymSubscriptionClass subscription;
	@ApiModelProperty(name = "VContact", notes = "Contact Details of Vendor")
	private Long contact;
	@ApiModelProperty(name = "Grating", notes = "Rating of gym")
	@DecimalMax(value = "5.0", inclusive = true, message = "Value should not exceed 5.0")
	@DecimalMin(value = "0.0", inclusive = true, message = "Value should not be less than 0.0")
	private Double rating;
	@ApiModelProperty(name = "GCapacity", notes = "Capacity of gym")
	@Positive(message = "Value should be positive")
	private int capacity;
}
