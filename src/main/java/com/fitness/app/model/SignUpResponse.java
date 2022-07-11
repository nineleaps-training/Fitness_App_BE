package com.fitness.app.model;

import com.fitness.app.entity.UserClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponse {

    private UserClass currentUser;

    private String message;

}
