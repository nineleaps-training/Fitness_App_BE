package com.fitness.app.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "user_bank")
public class UserBankDetails {
    @Id
    @Field
    private String email;
    @Field
    private String name;
    @Field
    private String bankName;
    @Field
    private String branchName;
    @Field
    private Long accountNumber;
    @Field
    private String bankIFSC;
   
}
