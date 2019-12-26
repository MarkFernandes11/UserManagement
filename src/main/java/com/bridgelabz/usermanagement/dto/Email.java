package com.bridgelabz.usermanagement.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Email implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5497076483074184800L;

	private String to;
	
	private String subject;
	
	private String body;
}
