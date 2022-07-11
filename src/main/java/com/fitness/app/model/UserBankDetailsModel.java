package com.fitness.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBankDetailsModel {

    private String userEmail;
    private String userName;
    private String userBankName;
    private String userBranchName;
    private Long userAccountNumber;
    private String userBankIFSC;

}
