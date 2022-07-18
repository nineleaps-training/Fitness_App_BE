package com.fitness.app.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "UserForgot")
public class UserForgot {
    @ApiModelProperty(name = "otp", notes = "OTP")
    private String otp;
    @ApiModelProperty(name = "bool", notes = "True or False")
    private boolean bool;

}
