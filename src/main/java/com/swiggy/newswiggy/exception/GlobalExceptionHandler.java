package com.swiggy.newswiggy.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ExceptionResponse> handleSQLIntegrityConstraintViolationException(
			SQLIntegrityConstraintViolationException ex) {

		log.error("SQLIntegrityConstraintViolationException: " + ex.getMessage());
		return new ResponseEntity<ExceptionResponse>(
				new ExceptionResponse(ex.getMessage(), HttpStatus.CONFLICT.name(), HttpStatus.CONFLICT.value()),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		log.error("IllegalArgumentException: " + ex.getMessage());
		return new ResponseEntity<ExceptionResponse>(
				new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidAgeException.class)
	public ResponseEntity<ExceptionResponse> handleInvalidAgeException(InvalidAgeException ex) {

		log.error("InvalidAgeException: " + ex.getMessage());
		return new ResponseEntity<ExceptionResponse>(
				new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(WrongPasswordException.class)
	public ResponseEntity<ExceptionResponse> handleWrongPasswordException(WrongPasswordException ex) {
		log.error("WrongPasswordException: " + ex.getMessage());
		return new ResponseEntity<ExceptionResponse>(
				new ExceptionResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED.name(), HttpStatus.UNAUTHORIZED.value()),
				HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(EmailNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleWrongPasswordException(EmailNotFoundException ex) {
		log.error("EmailNotFoundException: " + ex.getMessage());
		return new ResponseEntity<ExceptionResponse>(
				new ExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UnauthorizedUserException.class)
	public ResponseEntity<ExceptionResponse> handleUnauthorizedUserException(UnauthorizedUserException ex) {
		log.error("UnauthorizedUserException: " + ex.getMessage());
		return new ResponseEntity<ExceptionResponse>(
				new ExceptionResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED.name(), HttpStatus.UNAUTHORIZED.value()),
				HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ExceptionResponse> handleInvalidTokenException(InvalidTokenException ex) {
		log.error("InvalidTokenException: " + ex.getMessage());
		return new ResponseEntity<ExceptionResponse>(
				new ExceptionResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED.name(), HttpStatus.UNAUTHORIZED.value()),
				HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException ex) {
		log.error("Exception occured: " + ex.getMessage());
		return new ResponseEntity<ExceptionResponse>(
				new ExceptionResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED.name(), HttpStatus.UNAUTHORIZED.value()),
				HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
		log.error("Exception occured: " + ex.getMessage());
		return new ResponseEntity<ExceptionResponse>(
				new ExceptionResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED.name(), HttpStatus.UNAUTHORIZED.value()),
				HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleProductNotFoundException(ProductNotFoundException ex) {
		log.error("Exception occured: " + ex.getMessage());
		return new ResponseEntity<ExceptionResponse>(
				new ExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value()),
				HttpStatus.NOT_FOUND);
	}
}
