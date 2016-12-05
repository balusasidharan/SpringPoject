package com.balu.spring.exception;

import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppWideException {

	@ExceptionHandler(CannotCreateTransactionException.class)
	public String transactionFailureException(){
		
		return "error/error";
	}
	
}
