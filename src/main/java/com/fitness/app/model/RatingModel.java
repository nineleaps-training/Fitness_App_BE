package com.fitness.app.model;

import com.fitness.app.componets.StringValidate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingModel {
    @NotNull
    @NotBlank
    @NotEmpty
    @StringValidate
    private String rid;
    @NotNull
    @NotBlank
    @NotEmpty
    @StringValidate
    private String target;
    @NotNull
    @StringValidate
    private String rater;
    @NotNull
    private double rate;

}
