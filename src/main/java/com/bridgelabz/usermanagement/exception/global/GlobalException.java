package com.bridgelabz.usermanagement.exception.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.usermanagement.exception.custom.UserException;
import com.bridgelabz.usermanagement.utility.Response;

@ControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Response> internalServerError(Exception exception) {
		Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), null);
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserException.class)
	public final ResponseEntity<Response> badRequestError(UserException exception) {
		Response response = new Response(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), null);
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
