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
@ApiModel(description = "EnthusiastLoginModel")
public class EnthusiastLoginModel {
    @ApiModelProperty(name = "email", notes = "Email of enthusiast")
    @NotNull
    @NotEmpty
    @NotBlank
    @Email
    private String email;
    @ApiModelProperty(name = "password", notes = "Password of enthusiast")
    @NotBlank
    @NotEmpty
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password should contain 1 capital letter, 1 small letter, 1 digit, 1 special symbol and must be 8 or greater")
    private String password;

}
