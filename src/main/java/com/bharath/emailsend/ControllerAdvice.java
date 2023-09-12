package com.bharath.emailsend;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//@RestControllerAdvice
public class ControllerAdvice {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> generalExceptionHandler(Exception exp) {
		ErrorInfo e = new ErrorInfo();
		e.setTime(LocalDateTime.now());
		e.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		e.setErrorMessage("Something went Wrong");

		return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(EmailException.class)
	public ResponseEntity<ErrorInfo> emailExceptionHandler(EmailException exp) {
		ErrorInfo e = new ErrorInfo();
		e.setTime(LocalDateTime.now());
		e.setErrorCode(HttpStatus.BAD_REQUEST.value());
		e.setErrorMessage(exp.getMessage());

		return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
	}
}
