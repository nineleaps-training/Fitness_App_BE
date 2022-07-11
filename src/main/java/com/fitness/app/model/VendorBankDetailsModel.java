package com.fitness.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorBankDetailsModel {

    private String email;
    private String name;
    private String bankName;
    private String branchName;
    private Long accountNumber;
    private String bankIFSC;
    private String paymentSchedule;

}
