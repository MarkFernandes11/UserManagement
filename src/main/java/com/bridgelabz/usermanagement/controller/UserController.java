package com.bridgelabz.usermanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.usermanagement.dto.LoginDto;
import com.bridgelabz.usermanagement.exception.Response;

@RestController
@RequestMapping("usermanagement/")
public class UserController {
	
	@GetMapping
	public String get(@RequestParam String name) {
		return "Hello "+name;
	}
	
	@PostMapping("login")
	public ResponseEntity<Response> login(@RequestBody LoginDto loginDto) {
		return null;
	}
	
	@PutMapping("forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam String email) {
		return null;
	}
	
	
}
