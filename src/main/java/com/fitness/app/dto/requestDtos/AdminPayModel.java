package com.fitness.app.dto.requestDtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(description = "AdminPayModel")
public class AdminPayModel {
    @ApiModelProperty(name = "orderId", notes = "order-id")
    @NotNull
    @NotBlank
    @NotEmpty
    private String orderId;
    @ApiModelProperty(name = "vendor", notes = "vendor email id")
    @NotNull
    @NotBlank
    @NotEmpty
    private String vendor;
    @ApiModelProperty(name = "amount", notes = "amount payable")
    @NotNull
    @Min(value = 1, message = "Amount should be atleast 1")
    private int amount;
    @ApiModelProperty(name = "status", notes = "Due or Completed")
    private String status;
    @ApiModelProperty(name = "paymentId", notes = "payment id")
    private String paymentId;
    @ApiModelProperty(name = "receipt", notes = "receipt description")
    private String receipt;
    @ApiModelProperty(name = "date", notes = "date of payment")
    private LocalDate date;
    @ApiModelProperty(name = "time", notes = "time of payment")
    private LocalTime time;
    public AdminPayModel(String vendor, int amount) {

        this.vendor = vendor;
        this.amount = amount;
    }


}
