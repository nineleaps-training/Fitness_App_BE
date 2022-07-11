package com.fitness.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsModel {


    private String userEmail;
    private String userGender;
    private String userFullAddress;
    private String userCity;
    private Long userPostal;

}
