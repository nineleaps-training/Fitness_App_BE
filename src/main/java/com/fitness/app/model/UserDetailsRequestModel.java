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
@ApiModel(description = "UserDetailsRequestModel")
public class UserDetailsRequestModel {
    
    @NotNull
	@NotBlank
	@NotEmpty
    @Email
    @ApiModelProperty(name = "email", notes = "Email of the user")
    private String email;
    
    @NotBlank
	@NotEmpty
    @ApiModelProperty(name = "gender", notes = "Gender of the user")
    @NotNull
    private String gender;
    @ApiModelProperty(name = "fullAddress", notes = "Address of the user")
    private String fullAddress;
    @ApiModelProperty(name = "city", notes = "City of the user")
    private String city;
    
}
