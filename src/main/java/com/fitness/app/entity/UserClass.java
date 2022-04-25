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
@Document(collection =  "all_users")
public class UserClass {

	
	@Id
	@Field
	private String email;
	@Field
	private String fullName;
	@Field
	private String mobile;
	@Field
	private String password;
	@Field
	private String role;
	@Field
	private Boolean activated;
	@Field
	private Boolean loggedin;
}
