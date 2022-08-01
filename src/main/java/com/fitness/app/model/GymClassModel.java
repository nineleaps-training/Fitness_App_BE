package com.fitness.app.model;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.entity.GymTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	private String vendorEmail;
	@ApiModelProperty(name = "mGymName", notes = "Name of Gym")
	@NotNull
	@NotBlank
	@NotEmpty
	@Size(max = 25)
	private String mGymName;
	@ApiModelProperty(name = "mGymAddress", notes = "Address of Gym")
	private GymAddressClass mGymAddress;
	@ApiModelProperty(name = "mWorkoutList", notes = "List of workouts")
	@NotEmpty
	private List<String> mWorkoutList;
	@ApiModelProperty(name = "mTiming", notes = "Timings of gym")
	private GymTime mTiming;
	@ApiModelProperty(name = "mSubscription", notes = "Subscriptions of gym")
	private GymSubscriptionClass mSubscription;
	@ApiModelProperty(name = "mContact", notes = "Vendor Contact Details")
	@NotNull
	private Long mContact;
	@ApiModelProperty(name = "mCapacity", notes = "Capacity of gym")
	@NotNull
	@Positive(message = "Value should be positive")
	private int mCapacity;
}
