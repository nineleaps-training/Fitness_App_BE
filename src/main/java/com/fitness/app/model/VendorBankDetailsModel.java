package com.fitness.app.model;

import com.fitness.app.componets.StringValidate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorBankDetailsModel {

    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String email;
    @NotNull
    @NotBlank
    @NotEmpty
    @StringValidate
    private String name;
    @NotNull
    @NotBlank
    @NotEmpty
    @StringValidate
    private String bankName;
    @NotNull
    @NotBlank
    @NotEmpty
    @StringValidate
    private String branchName;
    @NotNull
    @NotBlank
    @NotEmpty
    private Long accountNumber;
    @NotNull
    @NotBlank
    @NotEmpty
    private String bankIFSC;
    @NotNull
    @NotBlank
    @NotEmpty
    @StringValidate
    private String paymentSchedule;

}
