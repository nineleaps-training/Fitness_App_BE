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
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "EnthusiastUserModel")
public class EnthusiastUserModel {

    @ApiModelProperty(name = "email", notes = "Email of Enthusiast")
    @Email
    @NotNull
	@NotBlank
	@NotEmpty
    private String email;
    @NotNull
	@NotBlank
	@NotEmpty
    @ApiModelProperty(name = "firstName", notes = "First name of Enthusiast")
    private String firstName;
    @ApiModelProperty(name = "lastName", notes = "Last name of Enthusiast")
    private String lastName;
    @ApiModelProperty(name = "password", notes = "Password of Enthusiast")
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",message="Password length must be 8 or greater")
    @NotNull
	@NotBlank
	@NotEmpty
    private String password;
}
