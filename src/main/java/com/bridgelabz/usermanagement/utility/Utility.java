package com.bridgelabz.usermanagement.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bridgelabz.usermanagement.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Utility {
	
	static ObjectMapper objectMapper;
	
	static {
		objectMapper = new ObjectMapper();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	public Object mapper(String userJson, UserDto userDto) {
		Object object = objectMapper.convertValue(userJson, userDto.getClass()); 
		
		return object;
	}
}
