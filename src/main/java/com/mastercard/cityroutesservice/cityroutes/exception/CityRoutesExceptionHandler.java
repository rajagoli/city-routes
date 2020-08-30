package com.mastercard.cityroutesservice.cityroutes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CityRoutesExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	protected ResponseEntity<Object> handleBadRequestException(Exception ex) {
		BadRequestException badRequestException = new BadRequestException();
		badRequestException.setMessage(ex.getMessage());
		return new ResponseEntity<Object>(badRequestException.getMessage(), HttpStatus.OK);
	}
}