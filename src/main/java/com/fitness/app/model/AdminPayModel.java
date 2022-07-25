package com.fitness.app.model;

import com.fitness.app.componets.StringValidate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminPayModel {
    @NotNull
    @NotBlank
    @NotEmpty
    @StringValidate
    private String id;
    @NotNull
    @NotBlank
    @NotEmpty
    @StringValidate
    private String orderId;
    @NotNull
    @StringValidate
    private String vendor;
    @NotNull
    private int amount;
    @StringValidate
    private String status;
    @StringValidate
    private String paymentId;
    @StringValidate
    private String receipt;
    private LocalDate date;
    private LocalTime time;
}
