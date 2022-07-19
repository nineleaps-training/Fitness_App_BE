package com.fitness.app.model;

import com.fitness.app.entity.UserClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "SignUpResponce")
public class SignUpResponceModel {
	@ApiModelProperty(name = "currentUser", notes = "Current User")
	private UserClass currentUser;
	@ApiModelProperty(name = "message", notes = "JWT Token")
	private String message;

}
