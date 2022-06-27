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
@Document(value = "vendor_details")
public class VendorDetails {

	@Id
	@Field
	private String vendorEmail;

	@Field
	private String vendorGender;

	@Field
	private String vendorFullAddress;

	@Field
	private String vendorCity;

	@Field
	private Long vendorPostal;

}
