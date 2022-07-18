package com.fitness.app.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("vender_user")
public class VenderUser {

	@Id
	private String id;
	@Field
	private String email;

	@Field
	private String firstName;
	@Field
	private String lastName;
	@Field
	private String password;
	@Field
	private boolean active;
	@Field
	private String mobile;
	@Field
	private boolean loggedIn;

	@Field
	private String role;

	public VenderUser(String email, boolean active, boolean loggedIn) {

		this.email = email;
		this.active = active;
		this.loggedIn = loggedIn;
	}

	public VenderUser(String email, String firstName, String lastName, String password, String mobile) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.mobile = mobile;
	}

}
