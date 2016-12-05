package com.balu.spring.aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Qualifier;




@Aspect
@Qualifier("timingAspect")
public class TimingAspect {

	
	@Before("execution  (** com.balu.spring.controller.PersonController.listPersons(..))")
	public void startLogging(){
		System.out.println("Started getting the result");
	}
	
	
	@After("execution (** com.balu.spring.controller.PersonController.listPersons(..))")
	public void finishLogging(){
		
		System.out.println("Finished getting the result");
	}
}
