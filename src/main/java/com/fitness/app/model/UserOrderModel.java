package com.fitness.app.model;

import java.util.List;

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
public class UserOrderModel {

    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String email;
    @NotNull
    @NotBlank
    @NotEmpty
    @StringValidate
    private String gym;
    @StringValidate
    private List<String> services;
    @StringValidate
    private String subscription;
    @NotNull
    private int amount;
    @StringValidate
    private String slot;

}
