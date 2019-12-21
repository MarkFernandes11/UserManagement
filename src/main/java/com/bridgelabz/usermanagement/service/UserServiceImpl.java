package com.bridgelabz.usermanagement.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.usermanagement.dto.AuthenticationDto;
import com.bridgelabz.usermanagement.dto.Email;
import com.bridgelabz.usermanagement.dto.LoginDto;
import com.bridgelabz.usermanagement.dto.UserDto;
import com.bridgelabz.usermanagement.exception.custom.UserException;
import com.bridgelabz.usermanagement.model.User;
import com.bridgelabz.usermanagement.repository.UserRepository;
import com.bridgelabz.usermanagement.utility.Response;
import com.bridgelabz.usermanagement.utility.TokenUtil;
import com.bridgelabz.usermanagement.utility.Utility;

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
	private Utility utility;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${fundoo.rabbitmq.routingkey}")
	String routingKey;
	
	
	@Override
	public Response login(LoginDto login) {
		User user = userServiceImpl.userExist(login.getUsername());
		userServiceImpl.passwordMatchChecker(login.getPassword(), user.getPassword());
		
		String token = tokenUtility.createToken(user.getUserId());
		
		return new Response(HttpStatus.OK.value(), "Login successful", token);
	}
	
	private User userExist(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		
		if(!user.isPresent()) {
			throw new UserException("User absent");
		}
		return user.get();
	}
	
	private void passwordMatchChecker(String password, String encodedPassword) {
		if(!passwordEncoder.matches(password, encodedPassword)) {
			throw new UserException("Invalid password");
		}
	}

	@Override
	public Response forgotPassword(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		
		if(!user.isPresent()) {
			throw new UserException("Specified email does not exists in our records");
		}
		
		String token = tokenUtility.createToken(user.get().getUserId());
		String to = email;
		String body = "http://localhost:8088/resetPassword?token="+token;
		String subject = "Password recovery mail"; 
		
		Email mail = new Email();
		mail.setTo(to);
		mail.setBody(body);
		mail.setSubject(subject);
		
		rabbitTemplate.convertAndSend(routingKey, mail);
		
		return new Response(HttpStatus.OK.value(), "Reset password linkhas been sent on entered email.", null);
	}

	@Override
	public Response registerUser(UserDto userDto, MultipartFile profilePic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response editUser(String token, Long userId, UserDto userDto, MultipartFile profilePic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getUsers(String token) {
		Long id = tokenUtility.decodeToken(token);
		
		List<User> users = userRepository.findAll();
		users = users.stream().filter(user -> user.getUserId() == id).collect(Collectors.toList()); 
		return new Response(HttpStatus.OK.value(), "Received list", users);
	}

	@Override
	public Response getUser(String token, Long userId) {
		
		User user = userRepository.findById(userId).get(); 
		return new Response(HttpStatus.OK.value(), "Received user", user);
	}

	@Override
	public Response getProfilePicture(String token) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response updateProfilePic(Long userId, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response removeProfilePicture(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getRegisteredUsers(String token) {
		// TODO Auto-generated method stub
		Long id = tokenUtility.decodeToken(token);
		
		List<User> users = userRepository.findAll();
		
		users = users.stream().filter(user -> user.getUserId() == id)
				.sorted((user1,user2) -> user1.getCreateTime()
				.compareTo(user2.getCreateTime())).collect(Collectors.toList());
		
		return new Response(HttpStatus.OK.value(), "Registered users list", users);
	}

	@Override
	public ResponseEntity<Response> updateAuthentication(String token, AuthenticationDto authentication) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
//	@Override
//	public Response getProfilePicture(Long userId) throws UserServiceException, IOException {
//		
//		Optional<User> user = userServiceImpl.userAbsent(userId);
//		
//		String profilePic = "";
//		String filePath = "/home/admin1/Documents/workspace-sts-3.9.10.RELEASE/Advanced-Java/Fundoo/src/main/java/com/bridgelabz/fundoo/ProfileImages/";
//		File fileFolder = new File(filePath);
//		System.out.println("Check 1");
//		if (fileFolder != null) {
//			for (final File file : fileFolder.listFiles()) {
//				if (!file.isDirectory()) {
//					System.out.println("Check 2");
//					String encodeBase64 = null;
//					try {
//						if ((filePath + file.getName()).equals(user.get().getProfilePic())) {
//							System.out.println("Check 3");
//							String extension = FilenameUtils.getExtension(file.getName());
//							FileInputStream fileInputStream = new FileInputStream(file);
//							byte[] bytes = new byte[(int) file.length()];
//							fileInputStream.read(bytes);
//							encodeBase64 = Base64.getEncoder().encodeToString(bytes);
//							profilePic = ("data:image/" + extension + ";base64," + encodeBase64);
//							System.out.println("Hey data rcvd "+profilePic);
//							fileInputStream.close();
//							break;
//						}
//
//					} catch (Exception e) {
//						System.out.println("exception occured");
//						logger.error("Exception occured");
//					}
//				}
//			}
//		}
//		System.out.println("Hey data rcvd "+profilePic);
//		return new Response(HttpStatus.OK.value(), "Img received" , profilePic);
//	}
//	
//	/**
//	 * Purpose: Method for updating profile picture of user of userService
//	 * 
//	 * @param emailIdToken to verify the user and granting him/her the authorization
//	 *                     to access the userServices.
//	 * @param file containing image for updating profile picture
//	 * @return Response which contains the response of the method
//	 * @throws IOException
//	 * @throws UserServiceException 
//	 */
//	
//	//@CachePut(value = "user", key="#userId")
//	
//	@Override
//	public Response updateProfilePic(Long userId, MultipartFile file) throws IOException, UserServiceException {
//
//		Optional<User> user = userAbsent(userId);
//		
//		byte[] bytes = file.getBytes();
//		
//		if(bytes.equals(null)) {
//			throw new UserServiceException(environment.getProperty("image.absent"));
//		}
//		String extension = file.getContentType().replace("image/", "");
//		String pathImg = "/home/admin1/Documents/workspace-sts-3.9.10.RELEASE/Advanced-Java/Fundoo/src/main/java/com/bridgelabz/fundoo/ProfileImages/";
//		Path path = Paths.get(pathImg + user.get().getEmail() + "." + extension);
//		logger.debug(path+"");
//		Files.write(path, bytes);
//
//		user.get().setProfilePic(pathImg + user.get().getEmail() + "." + extension);
//
//		return new Response(200, environment.getProperty("profilepic.set"), userRepository.save(user.get()));
//	}
//
//	//@CachePut(value = "user", key="#userId")
//	
//	@Override
//	public Response removeProfilePicture(Long userId) throws UserServiceException {		
//				
//		Optional<User> user = userAbsent(userId);
//		
//		if(user.get().getProfilePic() == null)
//			throw new UserServiceException(environment.getProperty("Image absent"));
//		
//		user.get().setProfilePic(null);
//		
//		return new Response(HttpStatus.OK.value(), environment.getProperty("profilepic.removed"),userRepository.save(user.get()));
//	}

}
