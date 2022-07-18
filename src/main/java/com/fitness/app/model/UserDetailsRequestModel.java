package com.fitness.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "UserDetailsRequestModel")
public class UserDetailsRequestModel {

	@ApiModelProperty(name = "email", notes = "Email of Vendor")
	@NotNull
	@NotBlank
	@NotEmpty
	@Email
	private String email;
	@NotNull
	@NotBlank
	@NotEmpty
	@ApiModelProperty(name = "gender", notes = "Gender of Vendor")
	@Pattern(regexp = "^(Male|Female|Others)$", message = "Gender allowed input: Male, Female or Others")
	private String gender;
	@ApiModelProperty(name = "fullAddress", notes = "Address of Vendor")
	@NotNull
	@NotBlank
	@NotEmpty
	private String fullAddress;
	@NotNull
	@NotBlank
	@NotEmpty
	@ApiModelProperty(name = "city", notes = "City of Vendor")
	private String city;
	@ApiModelProperty(name = "postal", notes = "Postal Code")
	@NotNull
	// @Pattern(regexp = "^[1-9][\\d]{2}\\s?[\\d]{3}$")
	private long postal;

}
