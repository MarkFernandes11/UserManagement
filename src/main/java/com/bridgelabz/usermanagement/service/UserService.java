package com.bridgelabz.usermanagement.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.usermanagement.dto.AuthenticationDto;
import com.bridgelabz.usermanagement.dto.LoginDto;
import com.bridgelabz.usermanagement.dto.ResetPasswordDto;
import com.bridgelabz.usermanagement.dto.UserDto;
import com.bridgelabz.usermanagement.utility.Response;

public interface UserService {

	Response login(LoginDto login);
	
	Response logout(Long id);
	
	Response forgotPassword(String email);
	
	Response resetPassword(Long id, ResetPasswordDto resetPasswordDto);
	
	Response registerUser(UserDto userDto, MultipartFile profilePic) throws IOException;

	Response editUser(Long id, UserDto userDto, MultipartFile profilePic) throws IOException;
	
	Response getUsers(Long id);
	
	Response getUser(Long userId);
	
	Response getProfilePicture(Long id) throws IOException;
	
	Response updateProfilePic(Long userId, MultipartFile file) throws IOException;

	Response removeProfilePicture(Long userId);
	
	Response getRegisteredUsers(Long id);
	
	Response updateAuthentication(Long id, AuthenticationDto authentication);
}



