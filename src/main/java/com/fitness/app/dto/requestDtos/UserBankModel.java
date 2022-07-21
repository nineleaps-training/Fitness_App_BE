package com.fitness.app.dto.requestDtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "UserBankModel")
public class UserBankModel {

    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    @ApiModelProperty(name = "email", notes = "Email of User")
    private String email;
    @ApiModelProperty(name = "Name", notes = "Name of the person")
    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^(a-z|A-Z)$", message = "Only alphabetical char allowed")
    private String name;
    @ApiModelProperty(name = "bankName", notes = "Name of the Bank")
    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^(a-z|A-Z)$", message = "Only alphabetical char allowed")
    private String bankName;
    @ApiModelProperty(name = "bankName", notes = "Name of the Bank")
    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^(a-z|A-Z)$", message = "Only alphabetical char allowed")
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
    private String schedule;

}
