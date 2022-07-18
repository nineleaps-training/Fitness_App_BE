package com.fitness.app.model;

import java.util.List;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "UserPerfomanceModel")
public class UserPerfomanceModel {

	@ApiModelProperty(name = "name", notes = "Name of User")
	@NotNull
	@NotBlank
	@NotEmpty
	@Size(max = 25)
	private String name;
	@ApiModelProperty(name = "email", notes = "Email of User")
	@NotNull
	@NotBlank
	@NotEmpty
	private String email;
	@ApiModelProperty(name = "gym", notes = "Gym of User")
	@NotNull
	@NotBlank
	@NotEmpty
	private String gym;
	@ApiModelProperty(name = "vendor", notes = "Vendor associated")
	@NotNull
	@NotBlank
	@NotEmpty
	private String vendor;
	@ApiModelProperty(name = "attendance", notes = "Attendace of User")
	@NotEmpty
	private List<Integer> attendance;
	@ApiModelProperty(name = "rating", notes = "Rating of user")
	@NotNull
	@NotBlank
	@NotEmpty
	@DecimalMax(value = "5.0", inclusive = true, message = "Value should not exceed 5.0")
	@DecimalMin(value = "0.0", inclusive = true, message = "Value should not be less than 0.0")
	private Double rating;
}
