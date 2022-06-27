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
	private String vendorEmail;
	@Field
	private String vendorName;
	@Field
    private String vendorBankName;
	@Field
    private String vendorBranchName;
	@Field
    private Long vendorAccountNumber;
	@Field
    private String vendorBankIFSC;
	@Field
	private String paymentSchedule;


}
