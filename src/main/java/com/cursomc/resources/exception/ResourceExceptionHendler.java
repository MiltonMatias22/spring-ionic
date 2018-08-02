package com.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cursomc.services.exception.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHendler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> ObjectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		
		StandardError error = new StandardError(
				HttpStatus.NOT_FOUND.value(),
				e.getMessage(),
				System.currentTimeMillis()
				);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
}
