package com.fitness.app.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "user_bank")
public class UserBankDetails {
    @Id
    @Field
    private String userEmail;
    @Field
    private String userName;
    @Field
    private String userBankName;
    @Field
    private String userBranchName;
    @Field
    private Long userAccountNumber;
    @Field
    private String userBankIFSC;

}
