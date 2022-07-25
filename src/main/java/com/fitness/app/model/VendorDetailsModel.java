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
public class VendorDetailsModel {
    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String email;
    @NotNull
    @StringValidate
    private String gender;
    @NotNull
    private String fullAddress;
    @NotNull
    @StringValidate
    private String city;
    @NotNull
    private Long postal;

}
