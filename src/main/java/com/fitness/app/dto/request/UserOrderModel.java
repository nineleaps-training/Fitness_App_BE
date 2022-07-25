package com.fitness.app.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "UserOrderModel")
public class UserOrderModel {

    @Email
    @ApiModelProperty(name = "email", notes = "Email of User")
    @NotNull
    @NotBlank
    @NotEmpty
    private String email;
    @ApiModelProperty(name = "gym", notes = "Gym of User")
    @NotNull
    @NotEmpty
    @NotBlank
    private String gym;
    @ApiModelProperty(name = "services", notes = "List of Services")
    private List<String> services;
    @ApiModelProperty(name = "subscription", notes = "Subscription purchased by user")
    private String subscription;
    @ApiModelProperty(name = "amount", notes = "Amount to be paid")
    @NotNull
    @Min(value = 1, message = "Amount should be atleast 1")
    private int amount;
    @ApiModelProperty(name = "slot", notes = "Slot of the gym")
    private String slot;
}
