package com.fitness.app.model;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminPayModel {

	
	
	
	private String orderId;

	private String vendor;
	
	private int amount;
	private String status;
	private String paymentId;
	private String reciept;
	private LocalDate date;
	private LocalTime time;
	
	
	public AdminPayModel(String vendor, int amount) {
	
		this.vendor = vendor;
		this.amount = amount;
	}
	
	
	
	
	
	
}
