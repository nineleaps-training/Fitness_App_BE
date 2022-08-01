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
	private String vEmail;

	@Field
	private String vGender;

	@Field
	private String vFullAddress;

	@Field
	private String vCity;

	@Field
	private Long vPostal;

}
