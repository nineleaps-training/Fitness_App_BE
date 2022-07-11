package com.fitness.app.model;

import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	@ApiModelProperty(name = "mGymname", notes = "Name of Gym")
	@NotNull
	@NotBlank
	@NotEmpty
	private String mGymname;
	@ApiModelProperty(name = "mGymaddress", notes = "Address of Gym")
	private GymAddressClass mGymaddress;
	@ApiModelProperty(name = "mWorkoutlist", notes = "List of workouts")
	@NotEmpty
	private List<String> mWorkoutlist;
	@ApiModelProperty(name = "mTiming", notes = "Timings of gym")
	private GymTime mTiming;
	@ApiModelProperty(name = "mSubscription", notes = "Subscriptions of gym")
	private GymSubscriptionClass mSubscription;
	@ApiModelProperty(name = "mContact", notes = "Vendor Contact Details")
	private Long mContact;
	@ApiModelProperty(name = "mCapacity", notes = "Capacity of gym")
	private int mCapacity;
}
