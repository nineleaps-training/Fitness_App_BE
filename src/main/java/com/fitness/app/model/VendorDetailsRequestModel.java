package com.fitness.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "VendorDetailsRequestModel")
public class VendorDetailsRequestModel {

    @ApiModelProperty(name = "email", notes = "Email of Vendor")
	@NotNull
	@NotBlank
	@NotEmpty
	@Email
	private String vEmail;
	@ApiModelProperty(name = "gender", notes = "Gender of Vendor")
	@NotNull
	@NotBlank
	@NotEmpty
	private String vGender;
	@ApiModelProperty(name = "fullAddress", notes = "Address of Vendor")
	private String vFullAddress;
	@ApiModelProperty(name = "city", notes = "City of Vendor")
	private String vCity;
	@ApiModelProperty(name = "postal", notes = "Postal Code")
	@NotNull
	// @Pattern(regexp = "^[1-9][\\d]{2}\\s?[\\d]{3}$")
	private long vPostal;
}
