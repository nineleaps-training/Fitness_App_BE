package com.fitness.app.model;

import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
@ApiModel(description = "GymRepresnt")
public class GymRepresnt {
	@ApiModelProperty(name = "Gid", notes = "Gym id")
	@NotNull
	private String id;
	@ApiModelProperty(name = "Vemail", notes = "Email of Vendor")
	@NotNull
	@NotBlank
	@NotEmpty
	@Email
	private String email;
	@NotNull
	@NotBlank
	@NotEmpty
	@ApiModelProperty(name = "RgymName", notes = "Name of Gym")
	private String gymName;
	@ApiModelProperty(name = "RgymAddress", notes = "Address of gym")
	private GymAddressClass gymAddress;
	@ApiModelProperty(name = "RworkoutList", notes = "List of workouts")
	private List<String> workoutList;
	@ApiModelProperty(name = "Rtiming", notes = "Timings of gym")
	private GymTime timing;
	@ApiModelProperty(name = "Rsubscription", notes = "Subscription of gym")
	private GymSubscriptionClass subscription;
	@ApiModelProperty(name = "Vcontact", notes = "Contact Details of Vendor")
	private Long contact;
	@ApiModelProperty(name = "Grating", notes = "Rating of gym")
	private Double rating;
	@ApiModelProperty(name = "Gcapacity", notes = "Capacity of gym")
	private int capacity;
}
