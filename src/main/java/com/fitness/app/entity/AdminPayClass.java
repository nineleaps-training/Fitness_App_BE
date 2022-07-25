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
@Document("admin_paid")
public class AdminPayClass {

	@Id
	@Field
	private String id;
	@Field
	private String orderId;
	@Field
	private String vendor;
	
	@Field
	private int amount;
	@Field
	private String status;
	@Field
	private String paymentId;
	@Field
	private String reciept;
	@Field
	private LocalDate date;
	@Field
	private LocalTime time;
	
	
	public AdminPayClass(String vendor, int amount) {
		super();
		this.vendor = vendor;
		this.amount = amount;
	}


	public AdminPayClass(String vendor, int amount, String status) {
		super();
		this.vendor = vendor;
		this.amount = amount;
		this.status = status;
	}
	
	
	
	
	
	
	
	
	
	
	
}
