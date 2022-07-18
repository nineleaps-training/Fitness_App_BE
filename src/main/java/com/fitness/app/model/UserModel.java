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
@ApiModel(description = "UserModel")
public class UserModel {
    @ApiModelProperty(name = "email", notes = "Email of User")
    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String email;
    @ApiModelProperty(name = "fullName", notes = "Name of User")
    private String fullName;
    @ApiModelProperty(name = "mobile", notes = "Contact number of User")
    private String mobile;
    @ApiModelProperty(name = "password", notes = "Password of User")
    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password should contain 1 capital letter, 1 small letter, 1 digit, 1 special symbol and must be 8 or greater")
    private String password;
    @ApiModelProperty(name = "role", notes = "Role of user")
    private String role;
    @ApiModelProperty(name = "custom", notes = "True or False")
    private Boolean custom;

}
