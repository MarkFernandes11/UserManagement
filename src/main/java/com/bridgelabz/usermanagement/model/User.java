package com.bridgelabz.usermanagement.model;

import java.util.Date;
import java.util.List;

import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	
	private Long userId;
	
	@NotNull
	private String firstName;
	
	private String middleName;
	
	private String lastName;
	
	@NotNull
	private Date dateOfBirth;
	
	@NotNull
	private String gender;
	
	@NotNull
	private String country;
	
	private String contact;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	private String address;
	
	@NotNull
	private String username;
	
	@NotNull
	private String password;
	
	@NotNull
	private String userRole;
	
	private String profilePicture;
	
	private int age;
	
	private boolean activationStatus;
	
	private Date createTime;
	
	private Date modifiedTime;
		
	private List<Permission> permission;
	
	@OneToOne
	private Authentication authentication;
}
