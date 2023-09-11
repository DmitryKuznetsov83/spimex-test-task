package com.spimex.test_task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler({MethodArgumentTypeMismatchException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
		return "Method argument not valid " + e.getMessage();
	}

	@ExceptionHandler({ConstraintViolationException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleConstraintViolationException(final ConstraintViolationException e) {
		return "Constraint violation " + e.getMessage();
	}

	@ExceptionHandler({Throwable.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleThrowable(final Throwable e) {
		return "Internal server error " + e.getMessage();
	}

}
