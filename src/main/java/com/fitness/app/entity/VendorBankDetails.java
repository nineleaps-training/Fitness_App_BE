package com.fitness.app.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "vendor_bank")
public class VendorBankDetails {

	@Id
	@Field
	private String email;
	@Field
	private String name;
	@Field
	private String bankName;
	@Field
	private String branchName;
	@Field
	private Long accountNumber;
	@Field
	private String bankIFSC;
	@Field
	private String paymentSchedule;

}
