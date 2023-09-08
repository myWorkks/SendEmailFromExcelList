package com.bharath.emailsend;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class EmailSendApplication {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext con = SpringApplication.run(EmailSendApplication.class, args);

		WebDriver driver = con.getBean(WebDriver.class);
		Thread.sleep(5000);

		driver.get("http://localhost:8080/");
		// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

		// Thread.sleep(5000);
		// con.close();
	}

	@Bean
	@Scope("singleton")
	public WebDriver webDriver() {
		System.setProperty("webdriver.chrome.driver",
				"G:\\MyWorks\\Extracts\\ChromeDriver\\chromedriver-win32\\chromedriver.exe");
		return new ChromeDriver();
	}

}
