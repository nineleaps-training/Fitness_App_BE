package com.fitness.app.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "AdminPayRequestModel")
public class AdminPayRequestModel {

	@ApiModelProperty(name = "id", notes = "admin id")
	@NotNull
	private String id;
	@ApiModelProperty(name = "orderId", notes = "order id")
	@NotNull
	private String orderId;
	@ApiModelProperty(name = "vendor", notes = "vendor email")
	@NotNull
	@NotBlank
	@NotEmpty
	private String vendor;
	@ApiModelProperty(name = "amount", notes = "amount payable")
	@NotNull
	private int amount;
	@ApiModelProperty(name = "status", notes = "Due or Completed")
	private String status;
	@ApiModelProperty(name = "paymentId", notes = "payment id")
	@NotNull
	private String paymentId;
	@ApiModelProperty(name = "reciept", notes = "receipt description")
	private String reciept;
	@ApiModelProperty(name = "date", notes = "date of payment")
	private LocalDate date;
	@ApiModelProperty(name = "time", notes = "time of payment")
	private LocalTime time;    
}