package com.fitness.app.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "DetailsModel")
public class DetailsModel {
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
	@Pattern(regexp = "^(a-z|0-9|A-Z)$", message = "Only alphabetical char allowed")
	private String fullAddress;
	@NotNull
	@NotBlank
	@NotEmpty
	@ApiModelProperty(name = "city", notes = "City of Vendor")
	@Pattern(regexp = "^(a-z|A-Z)$", message = "Only alphabetical char allowed")
	private String city;
	@ApiModelProperty(name = "postal", notes = "Postal Code")
	@NotNull
	private long postal;
}
