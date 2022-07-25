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
public class UserBankDetailsModel {

    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String userEmail;
    @NotNull
    @NotBlank
    @NotEmpty
    @StringValidate
    private String userName;
    @NotNull
    @NotBlank
    @NotEmpty
    @StringValidate
    private String userBankName;
    @NotNull
    @NotBlank
    @NotEmpty
    @StringValidate
    private String userBranchName;
    @NotNull
    @NotBlank
    @NotEmpty
    private Long userAccountNumber;
    @NotNull
    @NotBlank
    @NotEmpty
    private String userBankIFSC;

}
