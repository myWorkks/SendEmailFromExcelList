package com.bharath.emailsend;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class AspectClass {

	private final WebDriver webDriver;
//
//	@Autowired
	public AspectClass(WebDriver webDriver) {
		this.webDriver = webDriver;
		//System.out.println("aspect object created");
	}

//	@Before("mainMethodpointcut()")
//	public void check(JoinPoint joinPoint) {
//		System.out.println(joinPoint.toString());
//		System.out.println("before mai method called");
//	}

	@AfterReturning(pointcut = "mainMethodpointcut()")
	public void afterAppRun() {
		//System.out.println("main method executed");
		webDriver.get("http://localhost:8080/");
	}

	@Before("execution(*  com.bharath.emailsend..EmailController.*(..))")
	public void checkto() {
		//System.out.println("checking before");
	}

	@Pointcut("execution(public static void com.bharath.emailsend.EmailSendApplication.main(String[]))")
	public void mainMethodpointcut() {
		//System.out.println("main method poincut");
	}
}
