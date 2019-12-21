package com.bridgelabz.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.usermanagement.dto.AuthenticationDto;
import com.bridgelabz.usermanagement.dto.LoginDto;
import com.bridgelabz.usermanagement.dto.UserDto;
import com.bridgelabz.usermanagement.model.User;
import com.bridgelabz.usermanagement.service.UserService;
import com.bridgelabz.usermanagement.utility.Response;
import com.bridgelabz.usermanagement.utility.Utility;


@RestController
@RequestMapping("usermanagement/")
public class UserController {
	
	@Autowired
	private Utility utility;
	
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
	
	@PostMapping
	public ResponseEntity<Response> register(@RequestParam String registerJson, @RequestParam MultipartFile profileImage) {
		
		UserDto userDto = new UserDto();
		userDto = (UserDto) utility.mapper(registerJson, userDto);
		
		System.out.println("User dto received after conversion "+userDto);
		
		return null;
	}
	
	@PutMapping("edit")
	public ResponseEntity<Response> edit(@RequestParam String token, @RequestParam String editJson, @RequestParam MultipartFile profileImage ) {
		UserDto userDto;
		
		return null;
	}
	
	@GetMapping
	public ResponseEntity<Response> getUsers(@RequestParam String token) {
		
		return new ResponseEntity<Response>(userService.getUsers(token), HttpStatus.OK);
		
	}
	
	@GetMapping("{userId}")
	public ResponseEntity<Response> getUser(@RequestParam String token, @PathVariable Long userId) {
		
		return new ResponseEntity<Response>(userService.getUser(token, userId), HttpStatus.OK);
		
	}
	
	@GetMapping("registeredusers")
	public ResponseEntity<Response> getRegisteredUsers(@RequestParam String token) {
		
		return new ResponseEntity<Response>(userService.getRegisteredUsers(token), HttpStatus.OK);
		
	}
	
//	@GetMapping("/getImage")
//	public ResponseEntity<Response> getImage(@RequestHeader String token) throws UserServiceException, IOException {
//		Long id = tokenUtil.decodeToken(token);
//		
//		Response response = userService.getProfilePicture(id);
//		
//		return new ResponseEntity<Response>(response, HttpStatus.OK);
//	}
//	
//	@PostMapping("/addimage")
//	public ResponseEntity<Response> uploadProfilePicture(@RequestHeader String token, @RequestParam MultipartFile file) throws UserServiceException, IOException {
//		Long id = tokenUtil.decodeToken(token);
//		
//		Response addImage = userService.updateProfilePic(id, file);
//		
//		return new ResponseEntity<Response>(addImage, HttpStatus.OK);
//	}
//	
//	@PutMapping("/removeimage")
//	public ResponseEntity<Response> removeProfilePicture(String token) throws UserServiceException {		
//		Long id = tokenUtil.decodeToken(token);
//		
//		Response removeImage = userService.removeProfilePicture(id);
//		
//		return new ResponseEntity<Response>(removeImage, HttpStatus.OK);
//	}
	
	@GetMapping("profilePic")
	public ResponseEntity<Response> getProfilePic(@RequestParam String token) {
		
		return null;
	}
	
	@PutMapping("profilePic")
	public ResponseEntity<Response> updateProfilePic(@RequestParam String token, @RequestParam MultipartFile profileImage ) {
		
		return null;
	}
	
	@PutMapping("authentication")
	public ResponseEntity<Response> updateAuthentication(@RequestParam String token, @RequestBody AuthenticationDto authentication) {
		
		return null;
	}
}
