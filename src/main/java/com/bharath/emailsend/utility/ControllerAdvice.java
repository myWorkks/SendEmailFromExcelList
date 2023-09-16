package com.bharath.emailsend.utility;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.bharath.emailsend.exception.EmailException;

@RestControllerAdvice
public class ControllerAdvice {
	@ExceptionHandler(Exception.class)
	public ModelAndView generalExceptionHandler(Exception exp) {
		ErrorInfo e = new ErrorInfo();
		e.setTime(LocalDateTime.now());
		e.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		e.setErrorMessage(exp.getMessage()); // Set the exception message

		ModelAndView modelAndView = new ModelAndView("error"); // Specify the name of your custom error HTML page
	//modelAndView.
		modelAndView.addObject("errorInfo", e); // Add the ErrorInfo object to the model

		return modelAndView;
	}

	@ExceptionHandler(EmailException.class)
	public ModelAndView emailExceptionHandler(EmailException exp) {
	//	System.out.println(exp.getMessage());
		ErrorInfo e = new ErrorInfo();
		e.setTime(LocalDateTime.now());
		e.setErrorCode(HttpStatus.BAD_REQUEST.value());
		e.setErrorMessage(exp.getMessage());

		ModelAndView modelAndView = new ModelAndView("error"); // Specify the name of your custom error HTML page
		modelAndView.addObject("errorInfo", e); // Add the ErrorInfo object to the model

		return modelAndView;
	}
}
