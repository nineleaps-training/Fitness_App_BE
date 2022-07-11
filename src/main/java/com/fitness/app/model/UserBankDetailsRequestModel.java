package com.fitness.app.model;


import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "UserBankDetailsRequestModel")
public class UserBankDetailsRequestModel {
    @ApiModelProperty(name = "uEmail", notes = "Email of User")
    @NotNull
	@NotBlank
	@NotEmpty
    @Email
    private String uEmail;
    @ApiModelProperty(name = "uName", notes = "Name of user")
    @NotNull
	@NotBlank
	@NotEmpty
    private String uName;
    @ApiModelProperty(name = "uBankname", notes = "Name of the Bank")
    @NotNull
	@NotBlank
	@NotEmpty
    private String uBankname;
    @ApiModelProperty(name = "uBranchname", notes = "Name of the Branch")
    @NotNull
	@NotBlank
	@NotEmpty
    private String uBranchname;
    @ApiModelProperty(name = "uAccountnumber", notes = "Account number")
    @NotNull
    private Long uAccountnumber;
    @ApiModelProperty(name = "uBankifsc", notes = "IFSC Code of the Bank")
    @NotNull
	@NotBlank
	@NotEmpty
    private String uBankifsc;
   
}
