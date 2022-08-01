package com.fitness.app.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is a model class for AuthToken
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {

	private String jwt;
}
