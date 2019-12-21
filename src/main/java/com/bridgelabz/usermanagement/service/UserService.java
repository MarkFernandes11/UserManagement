package com.bridgelabz.usermanagement.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.usermanagement.dto.AuthenticationDto;
import com.bridgelabz.usermanagement.dto.LoginDto;
import com.bridgelabz.usermanagement.dto.UserDto;
import com.bridgelabz.usermanagement.model.User;
import com.bridgelabz.usermanagement.utility.Response;

public interface UserService {

	Response login(LoginDto login);
	
	Response forgotPassword(String email);
	
	Response registerUser(UserDto userDto, MultipartFile profilePic);

	Response editUser(String token, Long userId, UserDto userDto, MultipartFile profilePic);
	
	Response getUsers(String token);
	
	Response getUser(String token, Long userId);
	
	Response getProfilePicture(String token) throws IOException;
	
	Response updateProfilePic(Long userId, MultipartFile file) throws IOException;

	Response removeProfilePicture(Long userId);
	
	Response getRegisteredUsers(String token);
	
	ResponseEntity<Response> updateAuthentication(String token, AuthenticationDto authentication);
}



