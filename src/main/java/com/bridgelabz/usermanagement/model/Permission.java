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
public class Permission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long permissionId;
	
	private String permissionName;
	
	private boolean add;
	
	private boolean modify;
	
	private boolean delete;
	
	private boolean read;
	
	private Privilege privilege;
}
