package com.fitness.app.model;

import java.util.List;

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
public class MarkUserAttModel {

    @NotNull
    @NotBlank
    @NotEmpty
    @StringValidate
    private String gym;
    @NotNull
    @NotBlank
    @NotEmpty
    @StringValidate
    private String vendor;
    @NotNull
    @StringValidate
    private List<String> users;
}
