package com.fitness.app.model;

import com.fitness.app.componets.StringValidate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsModel {
    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String userEmail;
    @NotNull
    @StringValidate
    private String userGender;
    @NotNull
    private String userFullAddress;
    @NotNull
    @StringValidate
    private String userCity;
    @NotNull
    private Long userPostal;

}
