package com.bharath.emailsend;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class EmailSendApplication {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext con = SpringApplication.run(EmailSendApplication.class, args);
//ApplicationContext cont= new AnnotationConfigApplicationContext(BeansConfig.class);
		WebDriver driver = con.getBean(WebDriver.class);
		Thread.sleep(5000);
//
		driver.manage().window().maximize();
		driver.get("http://localhost:8080/");
		// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

		// Thread.sleep(5000);
		// con.close();
	}

}
