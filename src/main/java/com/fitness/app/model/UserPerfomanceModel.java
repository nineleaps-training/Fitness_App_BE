package com.fitness.app.model;

import java.util.List;

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
@ApiModel(description = "UserPerfomanceModel")
public class UserPerfomanceModel {

	@ApiModelProperty(name = "name", notes = "Name of User")
	@NotNull
	@NotBlank
	@NotEmpty
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
	private Double rating;
}
