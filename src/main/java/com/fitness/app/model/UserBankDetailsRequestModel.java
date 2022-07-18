package com.fitness.app.model;

import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "UserBankDetailsRequestModel")
public class UserBankDetailsRequestModel {

    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    @ApiModelProperty(name = "email", notes = "Email of User")
    private String email;
    @ApiModelProperty(name = "name", notes = "Name of user")
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;
    @ApiModelProperty(name = "bankName", notes = "Name of the Bank")
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(max = 25)
    private String bankName;
    @ApiModelProperty(name = "branchName", notes = "Name of the Branch")
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(max = 25)
    private String branchName;
    @ApiModelProperty(name = "accountNumber", notes = "Account number")
    @NotNull
    private Long accountNumber;
    @ApiModelProperty(name = "bankIFSC", notes = "IFSC Code of the Bank")
    @NotNull
    @NotBlank
    @NotEmpty
    private String bankIFSC;
    @ApiModelProperty(name = "paymentSchedule", notes = "Payment Schedule")
    private String paymentSchedule;

}
