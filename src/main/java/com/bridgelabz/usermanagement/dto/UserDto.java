package com.bridgelabz.usermanagement.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bridgelabz.usermanagement.model.Authentication;
import com.bridgelabz.usermanagement.model.Permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	private String firstName;
	
	private String middleName;
	
	private String lastName;
	
	private Date dateOfBirth;
	
	private String gender;
	
	private String country;
	
	private String contact;
	
	private String email;
	
	private String address;
	
	private String username;
	
	private String password;
	
	private String userRole;
		
	private List<Permission> permission = new ArrayList<Permission>();
	
}
