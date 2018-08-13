package com.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cursomc.services.exception.AuthorizationException;
import com.cursomc.services.exception.DataIntegrityException;
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
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
		
		StandardError error = new StandardError(
				HttpStatus.BAD_REQUEST.value(),
				e.getMessage(),
				System.currentTimeMillis()
				);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		
		ValidationError validationError = new ValidationError(
				HttpStatus.BAD_REQUEST.value(),
				"Erro de Validação",
				System.currentTimeMillis()
				);
		
		for(FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			validationError.addError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> Authorization(AuthorizationException e, HttpServletRequest request) {
		
		StandardError error = new StandardError(
				HttpStatus.FORBIDDEN.value(),
				e.getMessage(),
				System.currentTimeMillis()
				);
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}
}
