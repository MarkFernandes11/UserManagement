package com.bridgelabz.usermanagement.exception.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.usermanagement.exception.Response;
import com.bridgelabz.usermanagement.exception.custom.UserException;

@RestController
@ControllerAdvice
public class GlobalException {
	
	@ExceptionHandler
	public final ResponseEntity<Response> internalServerError(UserException exception) {
		Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), null);
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler
	public final ResponseEntity<Response> badRequestError(UserException exception) {
		Response response = new Response(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), null);
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
