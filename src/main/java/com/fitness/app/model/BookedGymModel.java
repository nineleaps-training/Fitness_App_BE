package com.fitness.app.model;

import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fitness.app.entity.GymAddressClass;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "BookedGymModel")
public class BookedGymModel {
	
	@ApiModelProperty(name = "id", notes = "Booked gym id")
	@NotNull
	private String id;
	@ApiModelProperty(name = "gymName", notes = "Name of gym")
	@NotNull
	@NotBlank
	@NotEmpty
	private String gymName;
	@ApiModelProperty(name = "vendor", notes = "Vendor Email")
	@NotNull
	@NotBlank
	@NotEmpty
	private String vendor;
	@ApiModelProperty(name = "service", notes = "List of Services")
	@NotEmpty
	private List<String> service;
	@ApiModelProperty(name = "slot", notes = "Slot")
	@NotNull
	@NotBlank
	@NotEmpty
	private String slot;
	@ApiModelProperty(name = "endDate", notes = "Subscription end date")
	@NotNull
	@NotBlank
	@NotEmpty
	private LocalDate endDate;
	@ApiModelProperty(name = "address", notes = "Address of gym")
	private GymAddressClass address;
	@ApiModelProperty(name = "contact", notes = "Contact number of vendor")
	private String contact;
	@ApiModelProperty(name = "rating", notes = "Rating of the gym")
	private Double rating;
}
