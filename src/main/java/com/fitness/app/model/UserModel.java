package com.fitness.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    private String email;
    private String fullName;
    private String mobile;
    private String password;
    private String role;
    private Boolean custom;

}
