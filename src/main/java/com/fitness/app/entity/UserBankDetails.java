package com.fitness.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private String uEmail;
    @Field
    private String uName;
    @Field
    private String ubankName;
    @Field
    private String ubranchName;
    @Field
    private Long uaccountNumber;
    @Field
    private String ubankIFSC;
   
}
