package com.fitness.app.model;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "UserOrderModel")
public class UserOrderModel {

	@Email
	@ApiModelProperty(name = "email", notes = "Email of User")
	@NotNull
	@NotBlank
	@NotEmpty
	private String email;
	@ApiModelProperty(name = "gym", notes = "Gym of User")
	@NotNull
	@NotEmpty
	@NotBlank
	private String gym;
	@ApiModelProperty(name = "services", notes = "List of Services")
	private List<String> services;
	@ApiModelProperty(name = "subscription", notes = "Subscription purchased by user")
	private String subscription;
	@ApiModelProperty(name = "amount", notes = "Amount to be paid")
	@NotNull
	@Min(value = 1, message = "Amount should be at least 1")
	private int amount;
	@ApiModelProperty(name = "slot", notes = "Slot of the gym")
	private String slot;
}
