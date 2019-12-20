package com.bridgelabz.usermanagement.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Authentication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long authenticationId;
	
	private boolean rememberMe;
	
	private boolean forgotPassword;
	
	private String name;
}
