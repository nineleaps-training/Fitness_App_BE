package com.fitness.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "VendorBankDetailsRequestModel")
public class VendorBankDetailsRequestModel {

    
    @NotNull
    @NotEmpty
	@NotBlank
    @Email
    @ApiModelProperty(name = "Vemail", notes = "Email of Vendor")
	private String email;
    @ApiModelProperty(name = "Vname", notes = "Name of Vendor")
    @NotNull
    @NotEmpty
	@NotBlank
	private String name;
	@NotBlank
	@NotEmpty
    @NotNull
    @ApiModelProperty(name = "VbankName", notes = "Bank Name of Vendor")
    private String bankName;
    @ApiModelProperty(name = "VbranchName", notes = "Branch Name of Vendor")
    @NotNull
	@NotBlank
	@NotEmpty
    private String branchName;
    @ApiModelProperty(name = "VaccountNumber", notes = "Account Number of Vendor")
    @NotNull
    private Long accountNumber;
    @ApiModelProperty(name = "VbankIFSC", notes = "IFSC Code of Bank")
    @NotNull
	@NotBlank
	@NotEmpty
    private String bankIFSC;
    @ApiModelProperty(name = "VpaymentSchedule", notes = "Payment Schedule")
    @NotNull
	@NotBlank
	@NotEmpty
	private String paymentSchedule;
}
