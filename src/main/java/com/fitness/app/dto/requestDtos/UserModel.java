package com.fitness.app.dto.requestDtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

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
    @Pattern(regexp = "^(a-z|A-Z)$", message = "Only alphabetical char allowed")
    @NotNull
    private String fullName;
    @ApiModelProperty(name = "mobile", notes = "Mobile number of User")
    @Pattern(regexp = "^(0-9)$", message = "Only numeric char allowed")
    @NotNull
    private String mobile;
    @ApiModelProperty(name = "password", notes = "Password of User")
    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password should contain 1 capital letter, 1 small letter, 1 digit, 1 special symbol and must be 8 or greater")
    private String password;
    @ApiModelProperty(name = "role", notes = "role of User")
    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^(USER|VENDOR)$", message = "role allowed input: USER or VENDOR")
    private String role;
    @ApiModelProperty(name = "custom", notes = "is customized or google account of User")
    @NotNull
    @NotBlank
    @NotEmpty
    private Boolean custom;


}
