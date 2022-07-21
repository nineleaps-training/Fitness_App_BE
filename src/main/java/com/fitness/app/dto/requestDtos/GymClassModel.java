package com.fitness.app.dto.requestDtos;

import java.util.List;

import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.entity.GymTimeClass;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "GymClassModel")
public class GymClassModel {
	@ApiModelProperty(name = "vendorEmail", notes = "Email of Vendor")
	@NotNull
	@NotBlank
	@NotEmpty
	@Email
	private String vendor_email;
	@ApiModelProperty(name = "GymName", notes = "Name of Gym")
	@NotNull
	@NotBlank
	@NotEmpty
	@Pattern(regexp = "^(a-z|A-Z)$", message = "Only alphabetical char allowed")
	private String gym_name;
	@ApiModelProperty(name = "GymAddress", notes = "Address of Gym")
	@NotNull
	@NotBlank
	@NotEmpty
	@Pattern(regexp = "^(a-z|A-Z)$", message = "Only alphabetical char allowed")
	private GymAddressClass gymAddress;
	@ApiModelProperty(name = "WorkoutList", notes = "Workout list of Gym")
	@NotNull
	@NotBlank
	@NotEmpty
	private List<String> workoutList;
	@ApiModelProperty(name = "timing", notes = "timings of  of Gym")
	@NotNull
	@NotBlank
	@NotEmpty
	private GymTimeClass timing;
	@ApiModelProperty(name = "subscription", notes = "subscription of  of Gym")
	@NotNull
	@NotBlank
	private GymSubscriptionClass subscription;
	@ApiModelProperty(name = "contact", notes = "contact of  of Gym")
	@NotNull
	@NotBlank
	@Pattern(regexp = "^(0-9)$", message = "Only numerics are allowed")
	private Long contact;
	@ApiModelProperty(name = "contact", notes = "capacity of  of Gym")
	@NotNull
	@NotBlank
	@Pattern(regexp = "^(0-9)$", message = "Only numerics are allowed")
	private int capacity;
}
