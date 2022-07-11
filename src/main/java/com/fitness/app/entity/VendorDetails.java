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
@Document(value = "vendor_details")
public class VendorDetails {

    @Id
    @Field
    private String email;

    @Field
    private String gender;

    @Field
    private String fullAddress;

    @Field
    private String city;

    @Field
    private Long postal;

}
