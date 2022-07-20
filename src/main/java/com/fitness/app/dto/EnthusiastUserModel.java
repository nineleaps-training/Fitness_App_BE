package com.fitness.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnthusiastUserModel {

    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
