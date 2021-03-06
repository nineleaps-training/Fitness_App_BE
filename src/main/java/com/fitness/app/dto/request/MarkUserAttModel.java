package com.fitness.app.dto.request;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "MarkUserAttModel")
public class MarkUserAttModel {

	@NotEmpty
	@ApiModelProperty(name = "gym", notes = "Gym id")
	@NotNull
	@NotBlank
	private String gym;
	@NotBlank
	@NotNull
	@ApiModelProperty(name = "vendor", notes = "Vendor Email")
	@NotEmpty
	private String vendor;
	@NotEmpty
	@ApiModelProperty(name = "users", notes = "List of users")
	private List<String> users;
}
