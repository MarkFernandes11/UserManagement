package com.bridgelabz.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDto {
	
	private boolean rememberMe;
	
	private boolean forgotPassword;
	
	private String name;
}
