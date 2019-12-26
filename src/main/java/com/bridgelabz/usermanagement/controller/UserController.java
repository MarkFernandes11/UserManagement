package com.bridgelabz.usermanagement.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.usermanagement.dto.AuthenticationDto;
import com.bridgelabz.usermanagement.dto.LoginDto;
import com.bridgelabz.usermanagement.dto.ResetPasswordDto;
import com.bridgelabz.usermanagement.dto.UserDto;
import com.bridgelabz.usermanagement.service.UserService;
import com.bridgelabz.usermanagement.utility.Response;
import com.bridgelabz.usermanagement.utility.TokenUtil;
import com.bridgelabz.usermanagement.utility.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


@RestController
@RequestMapping("usermanagement/")
@CrossOrigin
public class UserController {
	
	@Autowired
	private Utility utility;
	
	@Autowired
	private TokenUtil tokenUtility;
	
	@Autowired
	private UserService userService;
	
//	Testing
//	@GetMapping
//	public String get(@RequestParam String name) {
//		return "Hello "+name;
//	}
	
	@PostMapping("login")
	public ResponseEntity<Response> login(@RequestBody LoginDto loginDto) {
		
		return new ResponseEntity<Response>( userService.login(loginDto), HttpStatus.OK);		
	}
	
	@PutMapping("forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam String email) {

		return new ResponseEntity<Response>( userService.forgotPassword(email), HttpStatus.OK);
	}
	
	@PutMapping("resetpassword")
	public ResponseEntity<Response> resetPassword(@RequestHeader String token, @RequestBody ResetPasswordDto resetPasswordDto) {
		Long id = tokenUtility.decodeToken(token);
		
		return new ResponseEntity<Response>( userService.resetPassword(id, resetPasswordDto), HttpStatus.OK);		
	}
	
	@PostMapping
	public ResponseEntity<Response> register(@RequestParam String registerJson, @RequestParam MultipartFile profileImage)  throws JsonMappingException, JsonProcessingException,  IOException {
		UserDto userDto = new UserDto();
		userDto = (UserDto) utility.mapper(registerJson, userDto);
		
		System.out.println("User dto received after conversion "+userDto);
		System.out.println("Profile Image "+profileImage);
		
		return new ResponseEntity<Response>(userService.registerUser(userDto, profileImage), HttpStatus.OK);
	}
	
	@PutMapping("edit")
	public ResponseEntity<Response> edit(@RequestHeader String token, @RequestParam String editJson, @RequestParam MultipartFile profileImage ) throws IOException {
		
		UserDto userDto = new UserDto();
		userDto = (UserDto) utility.mapper(editJson, userDto);
		Long id = tokenUtility.decodeToken(token);
		
		System.out.println("User dto received after conversion "+userDto);
				
		return new ResponseEntity<Response>(userService.editUser(id, userDto, profileImage), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Response> getUsers(@RequestHeader String token) {
		Long id = tokenUtility.decodeToken(token);
		
		return new ResponseEntity<Response>(userService.getUsers(id), HttpStatus.OK);		
	}
	
	@GetMapping("{userId}")
	public ResponseEntity<Response> getUser(@RequestHeader String token, @PathVariable Long userId) {
		
		return new ResponseEntity<Response>(userService.getUser(token, userId), HttpStatus.OK);		
	}
	
	@GetMapping("registeredusers")
	public ResponseEntity<Response> getRegisteredUsers(@RequestHeader String token) {
		Long id = tokenUtility.decodeToken(token);
		
		return new ResponseEntity<Response>(userService.getRegisteredUsers(id), HttpStatus.OK);		
	}
		
	@PutMapping("/removeimage")
	public ResponseEntity<Response> removeProfilePicture(String token) {		
		Long id = tokenUtility.decodeToken(token);
		
		return new ResponseEntity<Response>(userService.removeProfilePicture(id), HttpStatus.OK);
	}
	
	@GetMapping("profilePic")
	public ResponseEntity<Response> getProfilePic(@RequestHeader String token) throws IOException {
		Long id = tokenUtility.decodeToken(token);
		
		return new ResponseEntity<Response>(userService.getProfilePicture(id), HttpStatus.OK);
	}
	
	@PutMapping("profilePic")
	public ResponseEntity<Response> updateProfilePic(@RequestHeader String token, @RequestParam MultipartFile profileImage ) throws IOException {
		Long id = tokenUtility.decodeToken(token);
		
		return new ResponseEntity<Response>(userService.updateProfilePic(id, profileImage), HttpStatus.OK);
	}
	
	@PutMapping("authentication")
	public ResponseEntity<Response> updateAuthentication(@RequestHeader String token, @RequestBody AuthenticationDto authentication) {
		Long id = tokenUtility.decodeToken(token);
		
		return new ResponseEntity<Response>(userService.updateAuthentication(id , authentication), HttpStatus.OK);
	}
}
