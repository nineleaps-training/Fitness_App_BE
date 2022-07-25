package com.fitness.app.model;

import java.util.List;

import com.fitness.app.componets.StringValidate;
import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.entity.GymTime;

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
public class GymClassModel {

    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String vendorEmail;
    @NotNull
    @NotBlank
    @NotEmpty
    @StringValidate
    private String gymName;
    @NotNull
    @NotBlank
    private GymAddressClass gymAddress;
    @NotNull
    private List<String> workoutList;
    private GymTime timing;
    private GymSubscriptionClass subscription;
    @NotNull
    @NotBlank
    @NotEmpty
    private Long contact;
    @NotNull
    private int capacity;

}
