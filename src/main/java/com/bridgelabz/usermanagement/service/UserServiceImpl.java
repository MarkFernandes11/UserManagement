package com.bridgelabz.usermanagement.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.usermanagement.dto.LoginDto;
import com.bridgelabz.usermanagement.dto.UserDto;
import com.bridgelabz.usermanagement.exception.Response;
import com.bridgelabz.usermanagement.model.User;

public class UserServiceImpl implements UserService {

	@Override
	public Response login(LoginDto login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response forgotPassword(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsers(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(String token, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response registerUser(String token, UserDto userDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response editUser(String token, Long userId, UserDto userDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getProfilePicture(String token, Long userId) throws IOException {
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
