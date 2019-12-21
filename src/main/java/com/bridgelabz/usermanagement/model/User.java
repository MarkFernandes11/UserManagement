package com.bridgelabz.usermanagement.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Permission> permissions;
	
	@OneToOne
	private Authentication authentication;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<LoginHistory> loginList;
}
