package com.fitness.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminPayModel {

    private String id;
    private String orderId;
    private String vendor;
    private int amount;
    private String status;
    private String paymentId;
    private String receipt;
    private LocalDate date;
    private LocalTime time;
}
