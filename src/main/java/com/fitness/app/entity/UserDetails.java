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
@Document("user_details")
public class UserDetails {

    @Id
    @Field
    private String uEmail;

    @Field
    private String uGender;

    @Field
    private String uFulladdress;

    @Field
    private String uCity;

    @Field
    private long uPostal;

}
