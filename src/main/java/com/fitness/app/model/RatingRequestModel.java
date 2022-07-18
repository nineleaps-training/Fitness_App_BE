package com.fitness.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor

@ApiModel(description = "RatingRequestModel")
public class RatingRequestModel {

	@ApiModelProperty(name = "target", notes = "Target of rating")
	@NotNull
	@NotBlank
	@NotEmpty
	private String target;
	@ApiModelProperty(name = "rater", notes = "Who is rating")
	@NotNull
	@NotBlank
	@NotEmpty
	private String rater;
	@NotNull
	@ApiModelProperty(name = "rating", notes = "Rating")
	@DecimalMax(value = "5.0", inclusive = true, message = "Value should not exceed 5.0")
	@DecimalMin(value = "0.0", inclusive = true, message = "Value should not be less than 0.0")
	private double rating;

}
