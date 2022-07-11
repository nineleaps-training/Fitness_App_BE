package com.fitness.app.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("vendor_order")
public class VendorPayment {

    @Id
    @Field
    private String vendor;
    @Field
    private String user;
    @Field
    private String gym;
    @Field
    private int amount;
    @Field
    private String status;
    @Field
    private LocalDate date;
    @Field
    private LocalTime time;


    public VendorPayment(String email, String vendor, int amount, String status) {

        this.user = email;
        this.vendor = vendor;
        this.amount = amount;
        this.status = status;
    }


    public VendorPayment(String email, String vendor, int amount) {
        super();
        this.user = email;
        this.vendor = vendor;
        this.amount = amount;
    }


}
