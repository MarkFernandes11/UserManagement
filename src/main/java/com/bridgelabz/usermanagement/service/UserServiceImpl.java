package com.bridgelabz.usermanagement.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.usermanagement.dto.AuthenticationDto;
import com.bridgelabz.usermanagement.dto.Email;
import com.bridgelabz.usermanagement.dto.LoginDto;
import com.bridgelabz.usermanagement.dto.ResetPasswordDto;
import com.bridgelabz.usermanagement.dto.UserDto;
import com.bridgelabz.usermanagement.exception.custom.UserException;
import com.bridgelabz.usermanagement.model.Authentication;
import com.bridgelabz.usermanagement.model.LoginHistory;
import com.bridgelabz.usermanagement.model.User;
import com.bridgelabz.usermanagement.repository.UserRepository;
import com.bridgelabz.usermanagement.utility.CommonFiles;
import com.bridgelabz.usermanagement.utility.Response;
import com.bridgelabz.usermanagement.utility.TokenUtil;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
	private TokenUtil tokenUtility;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Value("${fundoo.rabbitmq.routingkey}")
	String routingKey;
	
	
	@Override
	public Response login(LoginDto login) {
		User user = userServiceImpl.userExist(login.getUsername());
		userServiceImpl.passwordMatchChecker(login.getPassword(), user.getPassword());
		
		String token = tokenUtility.createToken(user.getUserId());
		
		LoginHistory loginHistory = new LoginHistory();
		loginHistory.setLoginDate(new Date());
		loginHistory.setUser(user);
		user.getLoginList().add(loginHistory);
		user.setLastLogin(new Date());
		
		userRepository.save(user);
		
		return new Response(HttpStatus.OK.value(), "Login successful", token);
	}
	
	private User userExist(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		
		if(!user.isPresent()) {
			throw new UserException("User absent");
		}
		return user.get();
	}
	
	private User userPresent(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		
		if(!user.isPresent()) {
			throw new UserException("User absent");
		}
		return user.get();
	}
	
	private User userPresent(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		
		if(!user.isPresent()) {
			throw new UserException("Specified email does not exists in our records");
		}
		return user.get();
	}
	
	private void passwordMatchChecker(String password, String encodedPassword) {
		if(!passwordEncoder.matches(password, encodedPassword)) {
			throw new UserException("Invalid password");
		}
	}
	
	private void userAbsent(String username, String email) {
		Optional<User> user = userRepository.findByUsernameAndEmail(username, email);
		
		if(user.isPresent()) {
			throw new UserException("User already exists");
		}
	}

	@Override
	public Response forgotPassword(String email) {
		User user = userServiceImpl.userPresent(email);
		
		String token = tokenUtility.createToken(user.getUserId());
		String to = email;
		String body = "http://localhost:8088/usermanagement/resetPassword?token="+token;
		String subject = "Password recovery mail"; 
		
		Email mail = new Email();
		mail.setTo(to);
		mail.setBody(body);
		mail.setSubject(subject);
		
		//Error 500
		rabbitTemplate.convertAndSend(routingKey, mail);
		
		return new Response(HttpStatus.OK.value(), "Reset password link has been sent on entered email.", null);
	}
	
	@Override
	public Response resetPassword(Long id, ResetPasswordDto resetPasswordDto) {
		// TODO Auto-generated method stub
		User user = userServiceImpl.userPresent(id);
		
		if(!resetPasswordDto.getPassword().equals(resetPasswordDto.getConfirmPassword())) {
			throw new UserException("Password and confirm password does not match");
		}
		
		user.setPassword(resetPasswordDto.getPassword());
		userRepository.save(user);
		
		return new Response(HttpStatus.OK.value(), "Password reset successful", user);
	}

	@Override
	public Response registerUser(UserDto userDto, MultipartFile profilePic) throws IOException {
		// TODO Auto-generated method stub
		userServiceImpl.userAbsent(userDto.getUsername(), userDto.getEmail());
		
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		User newUser = modelMapper.map(userDto, User.class);
		
		newUser.setActivationStatus(true);
		newUser.setCreateTime(new Date());
		newUser.setModifiedTime(new Date());
		newUser.setLoginList(null);
		
		System.out.println("Profile pic bytes are "+profilePic.getBytes());
				
		byte[] bytes;
		try {
			bytes = profilePic.getBytes();
			String extension = profilePic.getContentType().replace("image/", "");
			String fileLocation = CommonFiles.IMAGE_FOLDER_PATH + userDto.getEmail() + "." + extension;
			Path path = Paths.get(fileLocation);
			Files.write(path, bytes);
			newUser.setProfilePicture(fileLocation);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		userRepository.save(newUser);
		
		//		private int age;
		
		return new Response(HttpStatus.CREATED.value(), CommonFiles.REGISTER, null);
	}

	@Override
	public Response editUser(Long id, UserDto userDto, MultipartFile profilePic) throws IOException {
		// TODO Auto-generated method stub
		User user = userServiceImpl.userPresent(id);
		
		user = userServiceImpl.updateUserDetails(user, userDto);
		
		System.out.println("Profile pic bytes are "+profilePic.getBytes());
				
		byte[] bytes;
		try {
			bytes = profilePic.getBytes();
			String extension = profilePic.getContentType().replace("image/", "");
			String fileLocation = CommonFiles.IMAGE_FOLDER_PATH + userDto.getEmail() + "." + extension;
			Path path = Paths.get(fileLocation);
			Files.write(path, bytes);
			user.setProfilePicture(fileLocation);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		userRepository.save(user);
		
		//		private int age;
		
		return new Response(HttpStatus.OK.value(), CommonFiles.REGISTER, null);
	}
	
	private User updateUserDetails(User user, UserDto userDto) {
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setMiddleName(userDto.getMiddleName());
		user.setAddress(userDto.getAddress());
		user.setDateOfBirth(userDto.getDateOfBirth());
		user.setGender(userDto.getGender());
		user.setCountry(userDto.getCountry());
		user.setContact(userDto.getContact());
		user.setEmail(userDto.getEmail());
		user.setAddress(userDto.getAddress());
		user.setUsername(userDto.getUsername());
		user.setUserRole(userDto.getUserRole());
		user.setPermissions(userDto.getPermission());		
		user.setModifiedTime(new Date());
		
		return user; 
	}

	@Override
	public Response getUsers(Long id) {
		List<User> users = userRepository.findAll(); 
		return new Response(HttpStatus.OK.value(), "Received list", users);
	}

	@Override
	public Response getUser(String token, Long userId) {		
		User user = userPresent(userId); 
		return new Response(HttpStatus.OK.value(), "Received user", user);
	}

	@Override
	public Response getProfilePicture(Long userId) throws IOException {		
		User user = userServiceImpl.userPresent(userId);
		
		String profilePic = "";
		String filePath = CommonFiles.IMAGE_FOLDER_PATH;
		File fileFolder = new File(filePath);
		System.out.println("Check 1");
		if (fileFolder != null) {
			for (final File file : fileFolder.listFiles()) {
				if (!file.isDirectory()) {
					System.out.println("Check 2");
					String encodeBase64 = null;
					try {
						if ((filePath + file.getName()).equals(user.getProfilePicture())) {
							System.out.println("Check 3");
							String extension = FilenameUtils.getExtension(file.getName());
							FileInputStream fileInputStream = new FileInputStream(file);
							byte[] bytes = new byte[(int) file.length()];
							fileInputStream.read(bytes);
							encodeBase64 = Base64.getEncoder().encodeToString(bytes);
							profilePic = ("data:image/" + extension + ";base64," + encodeBase64);
							System.out.println("Hey data rcvd "+profilePic);
							fileInputStream.close();
							break;
						}
					} 
					catch (Exception e) {
						System.out.println("exception occured");
					}
				}
			}
		}
		System.out.println("Hey data rcvd "+profilePic);
		return new Response(HttpStatus.OK.value(), "Img received" , profilePic);
	}

	@Override
	public Response updateProfilePic(Long userId, MultipartFile file) throws IOException {
		User user = userPresent(userId);
		
		byte[] bytes = file.getBytes();
		
		if(bytes.equals(null)) {
			throw new UserException(CommonFiles.IMAGE_ABSENT);
		}
		String extension = file.getContentType().replace("image/", "");
		String pathImg = "path";
		Path path = Paths.get(pathImg + user.getEmail() + "." + extension);
		Files.write(path, bytes);

		user.setProfilePicture(pathImg + user.getEmail() + "." + extension);

		return new Response(200, CommonFiles.PICTURE_SET, userRepository.save(user));
	}
	

	@Override
	public Response removeProfilePicture(Long userId) {
		User user = userPresent(userId);
		
		if(user.getProfilePicture() == null)
			throw new UserException(CommonFiles.IMAGE_ABSENT);
		
		user.setProfilePicture(null);
		userRepository.save(user);
		
		return new Response(HttpStatus.OK.value(), CommonFiles.REMOVE_PIC, null);
	}
	

	@Override
	public Response getRegisteredUsers(Long id) {
		List<User> users = userRepository.findAll();
		
		users = users.stream().sorted((user1,user2) -> user2.getCreateTime()
				.compareTo(user1.getCreateTime())).collect(Collectors.toList());
		
		return new Response(HttpStatus.OK.value(), "Registered users list", users);
	}

	@Override
	public Response updateAuthentication(Long id, AuthenticationDto authentication) {
		User user = userServiceImpl.userPresent(id);
		
		Authentication authenticate = modelMapper.map(authentication, Authentication.class);
		user.setAuthentication(authenticate);		
		userRepository.save(user);
		
		return new Response(HttpStatus.OK.value(), CommonFiles.AUTH_UPDATED, null);
	}
	
}
