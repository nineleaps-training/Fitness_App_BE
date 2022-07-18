package com.fitness.app.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "DeleteGymModel")
public class DeleteGymModel {

	@ApiModelProperty(name = "email", notes = "Vendor Email")
	@NotNull
	@NotBlank
	@NotEmpty
	@Email
	private String email;
	@ApiModelProperty(name = "id", notes = "Gym id")
	@NotNull
	private String id;
	@ApiModelProperty(name = "password", notes = "Password of vendor")
	@NotNull
	@NotBlank
	@NotEmpty
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password should contain 1 capital letter, 1 small letter, 1 digit, 1 special symbol and must be 8 or greater")
	private String password;
}
