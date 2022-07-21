package com.fitness.app.dto.requestDtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "RatingRequestModel")
public class RatingModel {

	@ApiModelProperty(name = "target", notes = "Target of rating")
	@NotNull
	@NotBlank
	@NotEmpty
	@Pattern(regexp = "^(a-z|A-Z)$", message = "Only alphabetical char allowed")
	private String rateTarget;
	@ApiModelProperty(name = "rateRatter", notes = "who is rating")
	@NotNull
	@NotBlank
	@NotEmpty
	@Pattern(regexp = "^(a-z|A-Z)$", message = "Only alphabetical char allowed")
	private String rateRatter;
	@NotNull
	@ApiModelProperty(name = "rating", notes = "Rating")
	@DecimalMax(value = "5.0", inclusive = true, message = "Value should not exceed 5.0")
	@DecimalMin(value = "0.0", inclusive = true, message = "Value should not be less than 0.0")
	private Double rate;
	
}
