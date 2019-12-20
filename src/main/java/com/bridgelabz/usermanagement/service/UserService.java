package com.bridgelabz.usermanagement.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.usermanagement.dto.LoginDto;
import com.bridgelabz.usermanagement.dto.UserDto;
import com.bridgelabz.usermanagement.exception.Response;
import com.bridgelabz.usermanagement.model.User;

public interface UserService {

	Response login(LoginDto login);
	
	Response forgotPassword(String email);
	
	List<User> getUsers(String token);
	
	User getUser(String token, Long userId);
	
	Response registerUser(String token, UserDto userDto);

	Response editUser(String token, Long userId, UserDto userDto);
	
	Response getProfilePicture(String token, Long userId) throws IOException;
	
	Response updateProfilePic(Long userId, MultipartFile file) throws IOException;

	Response removeProfilePicture(Long userId);
}



